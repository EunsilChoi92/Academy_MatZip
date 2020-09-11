<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="sectionContainerCenter">
	<div id="mapContainer" style="width:100%; height:100%;"></div>
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=faf4b3a5a0e4ed4c610c95db44ba090d"></script>
	<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
	<script>
		const options = { //지도를 생성할 때 필요한 기본 옵션
			center: new kakao.maps.LatLng(35.865824, 128.594012), //지도의 중심좌표.
			level: 5 //지도의 레벨(확대, 축소 정도)
		};
	
		var map = new kakao.maps.Map(mapContainer, options); //지도 생성 및 객체 리턴
		
		console.log(map.getCenter());
		
		function getRestaurantList() {
			axios.get('/restaurant/ajaxGetList').then(function(res){
				console.log(res.data);
				
				res.data.forEach(function(item) {
					const na = {
							'Ga': item.lng,
							'Ha': item.lat
					}
					console.log(`na : ${na}`);
					const marker = new kakao.maps.Marker({
						position: na
					});
					
					marker.setMap(map);
				})
			});
		}
	</script>
</div>