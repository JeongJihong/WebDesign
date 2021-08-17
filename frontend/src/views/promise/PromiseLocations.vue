<template>
  <div class="m-4 page">
    <div class="d-flex" style="justify-content: space-between">
      <div class="d-flex">
        <button @click="goBack"><b-icon icon="arrow-left" class="fs-1 me-4"></b-icon></button>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <h2 class="fw-bold">
          다들 어디
        </h2>
      </div>
      <b-icon @click="updateLocations" icon="arrow-clockwise" animation="spin 1s" font-scale="2"></b-icon>
    </div>
    <hr>
    <div class="my-2">
      <div id="map" style="padding-bottom: 56.25%; width: 100%; height: 100%;"></div>
    </div>
    <hr>
    <div>
      <div v-for="person in attendantsLength" :key="person">
        <div class="d-flex flex-row mx-2 my-4" style="justify-content: space-between; text-align:center">
          <div v-if="thumbnails[person-1]" class="col-3 d-flex align-items-center">
            <b-avatar 
              :src="getThumbnailImgUrl({ idx: person-1, imgURL: thumbnails[person-1] }).thumbnail"></b-avatar>
          </div>
          <div v-else class="col-3 m-0 d-flex align-items-center">
            <b-avatar></b-avatar>
          </div>
          <div class="col-3 fw-bold m-0 d-flex align-items-center">
            <p>{{ nicknames[person-1]}}</p>
          </div>
          <div class="col-3 fw-bold m-0 d-flex align-items-center" style="color:green;">
            <p>{{ times[person-1]}}</p>
          </div>
          <div class="col-3 m-0 d-flex align-items-center">
            <p>{{ places[person-1]}}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
<script>
import axios from 'axios'


export default {
  data () {
    return {
      promiseid : this.$route.params.promiseid,
      attendantsInfo: '',
      attendantsLength: 0,
      promiseLat: 0,
      promiseLon: 0,
      thumbnails: [],
      nicknames : [],
      times: [],
      places: [],
      promiseType: '',
      attendantsLat: [],
      attendantsLon: [],
      centerLat: 0,
      centerLon: 0,
      level: 10,
    }
  },
  mounted () {
    if (window.kakao && window.kakao.maps) {
      this.updateLocations();
    } else {
      this.addKakaoMapScript();
      // const script = document.createElement('script')
      // /* global kakao */
      // script.onload = () => kakao.maps.load(this.initMap)
      // script.src =
      //   `http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${process.env.VUE_APP_MAP_API}&libraries=services,clusterer`
      // document.head.appendChild(script)
    }
  },
  methods: {
    goBack() {
      this.$router.go(-1)
    },
    addKakaoMapScript () {
      const script = document.createElement('script')
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap)
      script.src =
        `http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${process.env.VUE_APP_MAP_API}&libraries=services,clusterer`
      document.head.appendChild(script)
    },
    initMap() {
      // 마커 중심, 지도 범위 설정을 위한 배열
      // this.attendantsLat = [];
      // this.attendantsLon = [];

      var mapContainer = document.getElementById('map') // 지도를 표시할 div
      var mapOption = {
        center: new kakao.maps.LatLng(50, 120), // 지도의 중심좌표
        level: this.level, // 지도의 확대 레벨
      }
      if (this.places) {
        mapOption.center = new kakao.maps.LatLng(this.centerLat, this.centerLon)
        mapOption.level = this.level
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
          markerPosition = new kakao.maps.LatLng(this.promiseLat, this.promiseLon); // 마커가 표시될 위치입니다

      // 마커를 생성합니다
      var marker = new kakao.maps.Marker({
          position: markerPosition, 
          image: markerImage // 마커이미지 설정 
      });
      marker.setMap(map);  

      var thumbnailList = Array()
      var nameList = Array()
      var timeList = Array()
      var placeList = Array()
      
      // --------------------------- 약속 참가자들 위치 마커로 띄워줄거임 ---------------------------------------------
      for (point = 0; point < this.attendantsLength; point++) {
        const thumbnail = this.attendantsInfo[point].thumbnail;
        const name = this.attendantsInfo[point].nickname;
        const lat = this.attendantsInfo[point].lat;
        const lon = this.attendantsInfo[point].lon;
        const markerPosition = new kakao.maps.LatLng(lat, lon);

        // this.attendantsLat.push(this.lat);
        // this.attendantsLon.push(this.lon);

        const marker = new kakao.maps.Marker({
          position: markerPosition
        });
        marker.setMap(map)

        // 직선 거리, 시간 계산
        function deg2rad(deg) { return deg * (Math.PI/180) }
        var R = 6371; // Radius of the earth in km 
        var dLon = deg2rad(this.promiseLat-lat); // deg2rad below 
        var dLat = deg2rad(this.promiseLon-lon); 
        var a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(deg2rad(lon)) * Math.cos(deg2rad(this.promiseLon)) * Math.sin(dLon/2) * Math.sin(dLon/2); 
        var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
        var d = R * c; // Distance in km
        var time = parseInt(d / 15 * 60); // 평균 시속 15km/h로 나누고 분으로 환산
        
        if (time < 60) {
          var iwContent = '<div class="d-flex px-3" style="padding:5px; width:150px; justify-content: space-between; align-items:top;">'+ 
            '<span>'+'<font size="3em" color="black">'+name+'</font>'+'</span>'+
            '<span>'+'<font size="2em" color="green">도착'+time+'분전</font></span></div>',
          iwPosition = new kakao.maps.LatLng(lat, lon);
          var timeToStr = String(time) + "분전";
          timeList.push(timeToStr);
        }
        else {
          var hour = parseInt(time / 60);
          var minute = time % 60;
          var iwContent = '<div class="d-flex px-3" style="padding:5px; width:150px; justify-content: space-between; align-items:top;">'+ 
            '<span>'+'<font size="3em" color="black">'+name+'</font>'+'</span>'+
            '<span>'+'<font size="2em" color="green">'+hour+'시간'+minute+'분전</font></span></div>',
          iwPosition = new kakao.maps.LatLng(lat, lon);
          // var timeToStr = String(hour) + "시간" + String(minute) + "분전";
          var timeToStr = String(hour) + "시간전";
          timeList.push(timeToStr);
        }
        
        var infowindow = new kakao.maps.InfoWindow({
          position : iwPosition, 
          content : iwContent 
        });
        
        infowindow.open(map, marker);

        // 주소-좌표 변환 객체를 생성합니다
        
        var geocoder = new kakao.maps.services.Geocoder();
        
        geocoder.coord2Address(lon, lat, (result, status)=> {
          if (status === kakao.maps.services.Status.OK) {
            var detailAddr = result[0].address.address_name;
            var currentAddress = detailAddr
            var currentDong = currentAddress.split(' ')[2]
            placeList.push(currentDong);
          }   
        });
        thumbnailList.push(thumbnail);
        nameList.push(name);
        // timeList.push(time);
        
        // this.thumbnails = thumbnailList
        // this.nicknames = nameList
        // this.places = placeList
        // this.times = timeList
      }
      // ------------------------------------------------------------------------
      this.thumbnails = thumbnailList
      this.nicknames = nameList
      this.places = placeList
      this.times = timeList

    },
    updateLocations () {
      axios({
        method: 'get',
        url: `http://127.0.0.1:8080/promise/place/${this.promiseid}`,
        headers: {
          'Content-Type': 'application/json',
          'X-AUTH-TOKEN' : this.$store.state.token
        },
      })
      .then((res) => {
        console.log(res.data)
        this.attendantsInfo = res.data
        this.attendantsLength = res.data.length
        this.getPromiseInfo()
      })
      .catch((err) => {
        alert(err)
      })
    },
    getThumbnailImgUrl (payload) {
      return {
        ...this.thumbnails,
        thumbnail: this.thumbnails[payload.idx] && require(`@/assets/images/${payload.imgURL}`)
      }
    },
    getPromiseInfo () {
      axios({
        method: 'get',
        url: `http://127.0.0.1:8080/promise/${this.promiseid}`,
        headers: {
          'Content-Type': 'application/json',
          'X-AUTH-TOKEN' : this.$store.state.token
        },
      })
      .then((res) => {
        console.log(res.data)
        this.promiseLat = res.data.lat
        this.promiseLon = res.data.lon

        // -----------------------------------------------------------------------
        // 지도 중앙 좌표, 범위 설정
        this.attendantsLat = [];
        this.attendantsLon = [];
        this.attendantsLat.push(res.data.lat);
        this.attendantsLon.push(res.data.lon);

        for (var i = 0; i < this.attendantsLength; i++) {
          const lat = this.attendantsInfo[i].lat;
          const lon = this.attendantsInfo[i].lon;

          this.attendantsLat.push(lat);
          this.attendantsLon.push(lon);
        }
        
        // 마커 중심, 지도 범위 설정을 위한 계산
        var maxLat = Math.max.apply(null, this.attendantsLat);
        var minLat = Math.min.apply(null, this.attendantsLat);
        var maxLon = Math.max.apply(null, this.attendantsLon);
        var minLon = Math.min.apply(null, this.attendantsLon);

        var differenceLat = maxLat - minLat;
        var differenceLon = maxLon - minLon;

        var maxDifference = Math.max.apply(null, [differenceLat, differenceLon]);
        console.log(maxDifference);
        
        console.log(this.attendantsLat, '최대값은', Math.max.apply(null, this.attendantsLat))
        const avgLat = (maxLat + minLat) / 2;
        const avgLon = (maxLon + minLon) / 2;
        console.log('센터좌표', avgLat, avgLon);

        if (maxDifference > 0.11) {
          this.level = 11
        }
        else if (maxDifference > 0.06) {
          this.level = 10
        }
        else if (maxDifference > 0.03) {
          this.level = 9
        }
        else if (maxDifference > 0.01) {
          this.level = 8
        }
        else {
          this.level = 7
        }

        this.centerLat = avgLat;
        this.centerLon = avgLon;

        // -----------------------------------------------------------------------
        this.initMap()
      })
      .catch((err) => {
        alert(err)
      })
    }
  },
}
</script>

<style>

</style>