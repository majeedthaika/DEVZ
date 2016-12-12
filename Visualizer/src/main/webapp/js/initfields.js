// var QueryString = function () {
$(function (){
  // This function is anonymous, is executed immediately and
  // the return value is assigned to QueryString!
  var query_string = {};
  var query = window.location.search.substring(1);
  var vars = query.split("&");
  for (var i=0;i<vars.length;i++) {
    var pair = vars[i].split("=");
        // If first entry with this name
    if (typeof query_string[pair[0]] === "undefined") {
      query_string[pair[0]] = decodeURIComponent(pair[1]);
        // If second entry with this name
    } else if (typeof query_string[pair[0]] === "string") {
      var arr = [ query_string[pair[0]],decodeURIComponent(pair[1]) ];
      query_string[pair[0]] = arr;
        // If third or later entry with this name
    } else {
      query_string[pair[0]].push(decodeURIComponent(pair[1]));
    }
  }

  document.getElementById("lat0").value = ("lat0" in query_string) ? query_string["lat0"] : '13.77419';
  document.getElementById("lat1").value = ("lat1" in query_string) ? query_string["lat1"] : '13.78677';
  document.getElementById("lng0").value = ("lng0" in query_string) ? query_string["lng0"] : '100.46821';
  document.getElementById("lng1").value = ("lng1" in query_string) ? query_string["lng1"] : '100.48190';
  // if ("day" in query_string){ $('#day').combodate('setValue',query_string["day"]); }
  // if ("time" in query_string){ $('#time').combodate('setValue',query_string["time"]); }
  // if ("window" in query_string){ $("#windowsize").text(query_string["window"]); $("#slider").slider({value: query_string["window"]}); }
  // document.getElementById("slidestep").value = ("slide" in query_string) ? query_string["slide"] : '60';

  // return query_string;
});
