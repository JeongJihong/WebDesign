<template>
  <div id="app">

    <b-nav v-if="navShow" id="custom-navbar" class="d-flex justify-content-between">
      <b-nav-item>
        <router-link :to="{ name: 'FeedMain' }" class="text-decoration-none fw-bold">Home</router-link>
      </b-nav-item>
      <b-nav-item>
        <router-link :to="{ name: 'Login' }"  class="text-decoration-none me-3 text-dark">
          <span>로그인</span>
        </router-link>
        <router-link :to="{ name: 'Signup' }" class="text-decoration-none me-3 text-dark">
          <span>회원가입</span>
        </router-link>
        <router-link :to="{ name: 'ArticleCreate' }" class="text-decoration-none text-dark">
          <span>글생성</span>
        </router-link>
      </b-nav-item>
    </b-nav>

    <router-view></router-view>

  </div>
</template>

<script>
import "./components/css/style.scss";

export default {
  name: "app",
  data() {
    return {
      navShow: true,
      routerShow: true
    }
  },
  watch: {
    $route () {
      if (['FeedMain', 'Scrap'].includes(this.$route.name)) {
        this.navShow = true
      } else {
        this.navShow = false
      }
    }
  }
};

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