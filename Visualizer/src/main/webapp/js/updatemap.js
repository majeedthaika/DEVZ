function refreshGMapMarkers(){
  $.ajax({
    url:  '/plot',
    type: 'GET',
    data: {
            'lat0' : getLat0(),
            'lat1' : getLat1(),
            'lng0' : getLng0(),
            'lng1' : getLng1(),
            'day' : $('#day').combodate('getValue'),
            'time' : $('#time').combodate('getValue'),
            'window' : $("#windowsize").text(),
            'slide' : slidestep.value
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

$('#generateButton').on('click', function(event){
  setNewBounds({
    north: parseFloat(document.getElementById("lat1").value),
    south: parseFloat(document.getElementById("lat0").value),
    east: parseFloat(document.getElementById("lng1").value),
    west: parseFloat(document.getElementById("lng0").value)
  });
});

$('#decreasebutton').on('click', function(event){
  updateDateTime(-parseInt(document.getElementById("slidestep").value))
  refreshGMapMarkers()
});

$('#increasebutton').on('click', function(event){
  updateDateTime(parseInt(document.getElementById("slidestep").value))
  refreshGMapMarkers()
});

function updateDateTime(secondChange){
  var curTime = new Date(
    Date.parse("2015-06-".concat(document.getElementById("day").value," ",
                                 document.getElementById("time").value)));
  var newTime = new Date(curTime.setSeconds(curTime.getSeconds() + secondChange));
  if (newTime.getDate() == parseInt(document.getElementById("day").value)){
    $('#time').combodate('setValue',newTime);
  }
}
