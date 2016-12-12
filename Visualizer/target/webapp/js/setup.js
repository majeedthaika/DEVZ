$(function(){
  $('#time').combodate({
    firstItem: 'name',
    hourStep: 1,
    minuteStep: 1,
    secondStep: 1
  });
  $('#day').combodate({
    firstItem: 'name',
    dayStep: 1
  });
  var handle = $("#windowsize");
  $("#slider").slider({
    range: "min",
    value: 60,
    min: 1,
    max: 3600,
    create: function() {
      handle.text($(this).slider("value"));
    },
    slide: function(event, ui) {
      handle.text(ui.value);
    },
    change: function(e,ui){
      refreshGMapMarkers();
    }
  });
});
