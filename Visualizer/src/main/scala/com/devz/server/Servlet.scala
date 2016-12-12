package com.devz.server

import java.util.Date
import java.util.concurrent.TimeUnit
import scala.collection.mutable.Buffer
import scala.collection.mutable.ArrayBuffer

import org.scalatra._
import scala.util.parsing.json._

class ttetRawData(rawString: String) extends Ordered[ttetRawData] {

  val splitted = rawString.split(",")
  def imei: String = splitted(1)
  def lat: Double = splitted(2).toDouble
  def lng: Double = splitted(3).toDouble
  def speed: Float = splitted(4).toFloat
  def direction: Float = splitted(5).toFloat
  def timestring: Long = splitted(9).toLong * 1000
  def datetime = new Date(timestring)

  def compare(that: ttetRawData): Int = datetime.compareTo(that.datetime)

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
	var allCars = Buffer[ttetRawData]()
	var count = 0
	var firstTime: Long = 0
	var realInitTime: Long = 0

	get("/") {
		contentType = "text/html"
		new java.io.File(servletContext.getResource("/WEB-INF/html/visualizer.html").getFile)
	}

	get("/plot") {
		contentType = "text/html"
		val currTime = new Date().getTime()
		allCars = allCars.groupBy(_.imei)
					   .mapValues(_.max)
					   .values
					   .filter(_.timestring >= (firstTime + (currTime - realInitTime) - 20000))
					   .toBuffer[ttetRawData]
		println(allCars.length)

		val lat0 = request.getParameter("lat0").toDouble
		val lat1 = request.getParameter("lat1").toDouble
		val lng0 = request.getParameter("lng0").toDouble
		val lng1 = request.getParameter("lng1").toDouble
		def isInRect(LAT: Double,LNG: Double): Boolean ={
			(lat0 <= LAT && LAT <= lat1) && (lng0 <= LNG && LNG <= lng1)
		}
		val inRect = allCars.filter(car => isInRect(car.lat,car.lng))

		// println("["+inRect.mkString(",")+"]")
		"["+inRect.map(_.toJSONString).mkString(",")+"]"
		// "["+allCars.map(_.toJSONString).mkString(",")+"]"
	}

	post("/gpsInput") {
		val txt = request.body
		val data = new ttetRawData(txt)
		if (count==0) {
			firstTime = data.timestring
			realInitTime = new Date().getTime()
		}
		count = count + 1

		println(data)
		allCars += data
	}
}
