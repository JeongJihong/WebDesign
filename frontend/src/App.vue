<template>
  <div id="app">

    <b-navbar type="dark" variant="dark" style="justify-content:space-between;">
      <b-navbar-nav>
        <b-nav-item href="#">Home</b-nav-item>
      <div class='right'>
        <router-link :to="{ name:'Login'}">로그인</router-link>
        <router-link :to="{ name:'Signup'}">회원가입</router-link>
        <router-link :to="{ name:'FeedMain'}">메인</router-link>
        <router-link :to="{ name:'ArticleCreate'}">글생성</router-link>
      </div>
      </b-navbar-nav>
    </b-navbar>
    <router-view></router-view>
  </div>
</template>
<script>
import "./components/css/style.scss";
export default {
  name: "app"
};
</script>
<style scoped>
  .right {
    float: right;
    
  }
</style>