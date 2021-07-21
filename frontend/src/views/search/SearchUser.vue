<template>
  <div>
    <!-- 헤더 -->
    <div class="mt-3 mx-4 fs-1">
      <button><b-icon icon="arrow-left" class="me-4"></b-icon></button>
      <span class="fw-bold">유저 검색</span>
    </div>

    <!-- 검색 -->
    <div class="mt-4 mx-3">
      <b-form-input v-model="text" placeholder="검색" type="text"></b-form-input>
      <p class="mt-2">InputDebug: {{ text }}</p>
    </div>

    <!-- 검색 결과 -->
    <div>
      <p class="fw-bold mx-3">최근 검색</p>
      <b-list-group>
        <b-list-group-item href="#" class="border-0 my-1"><div class="d-flex align-items-center">
          <span class="dot me-4"></span>
          <span>Username</span>
        </div></b-list-group-item>
        <b-list-group-item href="#" class="border-0 my-1"><div class="d-flex align-items-center">
          <span class="dot me-4"></span>
          <span>Username</span>
        </div></b-list-group-item>
        <b-list-group-item href="#" class="border-0 my-1"><div class="d-flex align-items-center">
          <span class="dot me-4"></span>
          <span>Username</span>
        </div></b-list-group-item>
        <b-list-group-item href="#" class="border-0 my-1"><div class="d-flex align-items-center">
          <span class="dot me-4"></span>
          <span>Username</span>
        </div></b-list-group-item>
      </b-list-group>  
    </div>  
  </div>
</template>

<script>
export default {
  data() {
    return {
      text: ''
    }
  }
}
</script>

<style>
/* 프로필 이미지 들어가기 전 디버깅 용 */
.dot {
  height: 50px;
  width: 50px;
  background-color: #bbb;
  border-radius: 50%;
  display: inline-block;
}
</style>