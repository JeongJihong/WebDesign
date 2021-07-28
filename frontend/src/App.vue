<template>
  <div id="app">

    <b-nav v-if="navShow" id="custom-navbar" class="d-flex justify-content-between">
      <b-nav-item>
        <img src="./assets/images/main-icon-2.png" alt="" style="width: 83px; height: 35px;">
      </b-nav-item>
      <b-nav-item class="d-flex align-items-center">
        <router-link :to="{ name: 'AlarmList' }"  class="text-decoration-none me-3 text-dark">
          <span>알림</span>
        </router-link>
        <router-link :to="{ name: 'SearchUser' }" class="text-decoration-none me-3 text-dark">
          <span>검색</span>
        </router-link>
        <router-link :to="{ name: 'ArticleCreate' }" class="text-decoration-none me-3 text-dark">
          <span>피드작성</span>
        </router-link>
        <router-link :to="{ name: 'ProfileDetail' }" class="text-decoration-none text-dark">
          <span>프로필</span>
        </router-link>
      </b-nav-item>
    </b-nav>

    <router-view></router-view>

  </div>
</template>

<script>
import "./components/css/style.scss";

import { mapState } from 'vuex'

export default {
  name: "app",
  data() {
    return {
      navShow: true,
    }
  },
  computed: {
    ...mapState([
      'token'
    ])
  },
  watch: {
    $route () {
      console.log('일단 확인')
      if (this.$route.name === 'FeedMain') {
        this.navShow = true
        console.log('메인 맞음')
      } else {
        this.navShow = false
        console.log('메인 아님')
      }
    }
  },
  created() {
    console.log('일단 확인')
    if (this.$route.name === 'FeedMain') {
      this.navShow = true
      console.log('메인 맞음')
    } else {
      this.navShow = false
      console.log('메인 아님')
    }
  }
}

var prevScrollpos = window.pageYOffset
window.onscroll = function() {
  var currentScrollPos = window.pageYOffset;
  if (prevScrollpos < 100) {
    document.getElementById("custom-navbar").style.position = 'static'
  } else if (prevScrollpos > currentScrollPos) {
    document.getElementById("custom-navbar").style.top = "0"
    document.getElementById("custom-navbar").style.position = 'fixed'
    document.getElementById("custom-navbar").style.background = 'linear-gradient(rgba(255, 255, 255, 1), rgba(255, 255, 255, 0.8))'
  } else {
    document.getElementById("custom-navbar").style.top = "-50px"
  }
  prevScrollpos = currentScrollPos;
}
</script>

<style scoped>
#custom-navbar {
  background-color: white;
  position: static;
  z-index: 101;
  left: 0;
  right: 0;
  top: 0;
  width: 100%;
  transition: top 0.3s;
}
</style>