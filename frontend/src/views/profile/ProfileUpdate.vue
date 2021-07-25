<template>
  <div>
    <!-- 헤더 -->
    <div class="mt-3 mx-4 d-flex justify-content-between align-items-center">
      <span class="fs-1">
        <button><b-icon icon="arrow-left" class="me-4"></b-icon></button>
        <span class="fw-bold">프로필 정보 수정</span>
      </span>
      <button href="#" class="text-decoration-none" @click="updateProfileInfo">저장하기</button>
    </div>

    <!-- 프로필 수정 -->
    <div>
      <!-- 프로필 사진 수정 -->
      <div class="mt-5 mx-3 d-flex justify-content-center">
        <span class="dot"></span>
      </div>
      
      <!-- Username, 본인 소개글 수정 -->
      <div>
        <div class="mt-5 mx-3">
          <!-- Username -->
          <b-form-input placeholder="username" type="text"></b-form-input>
        </div>
        <div class="mt-3 mx-3">
          <!-- 본인 소개글 -->
          <b-form-textarea placeholder="본인 소개글" rows="3" max-rows="6"></b-form-textarea>
        </div>
      </div>

      <!-- 비밀번호 변경 버튼 -->
      <div class="mt-4 mx-3 d-grid">
        <!-- <b-button squared variant="danger">비밀번호 변경</b-button> -->
        <button class="btn btn-danger shadow-none" style="height: 45px;">비밀번호 변경</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'ProfileUpdate',
  setToken: function () {
      const token = localStorage.getItem('jwt')
      const config = {
        Authorization: `JWT ${token}`,
      }
      return config
    },
  data () {
    return{
      userInfo: {
        introduction: '',
        nickname: '',
        uid: '',
      }
    }
  },
  methods: {
    updateProfileInfo: function () {
      axios({
        method: 'patch',
        url: `http://127.0.0.1:8080/account/profile/`,
        data: this.userInfo,
        //headers: this.setToken(),
      })
      .then(() => {
        this.$router.push({name:'ProfileDetail'})
      })
      .catch((err) => {
        alert(err)
      })
    }
  },
  created () {
    axios({
      method: 'get',
      url: `http://127.0.0.1:8080/account/profile/`,
      //headers: this.setToken(),
    })
  }
}
</script>

<style scoped>
/* 프로필 이미지 들어가기 전 디버깅 용 */
.dot {
  height: 200px;
  width: 200px;
  background-color: #bbb;
  border-radius: 50%;
  display: inline-block;
}
</style>