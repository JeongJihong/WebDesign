
<!--
    가입하기는 기본적인 폼만 제공됩니다
    기능명세에 따라 개발을 진행하세요.
    Sub PJT I에서는 UX, 디자인 등을 포함하여 백엔드를 제외하여 개발합니다.
 -->
<template>
  <div>
    <div class="mt-3 mx-4 d-flex justify-content-between align-items-center">
      <span class="fs-1">
        <button @click="goBack"><b-icon id="icon" icon="arrow-left" class="me-4"></b-icon></button>
        <span class="fw-bold">회원가입</span>
      </span>
    </div>
    <br>
    <br>
    <div class="user join wrapC app">
      <div class="form-wrap">
        <div class="input-with-label d-flex justify-content-between align-items-center">
          <input  @change="reNickname()" v-model="nickname" id="nickname" class="me-2"
            placeholder="닉네임을 입력하세요." type="text" style="padding-inline-start: 4rem;" />
          <label for="nickname">닉네임</label>
          <button class="subbtn" id="nicknameConfirm" @click="confirmNickname()" style="height: 3rem;">
            중복확인
          </button>
        </div>

        <div class="input-with-label mt-3 d-flex justify-content-between align-items-center">
          <input @change="reEmail()" v-model="email" id="email" class="me-2"
            placeholder="이메일을 입력하세요." type="text" style="padding-inline-start: 4rem;"
            v-bind:class="{error : error.email, complete:!error.email&&email.length!==0}" />
          <label for="email">이메일</label>
          <button class="subbtn" id="emailConfirm"  @click="confirmEmail()" style="height: 3rem;">
            중복확인
          </button>
        </div>
        <div class="error-text" style="color: #EE4B55;" v-if="error.email">{{error.email}}</div>

        <div class="input-with-label mt-3">
          <input v-model="password"
          id="password" 
          :type="passwordType" 
          placeholder="비밀번호를 입력하세요."
          v-bind:class="{error : error.password, complete:!error.password&&password.length!==0}" 
          />
          <label for="password">비밀번호</label>
          <div class="error-text" v-if="error.password">{{error.password}}</div>
        </div>

        <div class="input-with-label mt-3">
          <input
            v-model="passwordConfirm"
            :type="passwordConfirmType"
            id="password-confirm"
            placeholder="비밀번호를 재입력하세요."
            @input="checkPassword()"
          />
          <label for="password-confirm">비밀번호 확인</label>
        </div>
      </div>
      
      <form @submit="checkForm" @submit.prevent="signup">
        <div v-if="activeButton() && isSubmit">
          <button class="btn-bottom" >가입하기</button>
        </div>
        <div v-else>
          <button class="btn-bottom disabled" disabled >가입하기</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import PV from "password-validator";
import * as EmailValidator from "email-validator";
import axios from 'axios'
export default {
  name:'Signup',
  data: () => {
    return {
      errors:[],
      email: "",
      emailConfirm: false,
      password: "",
      passwordConfirm: "",
      sameCheckPassword:false,
      nickname: "",
      nicknameConfirm:false,
      error: {
        email: false,
        password: false,
        nickname: false,
        passwordConfirm: false,
        term: false
      },
      isSubmit: false,
      passwordType: "password",
      passwordConfirmType: "password",
      passwordSchema: new PV(),
      termPopup: false
    };
  },
  created() {
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
  watch: {
    nickname: function(v) {
      const confirm =  document.getElementById("nicknameConfirm")
      confirm.innerText = "중복확인"
      this.nicknameConfirm=false;

    },
    password: function(v) {
      this.checkForm();
    },
    email: function(v) {
      const confirm =  document.getElementById("emailConfirm")
      confirm.innerText = "중복확인"
      this.emailConfirm=false;
      this.checkForm();
    },
  },
  methods:{
    goBack() {
      this.$router.go(-1)
    },
    reEmail(){
      this.emailConfirm = false
    },
    reNickname(){
      this.nicknameConfirm = false
    },
    confirmNickname(){
      const confirm =  document.getElementById("nicknameConfirm")

      axios({
        url:'https://i5b302.p.ssafy.io/api/account/checkNickname',
        method:'get',
        params:{
          nickname:this.nickname,
        }
      })
        .then(res=>{
          if (res.data === 'Fail'){
            alert('중복된 닉네임 입니다!')
            this.nickname=""
            
          }else{
            alert('사용 가능한 닉네임 입니다.')
            this.nicknameConfirm=true
            confirm.innerText = "확인완료"
          }
        })
    },
    confirmEmail(){
      const confirm =  document.getElementById("emailConfirm")
      axios({
        url:'https://i5b302.p.ssafy.io/api/account/checkEmail',
        method:'get',
        params:{
          email: this.email,
        }
      })
        .then(res=>{
          if (res.data === 'Fail'){
            alert('중복된 이메일 입니다!')
            this.email=""
          }else{
            alert('사용 가능한 이메일 입니다.')
            this.emailConfirm=true
            confirm.innerText = "확인완료"
          }

        })
    },
    signup(){
      axios({
        url:"https://i5b302.p.ssafy.io/api/account/signup",
        method:'post',
        data:{
          nickname:this.nickname,
          email: this.email,
          password: this.password,
        }
      })
        .then(res=>{
          this.$router.push({ name:'Login' })
        })
        .catch(err=>{
          alert('가입이 불가능 합니다.')        
        })
    },
    checkPassword: function(){
      if(this.password === this.passwordConfirm){
        this.sameCheckPassword = true
        return true;
      }else
      { this.sameCheckPassword = false 
      return false; }
    },
    activeButton: function(){
      if(this.email && this.password && this.passwordConfirm && this.nickname && this.sameCheckPassword && this.nicknameConfirm && this.emailConfirm){
        return true;
      }else{ return false; }
    },
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
  }
};
</script>
<style src="../../App.css">
</style>


