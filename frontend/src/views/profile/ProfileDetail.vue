<template>
  <div>
    <div class="d-flex">
      <button>
        뒤로가기버튼
      </button>
      <h1>본인프로필명</h1>
      <button>
        프로필 수정
      </button>
    </div>
    <div class="d-flex">
      <img src="@/assets/images/profile_default.png" alt="image">
      <div class="m-4">
        <h4>이름</h4>
        <div class=d-flex>
          <button class="d-flex">
            <h4>팔로우</h4>
            <h4>12</h4>
          </button>
          <button class="d-flex">
            <h4>팔로워</h4>
            <h4>123</h4>
          </button>
        </div>
      </div>
    </div>
    <div>
      <p>본인소개글</p>
      <p>안녕하세요 저는 싸피 5기 000입니다.</p>
    </div>
    <hr>

    <div class="container">
      <div class="row">
        <div v-for="slide in 9" :key="slide" class="col-4">
          <img src="@/assets/images/img-placeholder.png" alt="image" style="width: 80%; height:90%">
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  
}
</script>

<style>

</style>