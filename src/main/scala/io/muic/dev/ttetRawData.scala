package io.muic.dev

import java.util.Date

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
