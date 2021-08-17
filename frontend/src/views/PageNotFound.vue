<template>
<div>
  <div class="container">
    <div class="p-0 mx-3 d-flex justify-content-between align-items-center">
      <span class="mt-5 fs-1">죄송합니다</span>
      <img class="mt-3" src="@/assets/images/main-icon.png" width="100rem">
    </div>
    <p class="mt-4 mx-3">요청하신 페이지를 찾을 수 없습니다 😥</p>
    <p class="mx-3">더 나은 서비스를 위해서,</p>
    <p class="mx-3">누가 잘못했는지 알려주시면</p>
    <p class="mx-3">감사하겠습니다.</p>

    <div class="p-0 mt-4 mx-3">
      <hr class="style-one">
      <div class="mt-4 d-flex justify-content-between align-items-center">
        <div>
          <span class="me-4">김백준</span>
          <span>Backend</span>
        </div>
        <b-icon @click="goToMain" class="me-2" icon="emoji-angry-fill" variant="warning" scale="1.5"></b-icon>
      </div>
      <div class="mt-4 d-flex justify-content-between align-items-center">
        <div>
          <span class="me-4">신형식</span>
          <span>Frontend</span>
        </div>
        <b-icon @click="goToMain" class="me-2" icon="emoji-angry-fill" variant="warning" scale="1.5"></b-icon>
      </div>
      <div class="mt-4 d-flex justify-content-between align-items-center">
        <div>
          <span class="me-4">이두호</span>
          <span>Frontend</span>
        </div>
        <b-icon @click="goToMain" class="me-2" icon="emoji-angry-fill" variant="warning" scale="1.5"></b-icon>
      </div>
      <div class="mt-4 d-flex justify-content-between align-items-center">
        <div>
          <span class="me-4">정종우</span>
          <span>Frontend</span>
        </div>
        <b-icon @click="goToMain" class="me-2" icon="emoji-angry-fill" variant="warning" scale="1.5"></b-icon>
      </div>
      <div class="mt-4 d-flex justify-content-between align-items-center">
        <div>
          <span class="me-4">정지홍</span>
          <span>Backend</span>
        </div>
        <b-icon @click="goToMain" class="me-2" icon="emoji-angry-fill" variant="warning" scale="1.5"></b-icon>
      </div>
    </div>

    <div class="p-0 mt-4 d-flex justify-content-center">
      <button class="btn btn-primary shadow-none d-flex justify-content-center align-items-center"
        style="height: 2.3rem; width: 7rem;" @click="goBack">
        되돌아가기</button>
    </div>
  </div>
</div>
</template>

<script>
export default {
  methods: {
    goToMain() {
      if (localStorage.getItem('token')) {
        this.$router.push({ name: 'FeedMain' })
      } else {
        this.$router.push({ name: 'Login' })
      }
    },
    goBack() {
      this.$router.go(-1)
    }
  }
}
</script>

<style scoped>
hr.style-one {
  border: 0;
  height: 1px;
  background: #333;
  background-image: linear-gradient(to right, #ccc, #333, #ccc);
}
</style>