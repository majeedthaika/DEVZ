package com.Map

import org.scalajs.dom
import dom.document
import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport
import scala.scalajs.js.Dynamic.{ global => g, literal => lit, newInstance => jsnew }

object showMap extends JSApp {

	def appendPar(targetNode: dom.Node, text: String): Unit = {
		val parNode = document.createElement("p")
		val textNode = document.createTextNode(text)
		parNode.appendChild(textNode)
		targetNode.appendChild(parNode)
	}

	@JSExport
	def addClickedMessage(): Unit = {
	  appendPar(document.body, "You clicked the button!")
	}

	@JSExport
	def initialize(lat: Double, long: Double) = {
		val map_canvas = document.getElementById("map_canvas")
		val map_options = lit(center = (jsnew(g.google.maps.LatLng))(lat, long), zoom = 3, mapTypeId = g.google.maps.MapTypeId.ROADMAP)
		val gogleMap =  (jsnew(g.google.maps.Map))(map_canvas, map_options)
		val marker = (jsnew(g.google.maps.Marker))(lit(map = gogleMap, position = (jsnew(g.google.maps.LatLng)(lat, long))))
	}

	def main(): Unit = {
		appendPar(document.body,"Hello!")
	}
	
}