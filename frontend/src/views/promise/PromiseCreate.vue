<template>
  <div style="margin-left:10%; margin-right:10%;">
    <br>
    <b-form>
      <b-form-group id="input-group-1" label="Title:" label-for="input-1">
        <b-form-input
          id="input-1"
          v-model="title"
          placeholder="Enter Title"
          required
        ></b-form-input>
      </b-form-group>
      <br>
      <b-form-group id="input-group-2" label="Type:" label-for="input-2">
        <b-form-select
          id="input-2"
          v-model="Type"
          :options="Types"
          required
        ></b-form-select>
      </b-form-group>
      <br>
      <b-form-group id="input-group-3" label="인원" label-for="input-3">
        <b-form-spinbutton id="input-3" v-model="headCount" min="2" max="100"></b-form-spinbutton>
      </b-form-group>
      <br>
      
      <h5 class="mt-3">화상회의 여부</h5>
      <b-button :pressed.sync="virtual" variant="primary">{{ virtual }}</b-button>
      <br>
      <b-form-group id="input-group-5">
        <label :for="`type-date`">날짜:</label>
        <b-form-datepicker id=type-date v-model="promiseDate"></b-form-datepicker>
        <label :for="`type-time`">시간:</label>
        <b-time id=type-time  v-model="promiseTime" ></b-time>
      </b-form-group>
      <br>
      <div v-if="!virtual">
        <label for="around">주변 장소 검색</label><input id='around'  v-model="promiseAroundPlace" type="text">
        <button type="button" @click='initMap()' id='around'>검색</button>
        <hr>
        <br>
        <div>
          약속 장소 정하기 : <p id='place'></p>
        </div>
        <div class="map_wrap">
        <div id="map" style="width:100%;height:100%;position:relative;overflow:hidden;"></div>
        </div>
        <hr>
        <br>
      </div>
      <div class="d-flex justify-content-center">
        <b-button @click="promiseCreate()" variant="primary" style="width:40%;">Submit</b-button>
      </div>
    </b-form>
  </div>
</template>

<script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
<script>
import axios from 'axios';
export default {
  data() {
    return{
      virtual: false,
      title: '',
      headCount:2,
      promiseDate: '',
      promiseTime: '',
      Types: [{ text: 'Select One', value: null }, 'Travel', 'Restaurant', 'Study', 'Art', 'Game', 'Exercise', 'Etc'],
      Type:'',
      lat:0,
      lon:0,
      promiseAroundPlace:" ",
      promiseDetailPlace:""
      // "kakao": false
    }
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement('script')
      script.onload = () => kakao.maps.load(this.initMap)
      script.src =
        `http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${process.env.VUE_APP_MAP_API}&libraries=services,clusterer`
      document.head.appendChild(script)
    }
    console.log(`${process.env.VUE_APP_MAP_API}`)
  },
  methods: {
    initMap() {
      var mapContainer = document.getElementById('map'), // 지도를 표시할 div
          mapOption = {
            center: new kakao.maps.LatLng(37.564343, 126.947613), // 지도의 중심좌표
            level: 3, // 지도의 확대 레벨
          }
      var map = new kakao.maps.Map(mapContainer, mapOption)
      var marker = new kakao.maps.Marker({ 
          // 지도 중심좌표에 마커를 생성합니다 
          position: map.getCenter() 
      });
      var geocoder = new kakao.maps.services.Geocoder();
      // 주소-좌표 변환 객체를 생성합니다

      // 장소 검색 객체를 생성합니다
      var ps = new kakao.maps.services.Places(); 
      ps.keywordSearch(this.promiseAroundPlace, function(data, status, pagination){
        if (status === kakao.maps.services.Status.OK) {
          // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
          // LatLngBounds 객체에 좌표를 추가합니다
          var bounds = new kakao.maps.LatLngBounds();
          for (var i=0; i<data.length; i++) {
              // 마커를 생성하고 지도에 표시합니다
              bounds.extend(new kakao.maps.LatLng(data[i].y, data[i].x));
          }       
          // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
          map.setBounds(bounds);
        } 
      });
      marker.setMap(map); // 지도에 마커를 표시합니다
      kakao.maps.event.addListener(map, 'click', (mouseEvent) => {
        // 클릭한 위도, 경도 정보를 가져옵니다 
        var latlng = mouseEvent.latLng;
        marker.setPosition(latlng);
        marker.setMap(map);

        var message = '클릭한 위치의 위도는 ' + latlng.getLat() + ' 이고, ';
        message += '경도는 ' + latlng.getLng() + ' 입니다';
        console.log(message)

        this.lat = latlng.getLat()
        this.lon = latlng.getLng()

        geocoder.coord2Address(latlng.getLng(), latlng.getLat(), (result, status)=> {
          if (status === kakao.maps.services.Status.OK) {
            var detailAddr = result[0].address.address_name;
            console.log(detailAddr, typeof detailAddr)
            const place = document.querySelector('#place')
            place.innerText = detailAddr
            this.promiseDetailPlace = detailAddr
          }   
        });
      });
    },
    promiseCreate(){
      axios({
          url:'http://127.0.0.1:8080/promise',
          method:'post',
          headers: {
            'x-auth-token': `${localStorage.getItem('token')}`,
          },
          data:{
            title: this.title,
            headCount: this.headCount,
            promisetime: this.promiseDate+' '+this.promiseTime,
            type: this.Type,
            lat: this.lat,
            lon: this.lon,   
            place: this.promiseDetailPlace                
          }
        })
          .then(res=>{
            this.$router.push({ name:'PromiseList'})
            console.log(res.data)
          })
          .catch(err=>{
            console.log(err)
            console.log(this.title,
            this.checked,
            this.headCount,
            
            this.promiseDate+' '+this.promiseTime,
            this.time,
            this.Type,
            this.lat,
            this.lon,   
            this.promiseDetailPlace )
          })
    },
  }
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');
div { font-family: 'Jua', sans-serif; }
.map_wrap {position:relative;width:100%;height:350px;}
.map_wrap, .map_wrap * {margin:0;padding:0;font-family:'Malgun Gothic',dotum,'돋움',sans-serif;font-size:12px;}
.map_wrap a, .map_wrap a:hover, .map_wrap a:active{color:#000;text-decoration: none;}
.map_wrap {position:relative;width:100%;height:500px;}
.bg_white {background:#fff;}
#pagination {margin:10px auto;text-align: center;}
#pagination a {display:inline-block;margin-right:10px;}
#pagination .on {font-weight: bold; cursor: default;color:#777;}
 
</style>