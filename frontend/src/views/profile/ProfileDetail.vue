<template>
  <div style="margin-bottom:60px;">
    <div class="mt-3 mb-3 mx-4 d-flex justify-content-between align-items-center">
      <span>
        <button @click="goBack"><b-icon id="icon" icon="arrow-left" class="fs-1 me-4"></b-icon></button>
        <span class="fs-1 fw-bold">프로필</span>
      </span>
      <span>
        <button  v-if="this.nickname === this.myNickname" class="text-primary" @click="goToProfileUpdate">프로필 수정</button>
      </span>
    </div>
    <div class="m-4">
      <div class="d-flex">
        <b-avatar v-if="thumbnail" class="me-2" size="4rem" style="border: 1px solid black;"
          :src="getThumbnailImgUrl({ imgURL: thumbnail }).thumbnail"></b-avatar>
        <b-avatar v-else class="me-2" size="4rem"></b-avatar>
        <div class="mx-4">
          <div class="d-flex justify-content-between">
            <span class="fs-4">{{ this.nickname }}</span>
          </div>
          <div class="d-flex" style="justify-content: space-between">
            <button class="d-flex" @click="goToFollowList">
              <h5>팔로잉</h5>&nbsp;&nbsp;
              <h5 style="color:blue;">{{ this.followings }}</h5>
            </button>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <button class="d-flex" @click="goToFollowList">
              <h5>팔로워</h5>&nbsp;&nbsp;
              <h5 style="color:blue;">{{ this.followers }}</h5>
            </button>
          </div>
        </div>
      </div>
      <div class="mt-2">
        <div class="d-flex justify-content-between">
          <span style="font-size: 0.9rem">피플온도</span>
          <span style="font-size: 0.9rem">{{this.status + 10.0}}℃</span>
        </div>
        <b-progress :value="status+10" :max="30" class="mb-1" variant="info" height="0.5rem"></b-progress>
      </div>
      <div class="d-grid pt-3">
        <button @click="goToFollow" v-if="this.nickname !== this.myNickname && this.didIrequestFollowToYou === false" class="btn btn-primary shadow-none" style="display: flex; height: 30px; justify-content: center; align-items: center;">팔로우</button>
        <button v-if="this.nickname !== this.myNickname && this.didIrequestFollowToYou === true && this.doIFollowYou === false" @click="cancelFollow" class="btn btn-outline-primary shadow-none" style="display: flex; height: 30px; justify-content: center; align-items: center;">팔로우 요청 보냄</button>
        <button @click="unfollow" v-if="this.doIFollowYou === true" class="btn btn-outline-primary shadow-none" style="display: flex; height: 30px; justify-content: center; align-items: center; color: #0d6efd;">팔로우 취소</button>
      </div>
      <div v-if="this.didYouRequestFollowToMe === true">
        <p class="pt-2 pb-0 mb-0">{{ this.nickname }}님의 팔로우 요청</p>
        <div class="d-flex">
          <button @click="approveFollowRequest" class="col-6 btn btn-outline-secondary shadow-none" style="display: flex; height: 30px; justify-content: center; align-items: center; color: #0d6efd;">승인</button>
          <button @click="rejectFollowRequest" class="col-6 btn btn-dark shadow-none" style="display: flex; height: 30px; justify-content: center; align-items: center;">거절</button>
        </div>
      </div>
      <div class="pt-4">
        <p>본인소개글</p>
        <p>{{ this.introduction }}</p>
      </div>
      <hr>
      <div 
        v-for="image in this.articlesLength" 
        :key="image" 
        @click="goToArticleDetail(articles[image-1].articleid)" 
        class="square" 
        :style="{ backgroundImage: 'url(' + getArticleImgUrl({ idx: image-1, imgURL: articles[image-1].images }).thumbnail + ')' }"
      >
      </div>

    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { mapActions,mapState } from 'vuex'
export default {
  data () {
    return {
      nickname: this.$route.params.nickname,
      myNickname: '',
      introduction: '',
      followerLs: [],
      followingLs: [],
      followers: 0,
      followings: 0,
      myUid: 0,
      didIrequestFollowToYou: false,
      didYouRequestFollowToMe: false,
      followid: 0,
      articles: [],
      articlesLength: 0,
      thumbnail: '',
      doIFollowYou: false,
      status: 0, // Pipl 지수
    }
  },
  methods: {
    goBack() {
      this.$router.go(-1)
    },
    goToProfileUpdate () {
      this.$router.push({
        name: 'ProfileUpdate',
      })
    },
    goToFollowList () {
      this.$router.push({
        name: 'FollowList',
        params: {nickname: this.nickname}
      })
    },
    goToFollow () {
      axios({
        method: 'post',
        url: `https://i5b302.p.ssafy.io/api/account/profile/follow`,
        data: {
          'dstnickname': this.nickname,
          'srcnickname': this.myNickname
        },
        headers: {
          'Content-Type': 'application/json',
          'X-AUTH-TOKEN' : this.$store.state.token
        },
      })
      .then((res) => {
        this.didIrequestFollowToYou = !this.didIrequestFollowToYou
        this.getUserInfo()
      })

      // Follow Alarm Post
      axios({
        url: 'https://i5b302.p.ssafy.io/api/alarm',
        method: 'post',
        headers: {
          'Content-Type': 'application/json',
          'X-AUTH-TOKEN' : this.$store.state.token
        },
        data: {
          body: '팔로우 요청이 왔습니다.',
          category: 'Follow',
          receiverNickname: this.nickname,
          title: '팔로우 알림'
        }
      })
    },
    getUserInfo: function () {
      axios({
        method: 'get',
        url: `https://i5b302.p.ssafy.io/api/account/profile/${this.nickname}`,
        headers: {
          'Content-Type': 'application/json',
          'X-AUTH-TOKEN' : this.$store.state.token
        },
      })
      .then((res) => {
        this.myNickname = this.$store.state.username
        this.didIrequestFollowToYou = res.data.follow
        this.introduction = res.data.userProfile.introduction
        this.articles = res.data.article
        this.doIFollowYou = res.data.followBoolean
        if (this.articles === null) {
          this.articlesLength = 0
        }
        else {
          this.articlesLength = this.articles.length
        }
        this.thumbnail = res.data.userProfile.thumbnail
        // if (typeof this.thumbnail === 'undefined') {
        //   this.thumbnail = `https://i5b302.p.ssafy.io/img/profile_default.png`)
        // }
        this.checkFollowRequest()
        this.followerList()
      })
    },
    checkFollowRequest () {
      axios({
        method: 'get',
        url: `https://i5b302.p.ssafy.io/api/account/profile/follow/${this.nickname}`,
        headers: {
          'Content-Type': 'application/json',
          'X-AUTH-TOKEN' : this.$store.state.token
        },
      })
      .then((res) => {
        this.didYouRequestFollowToMe = res.data.otherToMe
        this.followid = res.data.followid
      })
    },
    approveFollowRequest () {
      axios({
        method: 'patch',
        url: `https://i5b302.p.ssafy.io/api/account/profile/follow`,
        headers: {
          'Content-Type': 'application/json',
          'X-AUTH-TOKEN' : this.$store.state.token
        },
        params: {
          'followid': this.followid,
        },
      })
      .then((res) => {
        this.didYouRequestFollowToMe = !this.didYouRequestFollowToMe
        alert(`${this.nickname}님의 팔로우 요청을 수락하셨습니다!`)
        this.getUserInfo()
      })
    },
    rejectFollowRequest () {
      axios({
        method: 'delete',
        url: `https://i5b302.p.ssafy.io/api/account/profile/follow`,
        headers: {
          'Content-Type': 'application/json',
          'X-AUTH-TOKEN' : this.$store.state.token
        },
        params: {
          'srcnickname': this.nickname,
          'dstnickname': this.myNickname
        },
      })
      .then((res) => {
        this.didYouRequestFollowToMe = !this.didYouRequestFollowToMe
        alert(`${this.nickname}님의 팔로우 요청을 거절하셨습니다!`)
        this.getUserInfo()
      })
    },
    unfollow () {
      axios({
        method: 'delete',
        url: `https://i5b302.p.ssafy.io/api/account/profile/follow`,
        headers: {
          'Content-Type': 'application/json',
          'X-AUTH-TOKEN' : this.$store.state.token
        },
        params: {
          'srcnickname': this.myNickname,
          'dstnickname': this.nickname
        },
      })
      .then((res) => {
        this.didYouRequestFollowToMe = !this.didYouRequestFollowToMe
        alert(`${this.nickname}님 팔로우를 취소했습니다!`)
        this.getUserInfo()
      })
    },
    goToChangePassword () {
      this.$router.push({
        name: 'ChangePassword',
      })
    },
    followerList () {
      axios({
        method: 'get',
        url: `https://i5b302.p.ssafy.io/api/account/profile/${this.nickname}/follower`,
        headers: {
          'Content-Type': 'application/json',
          'X-AUTH-TOKEN' : this.$store.state.token
        },
      })
      .then((res) => {
        this.followerLs = res.data
        this.followers = this.followerLs.length

        this.followingList()
      })
    },
    followingList () {
      axios({
        method: 'get',
        url: `https://i5b302.p.ssafy.io/api/account/profile/${this.nickname}/following`,
        headers: {
          'Content-Type': 'application/json',
          'X-AUTH-TOKEN' : this.$store.state.token
        },
      })
      .then((res) => {
        this.followingLs = res.data
        this.followings = this.followingLs.length
      })
    },
    cancelFollow () {
      axios({
        method: 'delete',
        url: `https://i5b302.p.ssafy.io/api/account/profile/follow/`,
        headers: {
          'Content-Type': 'application/json',
          'X-AUTH-TOKEN' : this.$store.state.token
        },
        params: {
          'srcnickname': this.myNickname,
          'dstnickname': this.nickname
        }
      })
      .then((res) => {
        this.didIrequestFollowToYou = !this.didIrequestFollowToYou
      })
    },
    goToArticleDetail (articleid) {
      this.$router.push({
        name: 'ArticleDetail',
        params: {articleid: articleid}
      })
    },
    getThumbnailImgUrl (payload) {
      return {
        ...this.thumbnail,
        thumbnail: this.thumbnail && `https://i5b302.p.ssafy.io/img/${payload.imgURL}`
      }
    },
    getArticleImgUrl (payload) {
      if (this.articles[payload.idx].images.length !== 0) {
        return {
          ...this.articles,
          thumbnail: this.articles[payload.idx].images.length && `https://i5b302.p.ssafy.io/img/${payload.imgURL[0].imgURL}`
        }
      }
      return {
        thumbnail: '@/assets/images/img-placeholder.png'
      }
    }
  },
  created () {
    // this.getUserInfo()
    axios({
      method: 'get',
      url: `https://i5b302.p.ssafy.io/api/account/checkJWT/`,
      headers: {
        'Content-Type': 'application/json',
        'X-AUTH-TOKEN' : this.$store.state.token
      },
    })
    .then((res) => {
      this.myNickname = this.$store.state.username
      this.myUid=res.data.uid
      this.getUserInfo()
    })
    .catch((err) => {
      alert(err)
    })

    axios({
      url: `https://i5b302.p.ssafy.io/api/account/status/${this.nickname}`,
      method: 'get',
      headers: {
        'Content-Type': 'application/json',
        'X-AUTH-TOKEN' : this.$store.state.token
      }
    })
      .then(res => {
        this.status = res.data
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
    background-color: black;
    background-size:cover; /* you change this to "contain" if you don't want the images to be cropped */
}

/* .img_1{background-image:url('img-placeholder.png');} */
.img_1{background-image:url('../../assets/images/img-placeholder.png');}
</style>