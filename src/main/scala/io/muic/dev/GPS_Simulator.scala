package io.muic.dev

import scala.concurrent.ExecutionContext.Implicits.global
import java.util.Date
import java.util.concurrent.TimeUnit
import scala.io.Source
import akka.pattern.ask
import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.util.Timeout
import scala.concurrent.duration._
object TimePerLineActor{
  case class TimeRequest(data_with_delay: (Long, ttetRawData))
  case class TimeReport(line: ttetRawData) // When done
}

class TimePerLineActor extends Actor{
  import TimePerLineActor._
  def receive = {
    case TimeRequest(data_with_delay) => {
      val (delay, data) = data_with_delay
      val diff = delay - new Date().getTime()
      if (diff > 0){TimeUnit.MILLISECONDS.sleep(diff);}
      sender ! TimeReport(data)
      context.stop(self) //no need message
    }
  }
}

object TimeActor{
  case class TimePrint(data_with_delay: (Long, ttetRawData))
  case class Tell(line:ttetRawData)
}

class TimeActor extends Actor{
  import TimeActor._
  import TimePerLineActor._
  var theMaster: Option[ActorRef] = None
  def receive = {
    case TimePrint(data_with_delay) => {
      theMaster = Some(sender)
      context.actorOf(Props[TimePerLineActor]) ! TimeRequest(data_with_delay)
    }
    case TimeReport(line) =>{
      theMaster.map(_ ! Tell(line))
    }
  }
}
class ttetRawData(rawString: String) {

  val splitted = rawString.split(",")
  def imei: String = splitted(1)
  def lat: Double = splitted(2).toDouble
  def lng: Double = splitted(3).toDouble
  def speed: Float = splitted(4).toFloat
  def direction: Float = splitted(5).toFloat
  def timestring: Long = splitted(9).toLong * 1000
  def datetime = new Date(timestring)

  def ==(that: ttetRawData): Boolean = datetime.compareTo(that.datetime) == 0
  def !=(that: ttetRawData): Boolean = datetime.compareTo(that.datetime) != 0
  def <(that: ttetRawData): Boolean = datetime.compareTo(that.datetime) < 0
  def <=(that: ttetRawData): Boolean = datetime.compareTo(that.datetime) <= 0
  def >(that: ttetRawData): Boolean = datetime.compareTo(that.datetime) > 0
  def >=(that: ttetRawData): Boolean = datetime.compareTo(that.datetime) > 0

  override def toString: String = datetime.toString
}

object GPS_Simulator extends App {
  val raw_data = Source.fromFile("src/test/dataset/data.csv")
    .getLines.drop(1)
    .map(line => new ttetRawData(line))
    .toList

  val initial_point = raw_data.minBy(_.datetime).timestring
  val curr_time = new Date().getTime()
  val datastream =  raw_data.sortWith(_ < _)
    .map({d => (curr_time+(d.timestring-initial_point),d) })

  val system = ActorSystem("TimedPrint")
  import TimeActor._
  implicit val timeout = Timeout(60 seconds)
  val LinesFutures = datastream.map({
    data_with_delay =>  {
      system.actorOf(Props[TimeActor]) ? TimePrint(data_with_delay)
    }
  })

  LinesFutures.foreach {case futLn =>
    futLn.onSuccess{
      case Tell(ln) => {
        println(s"${ln}")
      }
    }
  }
}
