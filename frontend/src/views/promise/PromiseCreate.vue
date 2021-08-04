<template>
  <div>약속생성
    <form action="">
      Title : <input type="text">
      약속인원 : <input type="text">
      <br>
      <label for="타입">약속유형 </label>
      <select name="" id="타입">
        <option value="game">게임</option>
        <option value="food">식사</option>
        <option value="study">공부</option>
        <option value="fitness">운동</option>
      </select>
      <br>
      약속 시간: <input type="date">
      <br>
      <input type="radio" id="화상"><label for="화상">화상여부</label>
      <br>
      <div id="map" style="padding-bottom: 56.25%; width: 100%; height: 100%;"></div>
      <br>
      <button>약속 만들기</button>
    </form>



  </div>
</template>
<script>
export default {
  data(){
    return{
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
  },
  methods: {
    initMap() {
      var mapContainer = document.getElementById('map'), // 지도를 표시할 div
          mapOption = {
            center: new kakao.maps.LatLng(37.564343, 126.947613), // 지도의 중심좌표
            level: 3, // 지도의 확대 레벨
          }
      var map = new kakao.maps.Map(mapContainer, mapOption)
      // var marker = new kakao.maps.Marker({
      //     position: markerPosition
      // });

      // // 마커가 지도 위에 표시되도록 설정합니다
      // marker.setMap(map)
    }
  },
}
</script>

<style>

</style>