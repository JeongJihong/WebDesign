<template>
  <div class="page" style="margin-bottom:60px;">
    <!-- 헤더 -->
    <div class="mt-3 mx-4 d-flex justify-content-between align-items-center">
      <span class="fs-1">
        <button @click="goBack"><b-icon id="icon" icon="arrow-left" class="me-4"></b-icon></button>
        <span class="fw-bold">{{ promiseDetail.title }}</span>
      </span>
    </div>

    <!-- 상단 약속 정보 -->
    <div class="mt-4 mx-4 d-flex flex-row justify-content-start">
      <!-- 약속 유형 Icon -->
      <span class="me-4">
        <img :src="getImgUrl.icon"
          :alt="promiseDetail.type">
      </span>
      <!-- 약속 정보 Text -->
      <span class="d-flex flex-column justify-content-between">
        <span>
          <span class="fw-bold">약속 유형</span>
          <span class="ms-2">{{ promiseDetail.type }}</span>
        </span>
        <span>
          <span class="fw-bold">약속 시간</span>
          <span class="ms-2">{{ promiseDate }} &nbsp; {{ promiseTime }}</span>
        </span>
        <span class="d-flex justify-content-start align-items-center">
          <span class="fw-bold">모임 여부</span>
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

    <!-- 카카오 맵 api 참고 -->
    <div v-show="promiseDetail.place" class="mt-4 pt-3">
      <div class="d-flex justify-content-between align-items-center">
        <span class="fw-bold mx-3">약속 장소</span>
        <b-icon @click="initMap" icon="arrow-clockwise" variant="primary" class="me-3"></b-icon>
      </div>
      <div>
        <!-- padding-bottom: 56.25% 는 16:9 비율로 고정한다는 style -->
        <div id="map" style="padding-bottom: 56.25%; width: 100%; height: 100%;">
        </div>
      </div>
      <span class="mt-1 me-1 d-flex justify-content-end">
        <span>{{ promiseDetail.place }}</span>
      </span>
    </div>

    <!-- 약속 인원 -->
    <div class="mt-4 pt-3">
      <div class="mx-3 d-flex justify-content-between align-items-center">
        <span class="fw-bold">약속 인원</span>
        <span style="color: #0d6efd;">{{ promiseDetail.peopleNum }}</span>
        <hr style="width: 65%;">
      </div>
      <b-list-group>
        <b-list-group-item
          class="border-0 my-1" v-for="user in promiseDetail.promisePeople" :key="user.uid"
          @click="goToProfile(user.nickname)">
          <div class="d-flex align-items-center">
            <span>
              <b-avatar v-if="user.thumbnail" class="me-2"
                :src="getThumbnailImgUrl({ imgURL: user.thumbnail }).thumbnail"></b-avatar>
              <b-avatar v-else class="me-2"></b-avatar>
            </span>
            <span>{{ user.nickname }}</span>
          </div>
        </b-list-group-item>
      </b-list-group>
    </div>

    <!-- 약속 취소  /  수락/거절 버튼 / 불참 버튼-->
    <div class="mt-4 mx-3 pt-4">
      <div v-if="promiseDetail.createrNickname === username"
        class="d-flex justify-content-center">
        <button @click="promiseDetailDelete()"
          class="ms-4 me-4 btn-danger px-4 py-2 rounded">약속 취소하기</button>
      </div>
      <div v-else-if="promiseDetail.createrNickname !== username && promiseDetail.approve === 0"
        class="d-flex justify-content-center">
        <button @click="promiseDetailReject()" class="me-4 btn-danger px-4 py-2 rounded">거절하기</button>
        <button @click="promiseDetailAccept()" class="ms-4 btn-primary px-4 py-2 rounded">수락하기</button>
      </div>
      <div v-else-if="promiseDetail.createrNickname !== username && promiseDetail.approve === 1"
        class="d-flex justify-content-center">
        <button @click="promiseDetailRejectAfterAccept()"
          class="ms-4 me-4 btn-danger px-4 py-2 rounded">불참하기</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { mapState } from 'vuex'

export default {
  data() {
    return {
      promisetime: '',
      location: {
        lat: 50,
        lon: 120
      }
    }
  },
  created() {
    let payload = {
      token: this.token,
      promiseid: this.$route.params.promiseid
    }
    this.$store.dispatch('promiseDetailGet', payload)
  },
  computed: {
    ...mapState([
      'token',
      'username',
      'promiseDetail'
    ]),
    getImgUrl () {
      return {
        ...this.promiseDetail,
        icon: this.promiseDetail.type && require(`@/assets/images/${this.promiseDetail.type}-icon.svg`)
      }
    }
  },
  methods: {
    goBack() {
      this.$router.go(-1)
    },
    goToProfile(nickname) {
      this.$router.push({ name: 'ProfileDetail', params: { nickname } })
    },
    promiseDetailDelete() {
      if (this.promiseDetail.createrNickname === this.username) {
        this.$store.state.promiseDeleteMode = true
        axios({
          url: `http://127.0.0.1:8080/promise/${this.$route.params.promiseid}`,
          method: "delete",
          headers: {
            "Content-Type": "application/json",
            "X-AUTH-TOKEN": this.token
          },
        })
          .then(() => {
            this.$store.state.promiseDeleteMode = false
          })

        // 약속 삭제(취소) -> 생성자'status -(2*num)
        let formdataMaker = new FormData()
        formdataMaker.append('status', -(2*this.promiseDetail.peopleNum))
        axios({
          // 어짜피 약속 삭제는 생성자만 가능하다 -> this.username 사용
          url: `http://127.0.0.1:8080/account/status/${this.username}`,
          method: 'put',
          headers: {
            "Content-Type": "application/json",
            "X-AUTH-TOKEN": this.token
          },
          data: formdataMaker
        })

        // this.$router.push({ name: 'PromiseList' })
        this.$router.push({ name: 'FeedMain' })
      }
    },
    promiseDetailAccept() {
      if (this.promiseDetail.createrNickname !== this.username && this.promiseDetail.approve === 0) {
        // 약속 수락
        axios({
          url: `http://127.0.0.1:8080/promise/people/${this.$route.params.promiseid}`,
          method: "put",
          headers: {
            "Content-Type": "application/json",
            "X-AUTH-TOKEN": this.token
          }
        })
          .then(() => {
            let payload = {
              token: this.token,
              promiseid: this.$route.params.promiseid
            }
            this.$store.dispatch('promiseDetailGet', payload)
          })
        

        // 약속 수락 -> 참가자'status +2
        let formdataAttender = new FormData()
        formdataAttender.append('status', 2)

        axios({
          url: `http://127.0.0.1:8080/account/status/${this.username}`,
          method: 'put',
          headers: {
            "Content-Type": "application/json",
            "X-AUTH-TOKEN": this.token
          },
          data: formdataAttender
        })

        // 약속 수락 -> 생성자'status +1
        let formdataMaker = new FormData()
        formdataMaker.append('status', 1)
        axios({
          url: `http://127.0.0.1:8080/account/status/${this.promiseDetail.createrNickname}`,
          method: 'put',
          headers: {
            "Content-Type": "application/json",
            "X-AUTH-TOKEN": this.token
          },
          data: formdataMaker
        })

        // 위치 정보 업데이트
        let formdata = new FormData()
        formdata.append('lat', this.location.lat)
        formdata.append('lon', this.location.lon)
        axios({
          url: `http://127.0.0.1:8080/promise/place/${this.$route.params.promiseid}`,
          method: 'put',
          headers: {
            "Content-Type": "application/json",
            "X-AUTH-TOKEN": this.token,
          },
          data: formdata
        })
      }
    },
    promiseDetailReject() {
      if (this.promiseDetail.createrNickname !== this.username && this.promiseDetail.approve === 0) {
        axios({
          url: `http://127.0.0.1:8080/promise/people/${this.$route.params.promiseid}`,
          method: "delete",
          headers: {
            "Content-Type": "application/json",
            "X-AUTH-TOKEN": this.token
          }
        })
          .then(() => {
            this.$router.go(-1)
          })
      }
    },
    promiseDetailRejectAfterAccept() {
      if (this.promiseDetail.createrNickname !== this.username && this.promiseDetail.approve === 1) {
        axios({
          url: `http://127.0.0.1:8080/promise/people/${this.$route.params.promiseid}`,
          method: "delete",
          headers: {
            "Content-Type": "application/json",
            "X-AUTH-TOKEN": this.token
          }
        })

        // 약속 수락 후 거절 -> 참가자'status -3
        let formdataAttender = new FormData()
        formdataAttender.append('status', -3)
        axios({
          url: `http://127.0.0.1:8080/account/status/${this.username}`,
          method: 'put',
          headers: {
            "Content-Type": "application/json",
            "X-AUTH-TOKEN": this.token
          },
          data: formdataAttender
        })

        this.$router.push({ name: "PromiseList"})
      }
    },
    getThumbnailImgUrl (payload) {
      return {
        ...this.promiseDetail,
        thumbnail: this.promiseDetail.promisePeople.length && require(`@/assets/images/${payload.imgURL}`)
      }
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