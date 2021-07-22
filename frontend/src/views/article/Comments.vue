<template>
  <div class="scale">
    <div class="d-flex justify-content-left align-items-center">
      <b-avatar src="https://placekitten.com/300/300" size="2rem"></b-avatar>
      <input type="text" placeholder="하고싶은말을 적어주세요">
    </div>
    <b-list-group>
      <b-list-group-item class="d-flex justify-content-left align-items-center">
        <b-avatar src="https://placekitten.com/300/300" size="2rem"></b-avatar>| Cras justo odio
        <div>
          <br>
          <a href="#" style="color:blue">수정</a> <a href="#" style="color:red">삭제</a>
        </div>
      </b-list-group-item>

      <b-list-group-item class="d-flex justify-content-left align-items-center">
        <b-avatar src="https://placekitten.com/300/300" size="2rem"></b-avatar>| Dapibus ac facilisis in
        <div>
          <br>
          <a href="#" style="color:blue">수정</a> <a href="#" style="color:red">삭제</a>
        </div>
      </b-list-group-item>

      <b-list-group-item class="d-flex justify-content-left align-items-center">
        <b-avatar src="https://placekitten.com/300/300" size="2rem"></b-avatar>| Morbi leo risus
        <div>
          <br>
          <a href="#" style="color:blue">수정</a> <a href="#" style="color:red">삭제</a>
        </div>
      </b-list-group-item>
    </b-list-group>
  </div>
</template>

<script>
export default {

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