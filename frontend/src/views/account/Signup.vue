
<!--
    가입하기는 기본적인 폼만 제공됩니다
    기능명세에 따라 개발을 진행하세요.
    Sub PJT I에서는 UX, 디자인 등을 포함하여 백엔드를 제외하여 개발합니다.
 -->
<template>
  <div class="user join wrapC">
    <h1>가입하기</h1>
    <div class="form-wrap">
      <div class="input-with-label">
        <input  @change="reNickname()" v-model="nickname" id="nickname" placeholder="닉네임을 입력하세요." type="text" />
        <label for="nickname">닉네임</label>
        <b-button @click="confirmNickname()">중복확인</b-button>
      </div>

      <div class="input-with-label">
        <input @change="reEmail()" v-model="email" id="email" placeholder="이메일을 입력하세요." type="text" 
        v-bind:class="{error : error.email, complete:!error.email&&email.length!==0}"
        />
        <label for="email">이메일</label>
        <b-button @click="confirmEmail()">중복확인</b-button>
        <div class="error-text" v-if="error.email">{{error.email}}</div>
      </div>

      <div class="input-with-label">
        <input v-model="password" 
        id="password" 
        :type="passwordType" 
        placeholder="비밀번호를 입력하세요."
        v-bind:class="{error : error.password, complete:!error.password&&password.length!==0}" 
        />
        <label for="password">비밀번호</label>
        <div class="error-text" v-if="error.password">{{error.password}}</div>
      </div>

      <div class="input-with-label">
        <input
          v-model="passwordConfirm"
          :type="passwordConfirmType"
          id="password-confirm"
          placeholder="비밀번호를 다시한번 입력하세요."
          @input="checkPassword()"
        />
        <label for="password-confirm">비밀번호 확인</label>
      </div>
    </div>
    
    <form @submit="checkForm" @submit.prevent="signup">
      <div v-if="activeButton() && isSubmit">
        <!-- <button @click="PopUpEmailModal" class="btn-bottom" >가입하기</button> -->
        <button class="btn-bottom" >가입하기</button>
      </div>
      <div v-else>
        <button class="btn-bottom disabled" disabled >가입하기</button>
      </div>
    </form>
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

      isTerm: false,
      isLoading: false,
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
      this.nicknameConfirm=false;
    },
    password: function(v) {
      this.checkForm();
    },
    email: function(v) {
      this.emailConfirm=false;
      this.checkForm();
    },
  },
  methods:{
    reEmail(){
      this.emailConfirm = false
    },
    reNickname(){
      this.nicknameConfirm = false
    },
    confirmNickname(){
      axios({
        url:'http://127.0.0.1:8080/account/checkNickname',
        method:'get',
        params:{
          nickname:this.nickname,
        }
      })
        .then(res=>{
          console.log(res)
          if (res.data === 'Fail'){
            alert('중복된 닉네임 입니다!')
            this.nickname=""
            
          }else{
            alert('사용 가능한 닉네임 입니다.')
            this.nicknameConfirm=true
          }
        })
        .catch(err=>{
          console.log(err)
        })
    },
    confirmEmail(){
      axios({
        url:'http://127.0.0.1:8080/account/checkEmail',
        method:'get',
        params:{
          email: this.email,
        }
      })
        .then(res=>{
          console.log(res)
          if (res.data === 'Fail'){
            alert('중복된 이메일 입니다!')
            this.email=""
          }else{
            alert('사용 가능한 이메일 입니다.')
            this.emailConfirm=true
          }

        })
        .catch(err=>{
          console.log(err)
        })
    },
    signup(){
      axios({
        url:"http://127.0.0.1:8080/account/signup",
        method:'post',
        data:{
          nickname:this.nickname,
          email: this.email,
          password: this.password,
        }
      })
        .then(res=>{
          console.log(res)
          this.$router.push({ name:'Login' })
        })
        .catch(err=>{
          console.log(err)
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


