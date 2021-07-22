<template>
  <div class="scale">
    <div class="mb-2">
      <b-avatar src="https://placekitten.com/300/300" size="2rem"></b-avatar><span> 냥사마</span>
    </div>
    <div>
      <b-carousel
        id="carousel-fade"
        style="text-shadow: 0px 0px 2px #000"
        fade
        indicators
      >
        <b-carousel-slide
          caption="First slide"
          img-src="https://picsum.photos/1024/480/?image=10"
        ></b-carousel-slide>
        <b-carousel-slide
          caption="Second Slide"
          img-src="https://picsum.photos/1024/480/?image=12"
        ></b-carousel-slide>
        <b-carousel-slide
          caption="Third Slide"
          img-src="https://picsum.photos/1024/480/?image=22"
        ></b-carousel-slide>
      </b-carousel>
    </div>
    <p style="margin:10px">Lorem ipsum dosdlor sit amet consectetur adipisicing elit. Autem, quibusdam eum. Officiis accusantium eum beatae dolore labore pariatur, cupiditate veritatis ipsa. Necessitatibus id laboriosam voluptatibus veritatis delectus, rem error quidem.</p>
    <p>?명이 좋아해요! <b-avatar src="https://placekitten.com/300/300" size="2rem"></b-avatar><b-button style="height:35px">스크랩하기</b-button></p>
    <hr>
    <div class="d-flex justify-content-left align-items-center">
      <b-avatar src="https://placekitten.com/300/300" size="2rem"></b-avatar>
      <input type="text" placeholder="하고싶은말을 적어주세요">
    </div>
    <b-list-group>
      <b-list-group-item class="d-flex justify-content-left align-items-center">
        <b-avatar src="https://placekitten.com/300/300" size="2rem"></b-avatar>| Cras justo odio
      </b-list-group-item>

      <b-list-group-item class="d-flex justify-content-left align-items-center">
        <b-avatar src="https://placekitten.com/300/300" size="2rem"></b-avatar>| Dapibus ac facilisis in
      </b-list-group-item>

      <b-list-group-item class="d-flex justify-content-left align-items-center">
        <b-avatar src="https://placekitten.com/300/300" size="2rem"></b-avatar>| Morbi leo risus
      </b-list-group-item>
    </b-list-group>
  </div>
</template>

<script>
export default {

}
</script>

Textarea
<style>
  .scale {
    margin: 10%;
    margin-top: 4%;
    align-content: center;
    justify-content: center;
  }
</style>