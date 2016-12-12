package com.devz.server

import java.util.Date
import java.util.concurrent.TimeUnit
import scala.collection.mutable.ArrayBuffer

import org.scalatra._
import scala.util.parsing.json._

class ttetRawData(rawString: String)  {

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

  def toJSONString: String = s"{${'"'}lat${'"'}: $lat, ${'"'}lng${'"'}: $lng, ${'"'}speed${'"'}: $speed, ${'"'}rotation${'"'}: $direction, ${'"'}die${'"'}: 0}"
  override def toString: String = datetime.toString+","+rawString
}

class Servlet extends TestStack {
	// val test_J = """
	// [
	// 	{"lat": 13.78, "lng": 100.470, "speed": 1, "rotation": 0, "die": 1},
	// 	{"lat": 13.78, "lng": 100.468, "speed": 1, "rotation": 0, "die": 0},
	// 	{"lat": 13.78, "lng": 100.466, "speed": 1, "rotation": 0, "die": 1},
	// 	{"lat": 13.78, "lng": 100.472, "speed": 1, "rotation": 0, "die": 0},
	// 	{"lat": 13.78, "lng": 100.474, "speed": 1, "rotation": 0, "die": 1}
	// ]
	// """
	var toSend = ArrayBuffer[String]()
	var count = 0

	get("/") {
		contentType = "text/html"
		new java.io.File(servletContext.getResource("/WEB-INF/html/visualizer.html").getFile)
	}

	get("/testGen") {
		contentType = "text/html"
		println(count)
		// println("["+toSend.mkString(",")+"]")
		"["+toSend.mkString(",")+"]"
	}

	post("/gpsInput") {
		val txt = request.body
		val data = new ttetRawData(txt)
		count = count + 1

		println(data)
		toSend += data.toJSONString
	}
}
