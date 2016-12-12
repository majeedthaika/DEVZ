$('#generateButton').on('click', function(event){
	$.ajax({
	    url:  '/testGen',
	    type: 'GET',
	    data: {
	            // 'lat0' : getLat0(),
	            // 'lat1' : getLat1(),
	            // 'lng0' : getLng0(),
	            // 'lng1' : getLng1(),
	            // 'day' : $('#day').combodate('getValue'),
	            // 'time' : $('#time').combodate('getValue'),
	            // 'window' : $("#windowsize").text(),
	            // 'slide' : slidestep.value
	          },
	    success: function(data){
	      console.log(JSON.parse(data));
	      // resetMarkers();
	      setMarkers(JSON.parse(data));
	    },
	    error: function(){
	      alert("No cars found at given co-ordinates and/or time\nTry Re-Generating/Change to new co-ordinates");
	    }
	  });
	});

function getMarkerColor(speed) {
  if (speed <= 10 ) { return 'firebrick'} else
  if (speed <= 60) { return 'coral' } else
  { return 'limegreen' }
}

markers = []

function setMarkers(locations) {
    for (var i = 0; i < locations.length; i++) {
        var markerInfo = locations[i];
        var LatLng = new google.maps.LatLng(parseFloat(markerInfo.lat), parseFloat(markerInfo.lng));
        var marker = new google.maps.Marker({
          map: getMap(),
          position: LatLng,
          icon: {
            path: 'M28 36 L32 26 L36 36 L32 34 L28 36',
            anchor: new google.maps.Point(32, 32),
            fillColor: getMarkerColor(parseFloat(markerInfo.speed)),
            fillOpacity: 1,
            rotation: parseInt(markerInfo.rotation)
          }
        });
        markers.push([marker, markerInfo.die]);
    }
    setTimeout(function(){ 
      for (var i = 0; i < markers.length; i++) {
        if (markers[i][1] == 1){
          markers[i][0].setMap(null); 
        }
      }
    }, 5000);
}