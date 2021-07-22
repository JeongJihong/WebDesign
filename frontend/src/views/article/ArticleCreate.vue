<template>
  <div class="scale">
    <button><b-icon-arrow-left></b-icon-arrow-left></button><span>게시글 생성하기</span>
    <div>
      <button><b-icon-plus></b-icon-plus></button>
      <b-img v-bind="mainProps" rounded alt="Rounded image" style="margin :10px"></b-img>
      <b-img v-bind="mainProps" rounded alt="Rounded image" style="margin :10px"></b-img>
      <b-img v-bind="mainProps" rounded alt="Rounded image" style="margin :10px"></b-img>
    </div>
    <div>
      <b-form-textarea id="textarea-rows" placeholder="Tall textarea" rows="8"></b-form-textarea>
      <b-col lg="4" class="pb-2" style="margin:10px"><b-button>Default Button</b-button></b-col>

    </div>
  </div>
</template>

<script>
export default {
  data() {
        return {
          mainProps: { blank: true, blankColor: '#777', width: 75, height: 75, class: 'm1' }
        }
      }
}
</script>

<style>
  .scale {
    margin: 5%;
    align-content: center;
    justify-content: center;
  }
</style>