
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
        <input v-model="nickName" id="nickname" placeholder="닉네임을 입력하세요." type="text" />
        <label for="nickname">닉네임</label>
      </div>

      <div class="input-with-label">
        <input v-model="email" id="email" placeholder="이메일을 입력하세요." type="text" />
        <label for="email">이메일</label>
      </div>

      <div class="input-with-label">
        <input v-model="password" id="password" :type="passwordType" placeholder="비밀번호를 입력하세요." />
        <label for="password">비밀번호</label>
      </div>

      <div class="input-with-label">
        <input
          v-model="passwordConfirm"
          :type="passwordConfirmType"
          id="password-confirm"
          placeholder="비밀번호를 다시한번 입력하세요."
        />
        <label for="password-confirm">비밀번호 확인</label>
      </div>
    </div>

    <label>
      <input v-model="isTerm" type="checkbox" id="term" />
      <span>약관을 동의합니다.</span>
    </label>

    <span @click="termPopup=true">약관보기</span>
    <form @submit="checkForm" @submit.prevent="join">
      <div v-if="activeButton()">
        <button @click="PopUpEmailModal" class="btn-bottom" >가입하기</button>
      </div>
      <div v-else>
        <button class="btn-bottom disabled" >가입하기</button>
      </div>
    </form>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name:'Signup',
  data: () => {
    return {
      errors:[],
      email: "",
      password: "",
      passwordConfirm: "",
      nickName: "",
      isTerm: false,
      isLoading: false,
      error: {
        email: false,
        password: false,
        nickName: false,
        passwordConfirm: false,
        term: false
      },
      isSubmit: false,
      passwordType: "password",
      passwordConfirmType: "password",
      termPopup: false
    };
  },
  methods:{
    signup(){
      axios({
        url:'http://192.168.43.197:3000/account/signup',
        method:'post',
        data:{
          nickName:this.nickName,
          eamil: this.email,
          password: this.password,
          passwordConfirm: this.passwordConfirm,
        }
      })
        .then(res=>{
          console.log(res)
          this.$router.push({ name:'Login' })
        })
        .catch(err=>{
          console.log(err)
        })
    },
    activeButton: function(){
      if(this.email && this.password && this.passwordConfirm && this.nickName){
        return true;
      }else{ return false; }
    },
    checkForm(e) {
      e.preventDefault();
      this.errors = [];
      if (!this.validateEmail(this.email)) {
        alert("이메일 형식을 확인하세요.");
      }
      if (!this.errors.length) return true;
    },
    validateEmail(email) {
    const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
    },
    PopUpEmailModal: function(){
      alert('회원가입 인증메일이 발송되었습니다.')
    }
  }
};
</script>


