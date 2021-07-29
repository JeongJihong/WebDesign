<template>
  <!-- <div class="scale"> -->
  <div>
     <!-- 헤더 -->
    <div class="mt-3 mx-4 fs-1">
      <button @click="goBack"><b-icon icon="arrow-left" class="me-4"></b-icon></button>
      <span class="fw-bold">댓글</span>
    </div>

    <!-- 댓글 입력 -->
    <div class="d-flex justify-content-left align-items-center mx-3 mt-4">
      <b-avatar src="https://placekitten.com/300/300" size="2.5rem" class="me-3"></b-avatar>
      <span class="d-flex justify-content-between" style="width: 100%;">
        <b-form-input type="text" placeholder="하고싶은말을 적어주세요"
          style="height: 38px;" class="me-3"></b-form-input>
        <button class="text-primary">게시</button>
      </span>
    </div>

    <!-- 댓글들 -->
    <b-list-group class="mt-4">
      <b-list-group-item class="d-flex justify-content-left align-items-center border-0 my-1"
        v-for="idx in 10" :key="idx">
        <b-avatar src="https://placekitten.com/300/300" size="2.5rem" class="me-3"></b-avatar>
        <span class="d-flex justify-content-between" style="width: 100%;">
          <span>Cras justo odio</span>
          <span>
            <button class="btn-warning badge text-dark fw-bold me-2">수정</button>
            <button class="btn-danger badge">삭제</button>
          </span>
        </span>
      </b-list-group-item>
    </b-list-group>
  </div>
</template>

<script>
export default {
  name: 'Comments',
  methods: {
    goBack() {
      this.$router.go(-1)
    }
  }
}
</script>

<style>
.scale {
    margin: 10%;
    margin-top: 4%;
    align-content: center;
    justify-content: center;
  }

</style>