<template>
  <div id="app">
    <b-nav v-if="navShow" id="custom-navbar" class="d-flex justify-content-between nav" style="position:absolute;">
      <b-nav-item>
        <img src="./assets/images/main-icon-2.png" alt="" style="width: 83px; height: 35px;">
      </b-nav-item>
      <button type="button" @click="onToggleDarkMode"><b-icon id="icon" icon="moon"></b-icon></button>
      <b-nav-item class="d-flex align-items-center nav-item">
        <router-link :to="{ name: 'PromiseList' }"  class="text-decoration-none me-3 text-dark">
          <b-icon id="icon" icon="signpost" ></b-icon>
        </router-link>
        <router-link :to="{ name: 'AlarmList' }"  class="text-decoration-none me-3 text-dark">
          <b-icon id="icon" icon="bell-fill"></b-icon>
        </router-link>
        <router-link :to="{ name: 'SearchUser' }" class="text-decoration-none me-3 text-dark">
          <b-icon id="icon" icon="search"></b-icon>
        </router-link>
        <router-link :to="{ name: 'ArticleCreate' }" class="text-decoration-none me-3 text-dark">
          <b-icon id="icon" icon="pencil-square"></b-icon>
        </router-link>
        <b-dropdown id="dropdownMenuButton" size="sm" right variant="link" toggle-class="text-decoration-none" no-caret>
          <template #button-content >
            <b-icon id="icon"  icon="person-fill"></b-icon>
          </template>
          <b-dropdown-item id="dropdownitem" :to="{ name: 'ProfileDetail', params: { nickname: username } }">자신의 프로필</b-dropdown-item>
          <b-dropdown-item id="dropdownitem" :to="{ name: 'Scrap'}">자신의 스크랩</b-dropdown-item>
          <b-dropdown-item id="dropdownitem" :to="{ name: 'PromiseCreate', }">약속 생성하기</b-dropdown-item>
          <b-dropdown-item-button id="dropdownitem"  class="danger" @click="logout()">로그아웃</b-dropdown-item-button>
        </b-dropdown>
      </b-nav-item>
    </b-nav>
    <b-nav v-if="backShow">
      <button @click="goBack"><b-icon icon="arrow-left" class="me-4"></b-icon></button>
      <span class="fw-bold" style="font-size:2rem;">{{ this.$route.name }}</span>
    </b-nav>
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
      backShow: true,
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
        this.backShow = false
      } 
      else if (this.$route.name === 'ArticleCreate' || this.$route.name === 'ArticleDetail' || this.$route.name === 'Comments' || this.$route.name === 'PromiseCreate' || this.$route.name === 'PromiseList') {
        this.navShow = false
        this.backShow = true
      } 
      else {
        this.navShow = false
        this.backShow = false
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
      this.backShow = false
    }  
    else if (this.$route.name === 'ArticleCreate' || this.$route.name === 'ArticleDetail' || this.$route.name === 'Comments') {
        this.navShow = false
        this.backShow = true
    }
    else {
      this.navShow = false
      this.backShow = false
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
      if (window) {
        console.log(window.matchMedia('(prefers-color-scheme: dark)').matches);

        if (window.matchMedia('(prefers-color-scheme: dark)').matches) {
          if(document.documentElement.classList.contains('darkmode')) {
            document.documentElement.classList.remove("darkmode");
            document.documentElement.classList.add("lightmode");
            document.getElementById("custom-navbar").style.position
          } else {
            document.documentElement.classList.remove("lightmode");
            document.documentElement.classList.add("darkmode");
          }
        } else {
          if(document.documentElement.classList.contains('lightmode')) {
            document.documentElement.classList.remove("lightmode");
            document.documentElement.classList.add("darkmode");
          } else {
            document.documentElement.classList.remove("darkmode");
            document.documentElement.classList.add("lightmode");
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
          url: 'http://127.0.0.1:8080/promise',
          method: 'get',
          headers: {
            "Content-Type": "application/json",
            "X-AUTH-TOKEN": this.$store.state.token,
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
                url: `http://127.0.0.1:8080/promise/place/${promiseIds[idx]}`,
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
  if (document.getElementById('custom-navbar') !== null) {
    var currentScrollPos = window.pageYOffset;
    if (prevScrollpos < 100) {
      document.getElementById("custom-navbar").style.position = 'absolute'
    } else if (prevScrollpos > currentScrollPos) {
      document.getElementById("custom-navbar").style.top = "0"
      document.getElementById("custom-navbar").style.position = 'fixed'
      document.getElementById("custom-navbar").style.zIndex = 2
    } else {
      document.getElementById("custom-navbar").style.top = "-50px"
    }
    prevScrollpos = currentScrollPos;
  }
}
</script>

<style src="./App.css">
</style>