<template>
  <div class="user" id="login">
      <div class="jb-text">
        <center>
          <img class="main-icon" src="../../assets/images/main-icon.png" alt="2">
        </center>
        <div class="wrapC">
          <div class="input-with-label">
            <input
              v-model="email"
              v-bind:class="{error : error.email, complete:!error.email&&email.length!==0}"
              @keyup.enter="login({ email, password })"
              id="email"
              placeholder="이메일을 입력하세요."
              type="text"
            />
            <label for="email">이메일</label>
            <div class="error-text" v-if="error.email">{{error.email}}</div>
          </div>
          <div class="input-with-label">
            <input
              v-model="password"
              type="password"
              v-bind:class="{error : error.password, complete:!error.password&&password.length!==0}"
              id="password"
              @keyup.enter="login({ email, password })"
              placeholder="비밀번호를 입력하세요."
            />
            <label for="password">비밀번호</label>
            <div class="error-text" v-if="error.password">{{error.password}}</div>
          </div>
          <div>
            <button
              class="btn btn-primary shadow-none"
              @click="login({ email, password })"
              :disabled="!isSubmit"
              style="height: 2.7rem; width: 100%;"
            >로그인</button>
          </div>

          <div class="sns-login">
            <div class="text">
              <p>SNS 간편 로그인</p>
              <KakaoLogin :component="component" />
              <!-- <GoogleLogin :component="component" /> -->
              <div class="bar"></div>
            </div>
          </div>
          
          <div class="add-option">
            <div class="text">
              <p>혹시</p>
              <div class="bar"></div>
            </div>
            <div class="wrap">
              <p>아직 회원이 아니신가요?</p>
              <router-link to="/account/signup" class="btn--text">가입하기</router-link>
            </div>
            <div class="wrap">
              <p>처음이신가요?</p>
              <router-link to="/onboarding" class="btn--text">온보딩</router-link>
            </div>
          </div>
        </div>
      </div>
  </div>
</template>

<script>

import "../../components/css/user.scss";
import PV from "password-validator";
import * as EmailValidator from "email-validator";
import KakaoLogin from "../../components/user/snsLogin/Kakao.vue";
import GoogleLogin from "../../components/user/snsLogin/Google.vue";

import { mapActions, mapState } from 'vuex'

export default {
  name:'Login',
  components: {
    KakaoLogin,
    // GoogleLogin
  },
  created() {
    if (this.token !== '') {
      this.$router.push({ name: 'FeedMain' })
    }
    
    this.component = this;

    this.passwordSchema
      .is()
      .min(8)
      .is()
      .max(100)
      .has()
      .digits()
      .has()
      .letters();
  },
  computed: {
    ...mapState([
      'loginState',
      'token'
    ])
  },
  watch: {
    password: function(v) {
      this.checkForm();
    },
    email: function(v) {
      this.checkForm();
    }
  },
  methods: {
    checkForm() {
      if (this.email.length >= 0 && !EmailValidator.validate(this.email))
        this.error.email = "이메일 형식이 아닙니다.";
      else this.error.email = false;

      if (
        this.password.length >= 0 &&
        !this.passwordSchema.validate(this.password)
      )
        this.error.password = "영문,숫자 포함 8 자리이상이어야 합니다.";
      else this.error.password = false;

      let isSubmit = true;
      Object.values(this.error).map(v => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
    },
    ...mapActions([
      'login'
    ])
  },
  data: () => {
    return {
      email: "",
      password: "",
      passwordSchema: new PV(),
      error: {
        email: false,
        passowrd: false
      },
      isSubmit: false,
      component: this
    };
  }
};
</script>
<style scoped>
  @import url('https://fonts.googleapis.com/css2?family=Pacifico&display=swap');
  template { padding: 0px; margin: 0px; }
  .jb-box {  width: 100%; overflow: hidden; margin: 0px auto; position: fixed; }
  .jb-text { position: absolute; top: 5%; width: 100%; }
  .jb-text p {color: black; }
  .main-icon { width: 130px; margin-bottom: 10px; }
</style>