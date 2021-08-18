<template>
  <div class="user d-flex flex-column justify-content-between mt-2" id="login" >
    <br>
      <div class="jb-text">
          <h1 class="d-flex flex-column justify-content-center" style="font-family: 'Pacifico', cursive;">Pipl.</h1>
        <br>
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
              class="d-flex align-items-center justify-content-center btn btn-primary shadow-none"
              @click="login({ email, password })"
              :disabled="!isSubmit"
              style="height: 2.7rem; width: 100%; margin-bottom: 2rem;"
            >로그인</button>
          </div>
          <div class="hr-sect">OR</div>
          <div style="margin-top:10px; width:100%;">
              <KakaoLogin :component="component" />
          </div>
          <br>
          <br>
          <div class="add-option">
            <!-- <div class="text">
              <p>혹시</p>
              <div class="bar"></div>
            </div> -->
            <div class="wrap d-flex justify-content-between align-items-center">
              <p class="m-0">아직 회원이 아니신가요?</p>
              <router-link to="/account/signup" class="btn--text">가입하기</router-link>
            </div>
          </div>
        </div>
      </div>
      <div style="height: 8vh;"></div>
      <div class="d-flex flex-column align-items-center">
          <p class="my-1" style="font-size: 0.85rem;">made by</p>
          <p style="font-size: 1.1rem; font-weight: 1000;">지금 어디시조</p>
      </div>
  </div>
</template>

<script>

import "../../components/css/user.scss";
import PV from "password-validator";
import * as EmailValidator from "email-validator";
import KakaoLogin from "../../components/user/snsLogin/Kakao.vue";

import { mapActions, mapState } from 'vuex'

export default {
  name:'Login',
  components: {
    KakaoLogin,
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
      'token',
      'loginFailed'
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
        this.error.password = "영문,숫자,특수문자 포함 8 자리이상이어야 합니다.";
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
  /* .jb-text { position: absolute; top: 5%; width: 100%; } */
  .jb-text { top: 5%; width: 100%; }
  .jb-text p {color: black; }
  .main-icon { width: 130px; margin-bottom: 10px; }
  .hr-sect {
        display: flex;
        flex-basis: 100%;
        align-items: center;
        color: rgba(0, 0, 0, 0.35);
        font-size: 12px;
        margin: 8px 0px;
      }
      .hr-sect::before,
      .hr-sect::after {
        content: "";
        flex-grow: 1;
        background: rgba(0, 0, 0, 0.35);
        height: 1px;
        font-size: 0px;
        line-height: 0px;
        margin: 0px 16px;
      }
</style>