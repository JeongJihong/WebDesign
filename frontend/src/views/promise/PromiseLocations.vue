<template>
  <div class="m-4">
    <div class="d-flex" style="justify-content: space-between">
      <div class="d-flex">
        <button @click="goBack"><b-icon icon="arrow-left" class="fs-1 me-4"></b-icon></button>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <h1>
          다들 어디
        </h1>
      </div>
      <b-icon icon="arrow-clockwise" animation="spin" font-scale="2"></b-icon>
    </div>
    <div class="my-5">
      <div id="map" style="padding-bottom: 56.25%; width: 100%; height: 100%;"></div>
    </div>
    <div>
      <div v-for="person in 3" :key="person">
        <div class="d-flex mx-4 my-4" style="justify-content: space-between;">
          <img src="@/assets/images/profile_default.png" alt="image" style="width: 40px; height: 40px" >
          <p>닉네임</p>
          <p>20분전</p>
          <p>대전 유성구</p>
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
      'nicknames' : ['김백준','신형식','이두호','정종우','정지홍'],
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
          level: 3, // 지도의 확대 레벨
        }

      var map = new kakao.maps.Map(mapContainer, mapOption)
      var point;
      for (point = 0; point < 5; point++) {
        const markerPosition = new kakao.maps.LatLng(37.564343 + point * 0.0003, 126.947613 - point * 0.0003);

        const marker = new kakao.maps.Marker({
          position: markerPosition
        });
        marker.setMap(map)

        var iwContent = '<div style="padding:5px;">'+this.nicknames[point]+'<br>'+(point+2)+'분전</div>',
            iwPosition = new kakao.maps.LatLng(37.564343 + point * 0.0003, 126.947613 - point * 0.0003);
      
        var infowindow = new kakao.maps.InfoWindow({
          position : iwPosition, 
          content : iwContent 
        });
        
        infowindow.open(map, marker); 
      }
      

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