// This example adds a user-editable rectangle to the map.
// When the user changes the bounds of the rectangle,
// an info window pops up displaying the new bounds.

var rectangle;
var infoWindow;
var map;

function initMap() {
  var bounds = {
    north: parseFloat(document.getElementById("lat1").value),
    south: parseFloat(document.getElementById("lat0").value),
    east: parseFloat(document.getElementById("lng1").value),
    west: parseFloat(document.getElementById("lng0").value)
  };

  map = new google.maps.Map(document.getElementById('map'), {
    center: {lat: (bounds.north+bounds.south)/2, lng: (bounds.east+bounds.west)/2},
    zoom: 15
  });

  // Define the rectangle and set its editable property to true.
  rectangle = new google.maps.Rectangle({
    bounds: bounds,
    editable: true,
    draggable: true,
    strokeColor: '#FF0000',
    strokeOpacity: 1,
    strokeWeight: 2,
    fillColor: '#000000',
    fillOpacity: 0,
    map: map,
  });

  // Add an event listener on the rectangle.
  rectangle.addListener('bounds_changed', showNewRect);

  // Define an info window on the map.
  infoWindow = new google.maps.InfoWindow();

  var contentString = '<b>Rectangle Co-ordinates:</b><br>' +
      'North: ' + bounds.north + ', East: ' + bounds.east + '<br>' +
      'South: ' + bounds.south + ', West: ' + bounds.west;

  infoWindow.setContent(contentString);
  infoWindow.setPosition({lat: bounds.north, lng: bounds.east});

  infoWindow.open(map);
  resetMarkers();
}
// Show the new coordinates for the rectangle in an info window.

/** @this {google.maps.Rectangle} */
function showNewRect(event) {
  var ne = rectangle.getBounds().getNorthEast();
  var sw = rectangle.getBounds().getSouthWest();

  var contentString = '<b>Rectangle Co-ordinates:</b><br>' +
      'North: ' + ne.lat() + ', East: ' + ne.lng() + '<br>' +
      'South: ' + sw.lat() + ', West: ' + sw.lng();

  // Set the info window's content and position.
  infoWindow.setContent(contentString);
  infoWindow.setPosition(ne);

  document.getElementById("lat1").value = ne.lat();
  document.getElementById("lng0").value = sw.lng();
  document.getElementById("lat0").value = sw.lat();
  document.getElementById("lng1").value = ne.lng();

  infoWindow.open(map);

  refreshGMapMarkers();
}

function setNewBounds(newbounds) {
  rectangle.setBounds(newbounds);
  map.setCenter({lat: (newbounds.north+newbounds.south)/2, lng: (newbounds.east+newbounds.west)/2});
  setTimeout(function() { refreshGMapMarkers(); }, 2000);
}

function getMap() {
  return map;
}

function getLat0(event) {
  return rectangle.getBounds().getSouthWest().lat();
}

function getLat1(event) {
  return rectangle.getBounds().getNorthEast().lat();
}

function getLng0(event) {
  return rectangle.getBounds().getSouthWest().lng();
}

function getLng1(event) {
  return rectangle.getBounds().getNorthEast().lng();
}
