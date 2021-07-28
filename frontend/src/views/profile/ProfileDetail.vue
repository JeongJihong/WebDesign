<template>
  <div class="m-4">
    <div class="d-flex my-4" style="justify-content: space-between">
      <div class="d-flex" style="justify-content: space-between">
        <button style="padding-right:20px">
          <b-icon-arrow-left class="h2"></b-icon-arrow-left>
        </button>
        <h2>{{ this.nickname }}</h2>
      </div>
      <button @click="goToProfileUpdate">
        프로필 수정
      </button>
    </div>
    <div class="d-flex">
      <img src="@/assets/images/profile_default.png" alt="image" style="width: 70px; height: 70px" >
      <div class="mx-4">
        <h4>이름</h4>
        <div class="d-flex" style="justify-content: space-between">
          <button class="d-flex" @click="goToFollowList">
            <h4>팔로우</h4>
            <h4 style="color:blue;">12</h4>
          </button>
          &nbsp;&nbsp;&nbsp;&nbsp;
          <button class="d-flex" @click="goToFollowList">
            <h4>팔로워</h4>
            <h4 style="color:blue;">123</h4>
          </button>
        </div>
      </div>
    </div>
    <div class="d-grid pt-3">
      <button @click="goToFollow" class="btn btn-primary shadow-none" style="display: flex; height: 30px; justify-content: center; align-items: center;">팔로우</button>
      <!-- <button @click="goToFollow" class="btn btn-primary shadow-none" style="display: flex; height: 25px; justify-content: center; align-items: center;">팔로우취소</button> -->
    </div>
    <div class="pt-4">
      <p>{{ this.introduction }}</p>
    </div>
    <hr>

    <div v-for="image in 9" :key="image" class="square img_1">
    </div>

  </div>
</template>

<script>
import axios from 'axios'

export default {
  data () {
    return {
      nickname: '',
      introduction: '',
      followers: 0,
      followings: 0,
    }
  },
  methods: {
    goToProfileUpdate () {
      this.$router.push({
        name: 'ProfileUpdate',
      })
    },
    goToFollowList () {
      this.$router.push({
        name: 'FollowList',
      })
    },
    goToFollow () {
      axios({
        method: 'post',
        url: `http://127.0.0.1:8080/account/profile/follow`,
        params: this.userInfo,
        headers: {
          'Content-Type': 'application/json',
          'X-AUTH-TOKEN' : this.$store.state.token
        },
      })
      .then((res) => {
        console.log('패치완료!!!!!!!!')
        console.log(res.data)
        this.$router.push({name:'ProfileDetail'})
      })
      .catch((err) => {
        alert(err)
      })
    },
    getUserInfo: function () {
      axios({
        method: 'get',
        url: `http://127.0.0.1:8080/account/profile/`,
        headers: {
          'Content-Type': 'application/json',
          'X-AUTH-TOKEN' : this.$store.state.token
        },
      })
      .then((res) => {
        this.nickname = res.data.nickname
        this.introduction = res.data.introduction
      })
      .catch((err) => {
        console.log(err)
      })
    },
    goToChangePassword () {
      this.$router.push({
        name: 'ChangePassword',
      })
    },
    followInfo () {
      axios({
        method: 'get',
        url: `http://127.0.0.1:8080/account/profile/${this.nickname}/follower`,
        headers: {
          'Content-Type': 'application/json',
          'X-AUTH-TOKEN' : this.$store.state.token
        },
      })
      .then((res) => {
        this.followerNum = res.data
      })
      .catch((err) => {
        alert(err)
      })
    }
  },
  created () {
    axios({
      method: 'get',
      url: `http://127.0.0.1:8080/account/checkJWT/`,
      headers: {
        'Content-Type': 'application/json',
        'X-AUTH-TOKEN' : this.$store.state.token
      },
    })
    .then((res) => {
      console.log('checkJWT 성공! 밑에 res 확인')
      console.log(res)
      const range = res.data.indexOf('/') - 1
      const uid = res.data.substr(0, range)
      this.myUid=uid
      this.getUserInfo()
      this.followInfo()
      console.log(this.myUid)
    })
    .catch((err) => {
      console.log(err)
    })
  }
}
</script>

<style>
.square {
    float:left;
    position: relative;
    width: 30%;
    padding-bottom : 30%; /* = width for a 1:1 aspect ratio */
    margin:1.66%;
    background-position:center center;
    background-repeat:no-repeat;
    background-size:cover; /* you change this to "contain" if you don't want the images to be cropped */
}

/* .img_1{background-image:url('img-placeholder.png');} */
.img_1{background-image:url('../../assets/images/img-placeholder.png');}
</style>