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
          <b-form-checkbox value="video">화상 회의</b-form-checkbox>
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
    <div id="map" style="padding-bottom: 56.25%; width: 100%; height: 50%;"></div>
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
        `http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${process.env.VUE_APP_MAP_API}`
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
@import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');
div { font-family: 'Jua', sans-serif; }
</style>