<template>
  <div>
    <!-- 헤더 -->
    <div class="mt-3 mx-4 d-flex justify-content-between align-items-center">
      <span class="fs-1">
        <button @click="goBack"><b-icon icon="arrow-left" class="me-4"></b-icon></button>
        <span class="fw-bold">{{ this.title }}</span>
      </span>
      <!-- 한 시간 전인지 판단하는 변수 필요 ex) Date.now() -->
      <button v-if="aHourAgo" style="color: #0d6efd;"
        @click="goToPromiseLocations(this.$route.params.promiseid)">
        지금 어디?</button>
      <button v-else style="color: grey;">지금 어디?</button>
    </div>

    <!-- 상단 약속 정보 -->
    <div class="mt-4 mx-4 d-flex flex-row justify-content-between">
      <!-- 약속 유형 Icon -->
      <span>
        <img v-if="promiseDetail.type" :src="'../assets/' + promiseDetail.type + '.svg'"
          :alt="promiseDetail.type">
        <!-- 디버깅용 코드 -> Icon 생기면 지우기-->
        <img v-else src="https://picsum.photos/100" style="border-radius: 50%;">
      </span>
      <!-- 약속 정보 Text -->
      <span class="d-flex flex-column justify-content-between">
        <span>
          <span class="fw-bold">약속 유형</span>
          <!-- 디버깅용 코드 -> v-if 지우고 v-else가 있는 span 지우기 -->
          <span v-if="promiseDetail.type" class="ms-2">{{ promiseDetail.type }}</span>
          <span v-else class="ms-2">게임</span>
        </span>
        <span>
          <span class="fw-bold">약속 시간</span>
          <!-- 디버깅용 코드 -> v-if 지우고 v-else가 있는 span 지우기 -->
          <span v-if="promiseDetail.type" class="ms-2">{{ promiseDetail.promisetime }}</span>
          <span v-else class="ms-2">8. 4. 19:00~24:00</span>
        </span>
        <span class="d-flex justify-content-start align-items-center">
          <span class="fw-bold">모임 여부</span>
          <!-- 디버깅용 코드 -> v-if 지우고 v-else가 있는 span 지우기 -->
          <span v-if="promiseDetail.place" class="ms-2">
            <input type="checkbox" id="switch">
            <label for="switch" class="custom-toggle-yes"></label>
          </span>
          <span v-else class="ms-2">
            <input type="checkbox" id="switch">
            <label for="switch" class="custom-toggle-no"></label>
          </span>
        </span>
      </span>
    </div>

    <!-- place가 존재하면 지도 보여주기 -->
    <!-- 카카오 맵 api 참고 -->
    <!-- 디버깅용 -> 추후 div에 v-if="promiseDetail.place" 추가 필요 -->
    <div class="mt-4 pt-3">
      <p class="fw-bold mx-3">약속 장소</p>
      <div>
        <!-- padding-bottom: 56.25% 는 16:9 비율로 고정한다는 style -->
        <div v-if="promiseDetail.place" id="map" style="padding-bottom: 56.25%; width: 100%; height: 100%;">
        </div>
        <div v-else id="map" style="padding-bottom: 56.25%; width: 100%; height: 100%;">
        </div>
      </div>
      <!-- 디버깅용 -> v-else 는 다 삭제 -->
      <span class="mt-1 me-1 d-flex justify-content-end">
        <span v-if="promiseDetail.place">{{ promiseDetail.place }}</span>
        <span v-else>대전광역시 유성구 동서대로 98-39</span>
      </span>
    </div>

    <!-- 약속 인원 -->
    <div class="mt-4 pt-3">
      <div class="mx-3 d-flex justify-content-between align-items-center">
        <span class="fw-bold">약속 인원</span>
        <!-- 디버깅용 -> v-if 구문과 v-else 줄 지우기 -->
        <span v-if="promiseDetail.peopleNum" style="color: #0d6efd;">{{ promiseDetail.peopleNum }}</span>
        <span v-else style="color: #0d6efd;">4</span>
        <hr style="width: 65%;">
      </div>
      <b-list-group>
        <b-list-group-item
          class="border-0 my-1" v-for="user in promisePeople" :key="user.uid"
          @click="goToProfile(user.nickname)">
          <div class="d-flex align-items-center">
            <span>
              <img v-if="user.thumnail" :src="user.thumnail"
                :alt="user.nickname + '의 프로필'">
              <!-- 디버깅용 코드 -> Icon 생기면 지우기-->
              <img v-else src="https://picsum.photos/60" style="border-radius: 50%;">
            </span>
            <span>{{ user.nickname }}</span>
          </div>
        </b-list-group-item>
      </b-list-group>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
export default {
  data() {
    return {
      title: this.$route.params.title,
      tmpX: 0,
      tmpY: 0,
    }
  },
  mounted() {
      if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement('script')
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap)
      script.src = `http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${process.env.VUE_APP_MAP_API}`
      document.head.appendChild(script)
    }
  },
  created() {
    // console.log(Date.now())
    this.$store.dispatch('promiseDetailGet',
      { 
        token: this.token,
        promiseid: this.$route.params.promiseid
      })
  },
  computed: {
    ...mapState([
      'token',
      'promiseDetail'
    ])
  },
  methods: {
    goBack() {
      this.$router.go(-1)
    },
    goToPromiseLocations(promiseid) {
      this.$router.push({ name: 'PromiseLocations', params: { promiseid } })
    },
    // kakao 지도 관련
    initMap() {
      // REST API 생성 전 디버깅 용 - lat: 위도 == y, lon: 경도 == x
      if (this.promiseDetail.lat && this.promiseDetail.lon) {
        this.tmpX = this.promiseDetail.lon
        this.tmpY = this.promiseDetail.lat
      } else {
        this.tmpX = 127.298514,
        this.tmpY = 36.3551420
      }

      // ######     좌표를 중심으로 지도가 그려짐      ######
      var mapContainer = document.getElementById('map'),                 // 지도를 표시할 div
          mapOption = {
            center: new kakao.maps.LatLng(this.tmpY, this.tmpX),       // 지도의 중심좌표
            level: 4,                                                    // 지도의 확대 레벨
          }
      var map = new kakao.maps.Map(mapContainer, mapOption)              // 지도를 생성

      map.setDraggable(false)
      map.setZoomable(false)


      // ######    여기부터는 지도에 마커 표시하기     ######
      var markerPosition = new kakao.maps.LatLng(this.tmpY, this.tmpX) // 마커가 표시될 위치
      var marker = new kakao.maps.Marker({ position: markerPosition })   // 마커를 생성
      marker.setMap(map)                                                 // 마커가 지도 위에 표시되도록 설정
    },
    goToProfile(nickname) {
      this.$router.push({ name: 'ProfileDetail', params: { nickname } })
    }
  }
}
</script>

<style scoped>
input[type=checkbox]{
	height: 0;
	width: 0;
	visibility: hidden;
}

.custom-toggle-no {
	cursor: pointer;
	text-indent: -9999px;
	width: 50px;
	height: 20px;
	background: #dc3545;
	display: block;
	border-radius: 20px;
	position: relative;
}

.custom-toggle-yes {
	cursor: pointer;
	text-indent: -9999px;
	width: 50px;
	height: 20px;
	background: #0d6efd;
	display: block;
	border-radius: 20px;
	position: relative;
}

.custom-toggle-no:after {
	content: '';
	position: absolute;
	top: 2px;
	left: 2px;
	width: 16px;
	height: 16px;
	background: #fff;
	border-radius: 16px;
	transition: 0.3s;
}

.custom-toggle-yes:after {
	content: '';
	position: absolute;
	top: 2px;
	left: 32px;
	width: 16px;
	height: 16px;
	background: #fff;
	border-radius: 16px;
	transition: 0.3s;
}
</style>