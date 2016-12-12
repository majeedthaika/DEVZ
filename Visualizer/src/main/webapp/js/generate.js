$('#generateButton').on('click', function(event){
	$.ajax({
	    url:  '/plot',
	    type: 'GET',
	    data: {
	            'lat0' : getLat0(),
	            'lat1' : getLat1(),
	            'lng0' : getLng0(),
	            'lng1' : getLng1()
	          },
	    success: function(data){
	      resetMarkers();
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