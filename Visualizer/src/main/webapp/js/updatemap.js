function refreshGMapMarkers(){
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
};

setInterval ( refreshGMapMarkers, 5000 );

$('#generateButton').on('click', function(event){
  setNewBounds({
    north: parseFloat(document.getElementById("lat1").value),
    south: parseFloat(document.getElementById("lat0").value),
    east: parseFloat(document.getElementById("lng1").value),
    west: parseFloat(document.getElementById("lng0").value)
  });
});
