<template>
  <div id="app">
    <b-nav justified v-if="navShow" id="top-custom-navbar" class="d-flex justify-content-between nav">
      <b-nav-item>
        <!-- <img src="./assets/images/main-icon-2.png" alt="" style="width: 83px; height: 35px;"> -->
        <h1 style="font-family: 'Pacifico', cursive;">Pipl.</h1>
      </b-nav-item>
      <b-nav-item class="d-flex align-items-center nav-item">
        <router-link :to="{ name: 'AlarmList' }"  class="text-decoration-none me-3 text-dark">
          <b-icon id="icon" icon="bell-fill"></b-icon>
          <p style="font-size:0.8rem">알람</p>
        </router-link>
      </b-nav-item>
      <b-nav-item>
        <router-link :to="{ name: 'SearchUser' }" class="text-decoration-none me-3 text-dark">
          <b-icon id="icon" icon="search"></b-icon>
          <p style="font-size:0.8rem">유저검색</p>
        </router-link>
      </b-nav-item>
        <b-nav-item>
        <b-dropdown id="dropdownMenuButton" size="sm" right variant="link"
          toggle-class="text-decoration-none d-flex flex-column align-items-center p-0" no-caret>
          <template #button-content >
            <b-icon id="icon"  icon="person-fill"></b-icon>
            <p style="font-size:0.8rem padding-top: 0.1rem;">프로필</p>
          </template>
          <b-dropdown-item id="dropdownitem" :to="{ name: 'ProfileDetail', params: { nickname: username } }">자신의 프로필</b-dropdown-item>
          <b-dropdown-item id="dropdownitem" :to="{ name: 'Scrap'}">자신의 스크랩</b-dropdown-item>
          <b-dropdown-item id="dropdownitem" :to="{ name: 'PromiseCreate', }">약속 생성하기</b-dropdown-item>
          <b-dropdown-item id="dropdownitem "><b-icon icon= "moon"></b-icon><button id="mode" type="button" @click="onToggleDarkMode">{{ mode }}</button></b-dropdown-item>

          <b-dropdown-item-button id="dropdownitem"  class="danger" @click="logout()">로그아웃</b-dropdown-item-button>
        </b-dropdown>
      </b-nav-item>
    </b-nav>
    <b-nav justified v-if="navShow" id="bottom-custom-navbar">
      <b-nav-item>
          <router-link :to="{ name: 'PromiseList' }"  class="text-decoration-none me-3 text-dark" >
            <b-icon font-scale="2.3" id="icon" icon="signpost" ></b-icon>
            <p style="font-size:0.8rem">PromiseList</p>
          </router-link>
      </b-nav-item>
      <b-nav-item>
          <router-link :to="{ name: 'FeedMain' }"  class="text-decoration-none me-3 text-dark">
            <b-icon font-scale="2.3" id="icon" icon="house-door" ></b-icon>
            <p style="font-size:0.8rem">Home</p>
          </router-link>
      </b-nav-item>
      <b-nav-item>
          <router-link :to="{ name: 'ArticleCreate' }" class="text-decoration-none me-3 text-dark">
            <b-icon font-scale="2.3" id="icon" icon="pencil-square"></b-icon>
            <p style="font-size:0.8rem">Create</p>
          </router-link>
      </b-nav-item>
    </b-nav>
    <div>
    <b-nav justified v-if="bottomNavShow" id="bottom-custom-navbar2">
      <b-nav-item>
          <router-link :to="{ name: 'PromiseList' }"  class="text-decoration-none me-3 text-dark" >
            <b-icon font-scale="2.3" id="icon" icon="signpost" ></b-icon>
            <p style="font-size:0.8rem">PromiseList</p>
          </router-link>
      </b-nav-item>
      <b-nav-item>
          <router-link :to="{ name: 'FeedMain' }"  class="text-decoration-none me-3 text-dark">
            <b-icon font-scale="2.3" id="icon" icon="house-door" ></b-icon>
            <p style="font-size:0.8rem">Home</p>
          </router-link>
      </b-nav-item>
      <b-nav-item>
          <router-link :to="{ name: 'ArticleCreate' }" class="text-decoration-none me-3 text-dark">
            <b-icon font-scale="2.3" id="icon" icon="pencil-square"></b-icon>
            <p style="font-size:0.8rem">Create</p>
          </router-link>
      </b-nav-item>
    </b-nav>
    <div v-if="commentNavShow">
      <div class="mt-3 mx-4 d-flex justify-content-between align-items-center">
        <span class="fs-1">
          <button @click="goBack"><b-icon id="icon" icon="arrow-left" class="me-4"></b-icon></button>
          <span class="fw-bold">댓글 목록</span>
        </span>
      </div>
    </div>
  </div>
    <router-view></router-view>
  </div>
</template>

<script>
import axios from 'axios'
import "./components/css/style.scss";
import { mapState, mapActions } from 'vuex'

export default {
  name: "app",
  data() {
    return {
      navShow: true,
      bottomNavShow: false,
      commentNavShow: false,
      mode:"",
    }
  },
  computed: {
    ...mapState([
      'token',
      'username'
    ])
  },
  watch: {
    $route () {
      if (this.$route.name === 'FeedMain') {
        this.navShow = true
        this.bottomNavShow= false
        this.commentNavShow= false
      }
      else if( this.$route.name === 'ArticleCreate' || this.$route.name === 'ArticleDetail' || this.$route.name === 'FollowList' || this.$route.name === 'ProfileDetail' || this.$route.name === 'ProfileUpdate' || this.$route.name === 'Scrap' || this.$route.name === 'PromiseCreate' || this.$route.name === 'PromiseLsit'|| this.$route.name === 'PromiseLocation'|| this.$route.name === 'SearchUser'|| this.$route.name === 'ChangePassword'|| this.$route.name === 'PromiseDetail' ){
        this.navShow = false
        this.bottomNavShow= true
        this.commentNavShow= false
      }
      else if( this.$route.name === 'Comments' ){
        this.navShow = false
        this.bottomNavShow= false
        this.commentNavShow= true
      }
      else {
        this.navShow = false
        this.bottomNavShow= false
        this.commentNavShow= false
      }

      if (localStorage.getItem('token')) {
        navigator.geolocation.getCurrentPosition((position) => {
          let location = {
            lat: position.coords.latitude,
            lon: position.coords.longitude
          }
          this.userLocationUpdate(location)
        })
      }
    }
  },
  created() {
    if (this.$route.name === 'FeedMain') {
        this.navShow = true
        this.bottomNavShow= false
        this.commentNavShow= false
      }
      else if( this.$route.name === 'ArticleCreate' || this.$route.name === 'ArticleDetail' || this.$route.name === 'FollowList' || this.$route.name === 'ProfileDetail' || this.$route.name === 'ProfileUpdate' || this.$route.name === 'Scrap' || this.$route.name === 'PromiseCreate' || this.$route.name === 'PromiseLsit'|| this.$route.name === 'PromiseLocation'|| this.$route.name === 'SearchUser' || this.$route.name === 'ChangePassword' || this.$route.name === 'PromiseDetail' ){
        this.navShow = false
        this.bottomNavShow= true
        this.commentNavShow= false
      }
      else if( this.$route.name === 'Comments' ){
        this.navShow = false
        this.bottomNavShow= false
        this.commentNavShow= true
      }
      else {
        this.navShow = false
        this.bottomNavShow= false
        this.commentNavShow= false
      }
    if(window && window.matchMedia('(prefers-color-scheme: dark)').matches) {
      this.mode= "lightmode"
    } else {
      this.mode= "darkmode"
    }
  },
  mounted() {
    if(window && window.matchMedia('(prefers-color-scheme: dark)').matches) {
      document.documentElement.classList.add("darkmode");
    } else {
      document.documentElement.classList.add("lightmode");
    }
  },
  methods:{
    onToggleDarkMode() {
      var darkmode = document.getElementById("mode")

      if (window) {
        console.log(window.matchMedia('(prefers-color-scheme: dark)').matches);

        if (window.matchMedia('(prefers-color-scheme: dark)').matches) {
          if(document.documentElement.classList.contains('darkmode')) {
            document.documentElement.classList.remove("darkmode");
            document.documentElement.classList.add("lightmode");
            this.mode= "darkmode"

          } else {
            document.documentElement.classList.remove("lightmode");
            document.documentElement.classList.add("darkmode");
            this.mode= "lightmode"
          }
        } else {
          if(document.documentElement.classList.contains('lightmode')) {
            document.documentElement.classList.remove("lightmode");
            document.documentElement.classList.add("darkmode");
            this.mode= "lightmode"
          } else {
            document.documentElement.classList.remove("darkmode");
            document.documentElement.classList.add("lightmode");
            this.mode= "darkmode"
          }
        }

      }
    },
    ...mapActions([ 
      'logout'
    ]),
    goBack() {
      this.$router.go(-1)
    },
    userLocationUpdate(location) {
      if (!this.$store.state.promiseDeleteMode) {
        axios({
          url: 'https://i5b302.p.ssafy.io/api/promise',
          method: 'get',
          headers: {
            "Content-Type": "application/json",
            "X-AUTH-TOKEN": this.$store.state.token
          }
        })
          .then(res => {
            let promiseIds = []
  
            for (let idx = 0; idx < res.data.waiting.length; idx++) {
              if (!promiseIds.includes(res.data.waiting[idx].promiseid)) {
                promiseIds.push(res.data.waiting[idx].promiseid)
              }
            }
  
            let payload = {
              data: res.data,
              promiseIds: promiseIds
            }
  
            return payload
          })
          .then(payload => {
            let promiseIdsWithUpcoming = payload.promiseIds
  
            for (let idx = 0; idx < payload.data.upcoming.length; idx++) {
              if (!promiseIdsWithUpcoming.includes(payload.data.upcoming[idx].promiseid)) {
                promiseIdsWithUpcoming.push(payload.data.upcoming[idx].promiseid)
              }
            }
  
            return promiseIdsWithUpcoming
          })
          .then(promiseIds => {
            let formdata = new FormData()
  
            formdata.append('lat', location.lat)
            formdata.append('lon', location.lon)
  
            for (let idx = 0; idx < promiseIds.length; idx++) {
              axios({
                url: `https://i5b302.p.ssafy.io/api/promise/place/${promiseIds[idx]}`,
                method: 'put',
                headers: {
                  "Content-Type": "application/json",
                  "X-AUTH-TOKEN": this.$store.state.token,
                },
                data: formdata
              })
            }
          })
      }
    }
  }
}

var prevScrollpos = window.pageYOffset
window.onscroll = function() {
  if (document.getElementById('top-custom-navbar') !== null && document.getElementById('bottom-custom-navbar') !== null) {
    var currentScrollPos = window.pageYOffset;
    if (prevScrollpos < 100) {
      document.getElementById("top-custom-navbar").style.position = 'fixed'
      document.getElementById("bottom-custom-navbar").style.position = 'fixed'
    } else if (prevScrollpos > currentScrollPos) {
      document.getElementById("top-custom-navbar").style.top = "0"
      document.getElementById("top-custom-navbar").style.position = 'fixed'
      document.getElementById("top-custom-navbar").style.zIndex = 2

      document.getElementById("bottom-custom-navbar").style.bottom = "0"
      document.getElementById("bottom-custom-navbar").style.position = 'fixed'
      document.getElementById("bottom-custom-navbar").style.zIndex = 2
    } else {
      document.getElementById("top-custom-navbar").style.top = "-75px"
      document.getElementById("bottom-custom-navbar").style.bottom = "-75px"
    }
    prevScrollpos = currentScrollPos;
  }
}
</script>

<style src="./App.css">
</style>