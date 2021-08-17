<template>
  <div style="margin-bottom:60px;">
    <div class="mt-3 mx-4 d-flex justify-content-between align-items-center">
      <span class="fs-1">
        <button @click="goBack"><b-icon id="icon" icon="arrow-left" class="me-4"></b-icon></button>
        <span class="fw-bold">패스워드 변경하기</span>
      </span>
    </div>
    <div class="form-wrap">
      <div class="input-with-label">
        <input name="email" v-model="email" id="email" placeholder="이메일을 입력하세요." type="text" />
        <label for="email">이메일</label>
      </div>

      <div class="input-with-label">
        <input name="oldpassword" v-model="oldpassword" id="oldpassword" placeholder="기존 비밀번호를 입력하세요." :type="passwordType"  />
        <label for="oldpassword">기존 비밀번호</label>
      </div>

      <div class="input-with-label">
        <input name="newpassword" v-model="newpassword" id="newpassword" placeholder="새 비밀번호를 입력하세요." :type="passwordType"  />
        <label for="newpassword">새 비밀번호</label>
        <div class="error-text" v-if="error.newpassword">{{error.newpassword}}</div>
      </div>

      <form @submit.prevent="changepassword" @submit="checkForm">
        <div v-if="activeButton() && !isSubmit">
          <!-- <button @click="PopUpEmailModal" class="btn-bottom" >가입하기</button> -->
          <button class="btn-bottom">변경하기</button>
        </div>
        <div v-else>
          <button class="btn-bottom disabled" >변경하기</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import PV from "password-validator";
import axios from 'axios'
export default {
  name:'ChangePassword',
  data: ()=> {
    return{
      email:"",
      oldpassword:"",
      newpassword:"",
      isSubmit: true,
      error: {
        newpassword: false,
      },
      passwordType: "password",
      passwordSchema: new PV(),
    }
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
    newpassword: function(v) {
      this.checkForm();
    },
  },
  methods:{
    goBack() {
      this.$router.go(-1)
    },
    changepassword(){
        axios({
          url:'https://i5b302.p.ssafy.io/api/account/changePassword',
          method:'put',
          data:{
            email: this.email,
            oldPassword: this.oldpassword,
            newPassword: this.newpassword,
          },
        })
          .then(res=>{
            console.log(res)
            this.$router.push({ name:'Login' })
          })
          .catch(err=>{
            console.log(typeof this.oldpassword)
            console.log(this.oldpassword)
            console.log(this.newpassword)
            console.log(this.email)
            console.log(err)
          })
    },
    checkForm() {
      let isSubmit = true;
      if (
        this.newpassword.length >= 0 &&
        !this.passwordSchema.validate(this.newpassword)
      ){
        this.error.newpassword = "영문,숫자 포함 8 자리이상이어야 합니다.";
        isSubmit = true;
      }
      else{
        this.error.newpassword = false;
        isSubmit = false;
      }
      this.isSubmit = isSubmit;
    },
    activeButton: function(){
      if(this.email && this.newpassword && this.oldpassword){
        return true;
      }else{ return false; }
    }
  }
}
// /account/changePassword
</script>

<style>

</style>