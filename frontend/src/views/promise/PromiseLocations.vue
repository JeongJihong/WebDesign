<template>
  <div class="m-4">
    <div class="d-flex" style="justify-content: space-between">
      <div class="d-flex">
        <button @click="goBack"><b-icon icon="arrow-left" class="fs-1 me-4"></b-icon></button>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <h2 class="fw-bold">
          다들 어디
        </h2>
      </div>
      <b-icon icon="arrow-clockwise" animation="spin" font-scale="2"></b-icon>
    </div>
    <hr>
    <div class="my-2">
      <div id="map" style="padding-bottom: 56.25%; width: 100%; height: 100%;"></div>
    </div>
    <hr>
    <div>
      <div v-for="person in 4" :key="person">
        <div class="d-flex flex-row mx-4 my-4" style="justify-content: space-between; text-align:center">
          <img src="@/assets/images/profile_default.png" alt="image" style="width: 35px; height: 35px;" >
          <p class="fw-bold">{{ nicknames[person-1]}}</p>
          <p class="fw-bold" style="color:green;">{{ times[person-1]}}분전</p>
          <p>{{ places[person-1]}}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'


export default {
  data () {
    return {
      nicknames : ['김백준','신형식','이두호','정지홍'],
      times: [28,16,10,21],
      places: ['충현동','신촌동','신촌동','아현동']
    }
  },
  methods: {
    goBack() {
      this.$router.go(-1)
    },
    initMap() {
      var mapContainer = document.getElementById('map'), // 지도를 표시할 div
        mapOption = {
          center: new kakao.maps.LatLng(37.564343, 126.947613), // 지도의 중심좌표
          level: 5, // 지도의 확대 레벨
        }

      var map = new kakao.maps.Map(mapContainer, mapOption)
      // 약속장소
      // var markerPosition = new kakao.maps.LatLng(37.564343, 126.947613);
      // var marker = new kakao.maps.Marker({
      //   position: markerPosition
      // });
      // marker.setMap(map)

      var point = 0;

      var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_red.png', // 마커이미지의 주소입니다    
          imageSize = new kakao.maps.Size(44, 50), // 마커이미지의 크기입니다
          imageOption = {offset: new kakao.maps.Point(27, 27)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.
            
      // 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
      var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption),
          markerPosition = new kakao.maps.LatLng(37.564343, 126.947613); // 마커가 표시될 위치입니다

      // 마커를 생성합니다
      var marker = new kakao.maps.Marker({
          position: markerPosition, 
          image: markerImage // 마커이미지 설정 
      });
      marker.setMap(map);  

      // 첫번째 인간
      var markerPosition1 = new kakao.maps.LatLng(37.564343, 126.952773);
      var marker1 = new kakao.maps.Marker({
        position: markerPosition1,
        title: 'hi'
      });
      marker1.setMap(map)
      var iwContent1 = '<div class="d-flex px-3" style="padding:5px; width:150px; justify-content: space-between; align-items:top;">'+ 
      '<span>'+'<font size="3em" color="black">'+this.nicknames[point]+'</font>'+'</span>'+
      '<span>'+'<font size="2em" color="green">도착'+this.times[point]+'분전</font></span></div>',
          iwPosition1 = new kakao.maps.LatLng(37.565343, 126.948613);
      var infowindow1 = new kakao.maps.InfoWindow({
        position : iwPosition1, 
        content : iwContent1 
      });
      infowindow1.open(map, marker1);
      point = point + 1

      // 두번째 인간
      var markerPosition2 = new kakao.maps.LatLng(37.560743, 126.942813);
      var marker2 = new kakao.maps.Marker({
        position: markerPosition2
      });
      marker2.setMap(map)
      var iwContent2 = '<div class="d-flex px-3" style="padding:5px; width:150px; justify-content: space-between; align-items:top;">'+ 
      '<span>'+'<font size="3em" color="black">'+this.nicknames[point]+'</font>'+'</span>'+
      '<span>'+'<font size="2em" color="green">도착'+this.times[point]+'분전</font></span></div>',
          iwPosition2 = new kakao.maps.LatLng(37.560743, 126.942813);
      // var iwContent2 = '<div style="padding:5px;">'+this.nicknames[point]+'<br>'+(point+2)+'분전</div>',
      //     iwPosition2 = new kakao.maps.LatLng(37.563343, 126.946613);
      var infowindow2 = new kakao.maps.InfoWindow({
        position : iwPosition2,
        content : iwContent2 
      });
      infowindow2.open(map, marker2);
      point = point + 1

      // 세번째 인간
      var markerPosition3 = new kakao.maps.LatLng(37.564743, 126.944013);
      var marker3 = new kakao.maps.Marker({
        position: markerPosition3
      });
      marker3.setMap(map)
      var iwContent3 = '<div class="d-flex px-3" style="padding:5px; width:150px; justify-content: space-between; align-items:top;">'+ 
      '<span>'+'<font size="3em" color="black">'+this.nicknames[point]+'</font>'+'</span>'+
      '<span>'+'<font size="2em" color="green">도착'+this.times[point]+'분전</font></span></div>',
          iwPosition3 = new kakao.maps.LatLng(37.560743, 126.942813);
      // var iwContent2 = '<div style="padding:5px;">'+this.nicknames[point]+'<br>'+(point+2)+'분전</div>',
      //     iwPosition2 = new kakao.maps.LatLng(37.563343, 126.946613);
      var infowindow3 = new kakao.maps.InfoWindow({
        position : iwPosition3,
        content : iwContent3 
      });
      infowindow3.open(map, marker3);
      point = point + 1

      // 네번째 인간
      var markerPosition4 = new kakao.maps.LatLng(37.560543, 126.949913);
      var marker4 = new kakao.maps.Marker({
        position: markerPosition4
      });
      marker4.setMap(map)
      var iwContent4 = '<div class="d-flex px-3" style="padding:5px; width:150px; justify-content: space-between; align-items:top;">'+ 
      '<span>'+'<font size="3em" color="black">'+this.nicknames[point]+'</font>'+'</span>'+
      '<span>'+'<font size="2em" color="green">도착'+this.times[point]+'분전</font></span></div>',
          iwPosition4 = new kakao.maps.LatLng(37.560743, 126.942813);
      // var iwContent2 = '<div style="padding:5px;">'+this.nicknames[point]+'<br>'+(point+2)+'분전</div>',
      //     iwPosition2 = new kakao.maps.LatLng(37.563343, 126.946613);
      var infowindow4 = new kakao.maps.InfoWindow({
        position : iwPosition4,
        content : iwContent4 
      });
      infowindow4.open(map, marker4);

      // for (point = 0; point < 5; point++) {
      //   const markerPosition = new kakao.maps.LatLng(37.564343 + point * 0.0003, 126.947613 - point * 0.0003);

      //   const marker = new kakao.maps.Marker({
      //     position: markerPosition
      //   });
      //   marker.setMap(map)

      //   var iwContent = '<div style="padding:5px;">'+this.nicknames[point]+'<br>'+(point+2)+'분전</div>',
      //       iwPosition = new kakao.maps.LatLng(37.564343 + point * 0.0003, 126.947613 - point * 0.0003);
      
      //   var infowindow = new kakao.maps.InfoWindow({
      //     position : iwPosition, 
      //     content : iwContent 
      //   });
        
      //   infowindow.open(map, marker); 
      // }
      

    }
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement('script')
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap)
      script.src =
        `http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${process.env.VUE_APP_MAP_API}`
      document.head.appendChild(script)
  }
}
}
</script>

<style>

</style>