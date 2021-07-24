<template>
  <div>
    <h1>
      패스워드 변경페이지
    </h1>
    <div class="form-wrap">
      <div class="input-with-label">
        <input v-model="nickname" id="nickname" placeholder="닉네임을 입력하세요." type="text" />
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
      <div>
        
      </div>
    </div>
  </div>
</template>

<script>
export default {

}
// /account/changePassword
</script>

<style>

</style>