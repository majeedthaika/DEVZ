package com.test

import org.scalatra._
import scala.util.parsing.json._


class Servlet extends TestStack {
	val test_J = """
	[
		{"lat": 13.78, "lng": 100.470, "speed": 1, "rotation": 0, "die": 1},
		{"lat": 13.78, "lng": 100.468, "speed": 1, "rotation": 0, "die": 0},
		{"lat": 13.78, "lng": 100.466, "speed": 1, "rotation": 0, "die": 1},
		{"lat": 13.78, "lng": 100.472, "speed": 1, "rotation": 0, "die": 0},
		{"lat": 13.78, "lng": 100.474, "speed": 1, "rotation": 0, "die": 1}
	]
	"""
	// val JSon_ = parse(test)

	get("/") {
		contentType = "text/html"
		new java.io.File(servletContext.getResource("/WEB-INF/html/visualizer.html").getFile)
	}

	get("/testGen") {
		contentType = "text/html"
		test_J
	}

}
