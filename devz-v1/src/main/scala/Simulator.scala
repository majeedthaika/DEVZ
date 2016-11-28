import scala.concurrent.ExecutionContext.Implicits.global
import java.util.Date
import java.util.concurrent.TimeUnit
import scala.io.Source
import akka.pattern.ask
import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.util.Timeout
import scala.concurrent.duration._

object TimePerLineActor{
  case class TimeRequest(line: ttetRawData)
  case class TimeReport(line: ttetRawData) // When done
}

class TimePerLineActor extends Actor{
  import TimePerLineActor._
  def receive = {
    case TimeRequest(line) => {
      var Rand = scala.util.Random
      var random = Rand.nextInt(10) + 3
      println(random + " seconds wait")
      TimeUnit.SECONDS.sleep(random);
      sender ! TimeReport(line)
      context.stop(self) //no need message
    }
  }
}

object TimeActor{
  case class TimePrint(line: ttetRawData)
  case class Tell(line:ttetRawData)
}

class TimeActor extends Actor{
  import TimeActor._
  import TimePerLineActor._
  var theMaster: Option[ActorRef] = None
  def receive = {
    case TimePrint(line) => {
      theMaster = Some(sender)
      context.actorOf(Props[TimePerLineActor]) ! TimeRequest(line)
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

object Main extends App {
  val raw_data = Source.fromFile("src/main/data.csv")
    .getLines.drop(1)
    .map(line => new ttetRawData(line))
    .toList

  val initial_point = raw_data.minBy(_.datetime).timestring
  val normalized =  raw_data.sortWith(_ < _)
  val system = ActorSystem("TimedPrint")
  import TimeActor._
  implicit val timeout = Timeout(60 seconds)
  val LinesFutures = normalized.map({
    line =>  system.actorOf(Props[TimeActor]) ? TimePrint(line)
  })

  LinesFutures.foreach {case futLn =>
    futLn.onSuccess{
      case Tell(ln) => println(s"${ln}")
    }
  }


  //                            .map(_.timestring - initial_point)
  //  println(normalized)
  //  normalized.foreach(println)

}
