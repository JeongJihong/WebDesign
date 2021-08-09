<template>
  <div>
    <!-- 헤더 -->
    <div class="mt-3 mx-4 d-flex justify-content-between align-items-center">
      <span class="fs-1">
        <button @click="goBack"><b-icon icon="arrow-left" class="fs-1 me-4"></b-icon></button>
        <span class="fw-bold">프로필 정보 수정</span>
      </span>
      <button class="text-decoration-none" @click="updateProfileInfo">저장하기</button>
    </div>

    <!-- 프로필 수정 -->
    <div>
      <!-- 프로필 사진 수정 -->
      <div class="mt-5 mx-3 d-flex justify-content-center">
        <span class="dot"></span>
      </div>
      
      <!-- Username, 본인 소개글 수정 -->
      <div>
        <div class="mt-5 mx-3">
          <!-- Username -->
          <b-form-input v-model="userInfo.nickname" placeholder="username" type="text"></b-form-input>
        </div>
        <div class="mt-3 mx-3">
          <!-- 본인 소개글 -->
          <b-form-textarea v-model="userInfo.introduction" placeholder="본인 소개글" rows="3" max-rows="6"></b-form-textarea>
        </div>
      </div>

      <!-- 비밀번호 변경 버튼 -->
      <div class="mt-4 mx-3 d-grid">
        <!-- <b-button squared variant="danger">비밀번호 변경</b-button> -->
          <button @click="goToChangePassword" class="btn btn-danger shadow-none" style="height: 45px;">비밀번호 변경</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { mapActions, mapState } from 'vuex'

export default {
  name: 'ProfileUpdate',
  data () {
    return{
      userInfo: {
        // uid: '',
        introduction: '',
        nickname: '',
        file: '@/assets/images/profile_default.png',
      },
      myUid:'',
    }
  },
  methods: {
    goBack() {
      this.$router.go(-1)
    },
    updateProfileInfo: function () {
      console.log(this.$store.state.token)
      axios({
        method: 'patch',
        url: 'http://127.0.0.1:8080/account/profile/',
        params: this.userInfo,
        headers: {
          // 'Content-Type': 'application/json',
          'Content-Type': 'multipart/form-data',
          'X-AUTH-TOKEN' : this.$store.state.token
        },
      })
      .then((res) => {
        this.$router.push({
          name:'ProfileDetail',
          params: {nickname: this.userInfo.nickname}
        })
      })
      .catch((err) => {
        alert(err)
      })
    },
    getUserInfo: function () {
      axios({
        method: 'get',
        url: `http://127.0.0.1:8080/account/profile/${this.userInfo.nickname}`,
        headers: {
          'Content-Type': 'application/json',
          'X-AUTH-TOKEN' : this.$store.state.token
        },
      })
      .then((res) => {
        this.userInfo.uid = res.data.uid
        this.userInfo.nickname = res.data.nickname
        this.userInfo.introduction = res.data.introduction
      })
      .catch((err) => {
        console.log(err)
      })
    },
    goToChangePassword () {
      this.$router.push({
        name: 'ChangePassword',
      })
    }
  },
  created () {
    console.log(this.$store.state)
    axios({
      method: 'get',
      url: `http://127.0.0.1:8080/account/checkJWT/`,
      headers: {
        'Content-Type': 'application/json',
        'X-AUTH-TOKEN' : this.$store.state.token
      },
    })
    .then((res) => {
      this.myUid=res.data.uid
      this.getUserInfo()
      console.log(this.myUid)
    })
    .catch((err) => {
      console.log(err)
    })
  }
}
</script>

<style scoped>
/* 프로필 이미지 들어가기 전 디버깅 용 */
.dot {
  height: 200px;
  width: 200px;
  background-color: #bbb;
  border-radius: 50%;
  display: inline-block;
}
</style>