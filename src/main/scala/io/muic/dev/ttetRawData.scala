package io.muic.dev

import java.util.Date

class ttetRawData(rawString: String) {

  val splitted = rawString.split(",")

  def imei: String = splitted(1)
  def lat: Double = splitted(2).toDouble
  def lng: Double = splitted(3).toDouble
  def speed: Float = splitted(4).toFloat
  def direction: Float = splitted(5).toFloat

  private val ts: Long = splitted(9).toLong*1000
  def datetime = new Date(ts)
}

object Main extends App {
  val record = new ttetRawData("994,10014599,13.89743,100.58785,20.0,354.0,0.0,0,1,1433091602,9")
  println(record.datetime)
}