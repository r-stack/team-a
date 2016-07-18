var ColorArray = ["#f00", "#3a3a91","#ff00ff"];
var parseData = null;

//HTMLへアクセス
function getJson(){
	//var return_data = $.get("http://172.20.10.6:4567/users");

	$.ajax({
		type:'GET',
        url:'http://localhost:4567/users',
        dataType: 'html',
        success: function(data) {
        	parseJson(data);
        }
   });

}

function getRecipe(){
	//var return_data = $.get("http://172.20.10.6:4567/users");

	$.ajax({
		type:'GET',
        url:'http://localhost:4567/recipe',
        dataType: 'html',
        success: function(data) {
        	parseJson(data);
        }
   });

}

function parseJson(data){
	//var obj = $.parseJSON('{"name":"John"}');
	parseData = $.parseJSON(data);
}


function jsonParseTest(){
	var str= '[{"userid":"1", "username":"test", "latitude":"35.621509", "longitude":"139.731826", "marriage":"0", "kids":"0", "skill":"0", "home":"0"},{"userid":"2", "username":"hoge", "latitude":"35.623855", "longitude":"139.728768", "marriage":"0", "kids":"0", "skill":"0", "home":"0"},{"userid":"3", "username":"fuga", "latitude":"35.620358", "longitude":"139.722738", "marriage":"0", "kids":"0", "skill":"0", "home":"0"}]';
	parseData = $.parseJSON(str);
};


function drawMap(){
	//sonParseTest();

	var mapOptions = {
		center: new google.maps.LatLng(35.044624, 138.48058),
		mapTypeId: google.maps.MapTypeId.ROADMAP,
	};
	map = new google.maps.Map(document.getElementById("mapArea"), mapOptions);
	infoWindow2 = new google.maps.InfoWindow({noSupress: true});


	var aryTest = new Array();
	var tyukan = new Array(parseData.length);

	for(var i in parseData )
	{

		if(i == 1){
			var wayPoints = new Array();
				var point = { location: new google.maps.LatLng(35.621843885938375,139.7281810993809) };
				wayPoints.push(point);
			tyukan[i] = wayPoints;
		}else{
			tyukan[i] = null;
		}

		var roteInfo = {
			"start" : new google.maps.LatLng(parseData[i].latitude, parseData[i].longitude),
			//"start" :  new google.maps.LatLng(parseData[i].latitude, parseData[i].longitude),
			"end" : new google.maps.LatLng(35.618095233860025, 139.7326717609758),
			"color" : ColorArray[i]
		};
		aryTest.push(roteInfo);
	}


/*
	var ary = [
		{
			"start" :  new google.maps.LatLng(35.044624, 138.480583),
			"end" : new google.maps.LatLng(35.200784, 138.662870),
			"icon" : "http://purazumakoi.info/sample/google_maps_api/direction-root/image/car.gif",
			"color" : "#f00"
		},
		{
			"start" :  new google.maps.LatLng(35.178424, 138.676367),
			"end" : new google.maps.LatLng(35.149800, 138.866934),
			"icon" : "http://purazumakoi.info/sample/google_maps_api/direction-root/image/car.gif",
			"color" : "#3a3a91"
		},
		{
			"start" :  new google.maps.LatLng(35.263073, 138.911010),
			"end" : new google.maps.LatLng(35.422982, 139.369972),
			"icon" : "http://purazumakoi.info/sample/google_maps_api/direction-root/image/car.gif",
			"color" : "#ff00ff"
		}
	];
*/

	for(var i in aryTest){
		fCalcRoute(aryTest[i]['start'], aryTest[i]['end'], aryTest[i]['icon'], aryTest[i]['color'], tyukan[i]);
	}
};

function fCalcRoute(start, end, icon, color, wayPoints) {

	// ルート表示 設定
	rendererOptions = {
		draggable: true,
		preserveViewport: false
	};
	var directionsDisplay =	 new google.maps.DirectionsRenderer(rendererOptions);
	directionsDisplay.setOptions(
		{
			polylineOptions: { strokeColor: color, strokeWeight:3 }
		});
	var directionsService =	 new google.maps.DirectionsService();


	// ルート間 ポイント設定
	if( wayPoints != null )
	{
		var request = {
			origin: start,
			destination: end,
			waypoints:wayPoints,
			travelMode: google.maps.DirectionsTravelMode.DRIVING,
			optimizeWaypoints: true
		};
	}else{
		var request = {
			origin: start,
			destination: end,
			travelMode: google.maps.DirectionsTravelMode.DRIVING,
			optimizeWaypoints: true
		};
	}

	directionsService.route(request,
			function(response,status){
				if (status == google.maps.DirectionsStatus.OK){


					directionsDisplay.setDirections(response);
				}
			}
	);
	directionsDisplay.setMap(map);

}
