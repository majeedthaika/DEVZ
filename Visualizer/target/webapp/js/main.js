console.log("hi outside");

$('#generateButton').on('click', function(event) {
    $.ajax({
        url:  '/plot',
        type: 'GET',
        data: { 'lat0' : $('#lat0').val(),
                'lat1' : $('#lat1').val(),
                'lng0' : $('#lng0').val(),
                'lng1' : $('#lng1').val(),
                'day' : $('#day').val(),
                'hr' : $('#hr').val(),
                'mi' : $('#mi').val(),
                'sec' : $('#sec').val(),
                'window' : $('#window').val() },
        success: function(data){
            $('#plotarea').html('<img src="data:image/png;base64,' + data + '" />');
        },
        error: function(){
            alert("error");
        }
    });
});
