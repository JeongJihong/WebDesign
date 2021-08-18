<template>
  <div style="margin-bottom:80px;">
    <div class="mt-3 mb-3 mx-4 d-flex justify-content-between align-items-center">
      <span>
        <button @click="goBack"><b-icon id="icon" icon="arrow-left" class="fs-1 me-4"></b-icon></button>
        <span class="fs-1 fw-bold">프로필 정보 수정</span>
      </span>
      <span>
        <button v-if="activeButton()" class="text-primary" @click="updateProfileInfo">저장하기</button>
      </span>
    </div>

    <!-- 프로필 수정 -->
    <div>
      <form enctype = "multipart/form-data" method="patch" >
        <!-- 프로필 사진 수정 -->
        <div class="mt-5 mx-3 d-flex justify-content-center">
          <img v-if="this.thumbnail" @click.prevent="clickInputTag()" id='addimage' style="border: 2px solid black"
          :src="getThumbnailImgUrl({ imgURL: this.thumbnail }).thumbnail"
          alt="image" class="dot">
          <b-avatar v-else @click.prevent="clickInputTag()" id='addimage'></b-avatar>
          <input hidden ref="plus" id="file" type="file"  accept="image/*" @change.prevent="uploadImage($event)" multiple>
        </div>
        
        <!-- Username, 본인 소개글 수정 -->
        <hr>
        <div>
          <div class="mt-3 mx-3">
            <!-- Username -->
            <label for="nickname">닉네임</label>
            <div class="d-flex flex-row">
              <b-form-input @change="reNickname()" v-model="userInfo.nickname" id="nickname" placeholder="username" type="text"></b-form-input>
              &nbsp;&nbsp;
              <b-button v-if="!checkNicknameValidation" @click="confirmNickname()" style="height:100%" >중복확인</b-button>
              <b-button v-if="checkNicknameValidation" @click="confirmNickname()" style="height:100%" disabled>중복확인</b-button>
            </div>
          </div>
          <div class="mt-3 mx-3">
            <!-- 본인 소개글 -->
            <label for="introduction">소개글</label>
            <b-form-textarea v-model="userInfo.introduction" id="introduction" placeholder="본인 소개글" rows="3" max-rows="6"></b-form-textarea>
          </div>
        </div>
      </form>

      <!-- 비밀번호 변경 버튼 -->
      <div class="mt-4 mx-3 d-grid" v-if="!isLoginByKakao">
        <!-- <b-button squared variant="danger">비밀번호 변경</b-button> -->
          <button @click="goToChangePassword" class="btn btn-danger shadow-none" style="height: 45px;">비밀번호 변경</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { mapActions, mapState } from 'vuex'

export default {
  name: 'ProfileUpdate',
  data () {
    return{
      userInfo: {
        // uid: '',
        introduction: '',
        nickname: '',
        file: File,
      },
      thumbnail: '',
      myUid:'',
      isLoginByKakao: false,
      nicknameConfirm: true,
      originalNickname: '',
      checkNicknameValidation: true,
    }
  },
  methods: {
    goBack() {
      this.$router.go(-1)
    },
    clickInputTag() {
      this.$refs['plus'].click()
    },
    uploadImage(event) { 
      this.userInfo.file = event.target.files[0]

      var reader = new FileReader();
      reader.onload = function(event) {
        var img = document.querySelector("#addimage")
        img.setAttribute("src", event.target.result)
      }
      reader.readAsDataURL(event.target.files[0])
    },
    updateProfileInfo: function () {
      const formData = new FormData();
      formData.append("nickname", this.userInfo.nickname);
      formData.append("introduction", this.userInfo.introduction);
      formData.append("thumbnail", this.userInfo.file);
      axios({
        method: 'post',
        url: 'https://i5b302.p.ssafy.io/api/account/profile/',
        data: formData,
        headers: {
          'Content-Type': 'multipart/form-data',
          'X-AUTH-TOKEN' : this.$store.state.token
        },
      })
      .then((res) => {
        this.$router.push({
          name:'ProfileDetail',
          params: {nickname: this.userInfo.nickname}
        })
      })
    },
    getUserInfo: function () {
      console.log('getUserInfo에 있는 닉네임',this.userInfo.nickname)
      axios({
        method: 'get',
        url: `https://i5b302.p.ssafy.io/api/account/profile/${this.userInfo.nickname}`,
        headers: {
          'Content-Type': 'application/json',
          'X-AUTH-TOKEN' : this.$store.state.token
        },
      })
      .then((res) => {
        this.userInfo.uid = res.data.userProfile.uid
        this.userInfo.nickname = res.data.userProfile.nickname
        this.userInfo.introduction = res.data.userProfile.introduction
        this.thumbnail = res.data.userProfile.thumbnail
        if (typeof this.thumbnail === 'undefined') {
          this.thumbnail = 'profile_default.png'
        }
      })
    },
    goToChangePassword () {
      this.$router.push({
        name: 'ChangePassword',
      })
    },
    getThumbnailImgUrl (payload) {
      return {
        ...this.thumbnail,
        thumbnail: this.thumbnail && `https://i5b302.p.ssafy.io/img/${payload.imgURL}`
      }
    },
    reNickname(){
      this.nicknameConfirm = false
      this.checkNicknameValidation = false
      if (this.userInfo.nickname === this.originalNickname) {
        this.nicknameConfirm = true
        this.checkNicknameValidation = true
      }
    },
    confirmNickname(){
      axios({
        url:'https://i5b302.p.ssafy.io/api/account/checkNickname',
        method:'get',
        headers: {
          'Content-Type': 'application/json',
          'X-AUTH-TOKEN' : this.$store.state.token
        },
        params:{
          nickname: this.userInfo.nickname,
        }
      })
        .then(res=>{
          if (res.data === 'Fail'){
            alert('중복된 닉네임 입니다!')
          }else{
            alert('사용 가능한 닉네임 입니다.')
            this.nicknameConfirm=true
          }
        })
    },
    activeButton: function(){
      if(this.userInfo.nickname && this.nicknameConfirm){
        return true;
      }else{ return false; }
    },
  },
  watch : {
    checkNicknameValidation: function () {
      if (this.userInfo.nickname === this.originalNickname) {
        return true;
      }
      else if (this.nicknameConfirm) {
        return true;
      }
      else {
        return false;
      }
    }
  },
  created () {
    axios({
      method: 'get',
      url: `https://i5b302.p.ssafy.io/api/account/checkJWT/`,
      headers: {
        'Content-Type': 'application/json',
        'X-AUTH-TOKEN' : this.$store.state.token
      },
    })
    .then((res) => {
      this.userInfo.nickname = this.$store.state.username
      this.originalNickname = this.$store.state.username
      this.isLoginByKakao = this.$store.state.isLoginByKakao
      this.getUserInfo()
    })
  }
}
</script>

<style>
/* 프로필 이미지 들어가기 전 디버깅 용 */
.dot {
  height: 200px;
  width: 200px;
  background-color: #bbb;
  border-radius: 50%;
  display: inline-block;
}
</style>