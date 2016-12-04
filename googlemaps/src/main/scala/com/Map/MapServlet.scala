package com.Map

import org.scalatra._
import org.scalajs.dom
import dom.document
import scala.scalajs.js.JSApp

class MapServlet extends GooglemapsStack {
	get("/") {
	contentType="text/html"
	<html>
		<body>
			<h1>My First Google Map</h1>
			<script src="https://maps.googleapis.com/maps/api/js"></script>
			<div id="map_canvas" style="width:100%;height:500px"></div>

			<button id="click-me-button" type="button"
      			onclick="com.Map.showMap().addClickedMessage()">Click me!</button>

			<script type="text/javascript" src="./target/scala-2.11/googlemaps-fastopt.js"></script>

			<script type="text/javascript">
				com.Map.showMap().main();
				com.Map.initialize(42.2781410217,-74.9159927368);
			</script>
		</body>
	</html>
	}
}
