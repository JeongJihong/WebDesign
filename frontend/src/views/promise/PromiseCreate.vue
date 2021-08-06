<template>
  <div style="margin-left:10%; margin-right:10%;">
    <br>
    <b-form @submit="onSubmit" v-if="show">
      <b-form-group id="input-group-1" label="Title:" label-for="input-1">
        <b-form-input
          id="input-1"
          v-model="form.name"
          placeholder="Enter Title"
          required
        ></b-form-input>
      </b-form-group>

      <br>

      <b-form-group id="input-group-2" label="Type:" label-for="input-2">
        <b-form-select
          id="input-2"
          v-model="form.Type"
          :options="Types"
          required
        ></b-form-select>
      </b-form-group>

      <br>

      <b-form-group id="input-group-4" label="인원" label-for="input-4">
        <b-form-spinbutton id="input-4" v-model="num" min="2" max="100"></b-form-spinbutton>
      </b-form-group>

      <br>
      <b-form-group id="input-group-3">
        <b-form-checkbox-group
          v-model="form.checked"
          id="checkboxes-3"
        >
          <b-form-radio value="video">화상 회의</b-form-radio>
        </b-form-checkbox-group>
      </b-form-group>
      <br>
      <b-form-group id="input-group-3">
        <label :for="`type-date`">날짜:</label>
        <b-form-input id=type-date type="date"></b-form-input>
        br
        <label :for="`type-time`">시간:</label>
        <b-form-input id=type-time type="time"></b-form-input>
      </b-form-group>
    <br>
      <!-- <b-button type="submit" variant="primary">Submit</b-button> -->
    </b-form>
    <hr>
      <b-input-group prepend="약속장소 탐색" class="mt-3">
        <b-form-input placeholder="대전 대흥동" type="text" v-model="aroundplace" ></b-form-input>
        <b-input-group-append>
          <b-button variant="outline-success">검색</b-button>
        </b-input-group-append>
      </b-input-group>
    <br>
    <div class="map_wrap">
      <div id="map" style="width:100%;height:100%;position:relative;overflow:hidden;"></div>
    </div>
    <hr>
    <br>
    <div class="d-flex justify-content-center">
      <b-button type="submit" variant="primary" style="width:40%;">Submit</b-button>
    </div>
  </div>
</template>
<script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
<script>
export default {
  data() {
    return{
      form: {
        title: '',
        name: '',
        Type: null,
        checked: [],
        num:Number,
        date: Date,
        time: TimeRanges
      },
      Types: [{ text: 'Select One', value: null }, 'Game', 'Study', 'Travel', 'Restaurant'],
      show: true,
      promiseTime:Date,
      lat:Number,
      lon:Number,
      aroundplace:""
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

      marker.setMap(map); // 지도에 마커를 표시합니다
      kakao.maps.event.addListener(map, 'click', function(mouseEvent) {
        // 클릭한 위도, 경도 정보를 가져옵니다 
        var latlng = mouseEvent.latLng;
        marker.setPosition(latlng);
        marker.setMap(map);

        var message = '클릭한 위치의 위도는 ' + latlng.getLat() + ' 이고, ';
        message += '경도는 ' + latlng.getLng() + ' 입니다';
        console.log(message)

        geocoder.coord2Address(latlng.getLng(), latlng.getLat(), function(result, status) {
          if (status === kakao.maps.services.Status.OK) {
            var detailAddr = result[0].address.address_name;
            console.log(detailAddr)
          }   
        });
      });
    },
  },
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');
div { font-family: 'Jua', sans-serif; }
.map_wrap {position:relative;width:100%;height:350px;}
.title {font-weight:bold;display:block;}
.hAddr {position:absolute;left:10px;top:10px;border-radius: 2px;background:#fff;background:rgba(255,255,255,0.8);z-index:1;padding:5px;}
#centerAddr {display:block;margin-top:2px;font-weight: normal;}
.bAddr {padding:5px;text-overflow: ellipsis;overflow: hidden;white-space: nowrap;}
</style>