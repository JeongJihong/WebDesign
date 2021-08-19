<template>
  <div style="margin-bottom:80px;">
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
      <div class="d-flex align-items-center">
        <b-avatar v-if="thumbnail" class="me-2" size="4rem" style="border: 1px solid black;"
          :src="getThumbnailImgUrl({ imgURL: thumbnail }).thumbnail"></b-avatar>
        <b-avatar v-else class="me-2" size="4rem"></b-avatar>
        <div class="d-flex flex-column" style="margin-left: 1rem;">
          <div class="align-items-center">
            <span class="fs-4">{{ this.nickname }}</span>
          </div>
          <div v-if="introduction" class="align-items-center" style="margin-top: 0.5rem; margin-botton: 0">
            <p style="font-size: 0.85rem;">{{ this.introduction }}</p>
          </div>
        </div>
      </div>
      <div v-if="this.nickname !== this.myNickname">
        <div class="d-grid pt-3">
          <button @click="goToFollow" v-if="this.nickname !== this.myNickname && this.didIrequestFollowToYou === false" class="btn btn-primary shadow-none" style="display: flex; height: 30px; justify-content: center; align-items: center;">팔로우</button>
          <button v-if="this.nickname !== this.myNickname && this.didIrequestFollowToYou === true && this.doIFollowYou === false" @click="cancelFollow" class="btn btn-outline-primary shadow-none" style="display: flex; height: 30px; justify-content: center; align-items: center; color: #0d6efd;">팔로우 요청 보냄</button>
          <button @click="unfollow" v-if="this.doIFollowYou === true" class="btn btn-outline-primary shadow-none" style="display: flex; height: 30px; justify-content: center; align-items: center; color: #0d6efd;">팔로우 취소</button>
        </div>
        <div v-if="this.didYouRequestFollowToMe === true">
          <p class="pt-2 pb-0 mb-0">{{ this.nickname }}님의 팔로우 요청</p>
          <div class="d-flex">
            <button @click="approveFollowRequest" class="col-6 btn btn-outline-secondary shadow-none" style="display: flex; height: 30px; justify-content: center; align-items: center; color: #0d6efd;">승인</button>
            <button @click="rejectFollowRequest" class="col-6 btn btn-dark shadow-none" style="display: flex; height: 30px; justify-content: center; align-items: center;">거절</button>
          </div>
        </div>
      </div>
      <div class="mt-3">
        <div class="d-flex justify-content-between">
          <span :style="{'font-size': '0.9rem', 'color' : textColor}" >피플포인트<b-icon icon="emoji-heart-eyes" style="margin-left: 0.5rem;"></b-icon></span>
          <span :style="{'font-size': '0.9rem', 'color' : textColor}">{{this.status + 10.0}}p</span>
        </div>
        <b-progress :value="status+10" :max="30" class="mb-1" :variant="variant" height="0.4rem"></b-progress>
      </div>
      <div style="margin-top: 2rem;" class="d-flex justify-content-between">
        <span id="app" :style="{'font-size': '0.8rem', 'color' : textColor}">{{ this.recommendedExpressions }}</span>
        <span id="app" :style="{'font-size': '0.8rem', 'color' : textColor}">=></span>
        <router-link id="app" to="/promise/create" class="btn--text" style="font-size: 0.8rem;">약속 잡기</router-link>
      </div>
      <hr>
      <div class="d-flex mx-2" style="justify-content: space-between; margin-top: 2rem;">
        <button class="d-flex flex-column justify-content-center align-items-center">
          <span style="font-size: 1.2rem;" id="app">생성한 약속</span>
          <span style="font-size: 1rem;" id="app">{{this.promisesLength}}회</span>
        </button>
        <div style="border-left: 1px solid #999999"></div>
        <button class="d-flex flex-column justify-content-center align-items-center" @click="goToFollowList">
          <span style="font-size: 1.2rem;" id="app">팔로워</span>
          <span style="font-size: 1rem;" id="app">{{ this.followers }}</span>
          <!-- <span style="color:blue;">{{ this.followings }}</span> -->
        </button>
        <div style="border-left: 1px solid #999999"></div>
        <button class="d-flex flex-column justify-content-center align-items-center" @click="goToFollowList">
          <span style="font-size: 1.2rem;" id="app">팔로잉</span>
          <span style="font-size: 1rem;" id="app">{{ this.followings }}</span>
          <!-- <span style="color:blue;">{{ this.followers }}</span> -->
        </button>
      </div>
      <!-- <div class="pt-4">
        <p>본인소개글</p>
        <p>{{ this.introduction }}</p>
      </div> -->
      
      <div class="mt-3">
        <b-tabs content-class="mt-3" fill>
          <b-tab class="" :title="'게시글 (' + nonPromisesLength + ')'" active>
            <div 
              v-for="image in this.nonPromisesLength" 
              :key="image" 
              @click="goToArticleDetail(justArticles[image-1].articleid)" 
              class="square" 
              :style="{ backgroundImage: 'url(' + getArticleImgUrl({ idx: justArticlesIndex[image-1], imgURL: justArticles[image-1].images }).thumbnail + ')' }"
            >
            </div>
          </b-tab>
          <b-tab :title="'약속 (' + promisesLength + ')'" active>
            <div 
              v-for="image in this.promisesLength" 
              :key="image" 
              @click="goToArticleDetail(promiseArticles[image-1].articleid)" 
              class="square" 
              :style="{ backgroundImage: 'url(' + getArticleImgUrl({ idx: promiseArticlesIndex[image-1], imgURL: promiseArticles[image-1].images }).thumbnail + ')' }"
            >
              
              <span class="d-flex justify-content-center align-items-center" style="position: absolute; height:100%; width:100%">{{ promiseArticles[image-1].type }}</span>
              <!-- <span class="square" style="position: absolute; margin-top:35%;">{{ promiseArticles[image-1].type }}</span> -->
              <!-- <span>{{ promiseArticles[image-1] }}</span> -->
            </div>
          </b-tab>
        </b-tabs>
      </div>
      <!-- <div 
        v-for="image in this.articlesLength" 
        :key="image" 
        @click="goToArticleDetail(articles[image-1].articleid)" 
        class="square" 
        :style="{ backgroundImage: 'url(' + getArticleImgUrl({ idx: image-1, imgURL: articles[image-1].images }).thumbnail + ')' }"
      >
      </div> -->

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
      promisesLength: 0,
      nonPromisesLength: 0,
      recommendedExpressions: '',
      variant: '',
      textColor: '',
      promiseArticles: [],
      justArticles: [],
      promiseArticlesIndex: [],
      justArticlesIndex: [],
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
        if (!this.articles) {
          this.articlesLength = 0
        }
        else {
          this.articlesLength = this.articles.length
        }
        this.thumbnail = res.data.userProfile.thumbnail
        if (!this.introduction) {
          this.introduction = ''
        }
        // if (typeof this.thumbnail === 'undefined') {
        //   this.thumbnail = `https://i5b302.p.ssafy.io/img/profile_default.png`)
        // }
        // 약속 수 계산 s
        console.log(this.articles)
        let i = 0;
        let promiseNum = 0;
        for (i = 0; i < this.articlesLength; i++) {
          console.log(this.articles[i].article.promiseid)
          if (this.articles[i].article.promiseid) {
            promiseNum++;
            this.promiseArticles.push(this.articles[i])
            this.promiseArticlesIndex.push(i)
          }
          else {
            this.justArticles.push(this.articles[i])
            this.justArticlesIndex.push(i)
          }
        }
        this.promisesLength = promiseNum;
        this.nonPromisesLength = this.articlesLength - promiseNum;
        // 추천 문구 만들기
        if (this.status > 15) {
          this.recommendedExpressions = "핵인싸인 당신에게 주어지는 Pipl 목걸이!"
          this.variant = "orange"
          this.textColor = "#FF8657"
        }
        else if (this.status > 10) {
          this.recommendedExpressions = "이미 인싸인 당신! 핵인싸가 멀지 않았다..!"
          this.variant = "warning"
          this.textColor = "#FFC107"
        }
        else if (this.status > 5) {
          this.recommendedExpressions = "조금만 더 달려서 인싸의 길로 가즈아!"
          this.variant = "success"
          this.textColor = "#4CAF50"
        }
        else if (this.status > 1) {
          this.recommendedExpressions = "오늘도 Pipl과 함께 약속을 만들어 봐요!"
          this.variant = "primary"
          this.textColor = "#14C6FF"
        }
        else if (this.status === 1) {
          this.recommendedExpressions = "첫 약속은 어떠셨나요? 두 번째 약속도 Pipl!"
          this.variant = "primary"
          this.textColor = "#14C6FF"
        }
        else if (this.status === 0) {
          this.recommendedExpressions = "Pipl과 함께 새로운 약속을 만들고 싶다면?"
          this.variant = "info"
          this.textColor = "#14c6FF"
        }
        else {
          this.recommendedExpressions = "Pipl에서 잡은 약속은 꼭 지켜주세요!"
          this.variant = "secondary"
          this.textColor = "#424242"
        }
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
        if (this.followerLs) {
          this.followers = this.followerLs.length
        }
        else {
          this.followers = 0
        }
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
        if (this.followingLs) {
          this.followings = this.followingLs.length
        }
        else {
          this.followings = 0
        }
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
      // if (this.articles[payload.idx].images.length !== 0) {
      if (!this.articles[payload.idx].images) {
        return {
          thumbnail: '@/assets/images/img-placeholder.png'
        }
      }
      else {
        return {
          ...this.articles,
          thumbnail: this.articles[payload.idx].images.length && `https://i5b302.p.ssafy.io/img/${payload.imgURL[0].imgURL}`
        }
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