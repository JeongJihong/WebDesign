<template>
  <div class="m-4">
    <div class="d-flex my-4" style="justify-content: space-between">
      <div class="d-flex" style="justify-content: space-between">
        <button style="padding-right:20px">
          <b-icon-arrow-left class="h2"></b-icon-arrow-left>
        </button>
        <h2>본인프로필명</h2>
      </div>
      <button>
        프로필 수정
      </button>
    </div>
    <div class="d-flex">
      <img src="@/assets/images/profile_default.png" alt="image" style="width: 70px; height: 70px" >
      <div class="mx-4">
        <h4>이름</h4>
        <div class="d-flex" style="justify-content: space-between">
          <button class="d-flex">
            <h4>팔로우</h4>
            <h4 style="color:blue;">12</h4>
          </button>
          &nbsp;&nbsp;&nbsp;&nbsp;
          <button class="d-flex">
            <h4>팔로워</h4>
            <h4 style="color:blue;">123</h4>
          </button>
        </div>
      </div>
    </div>
    <div class="pt-4">
      <p>본인소개글</p>
      <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Ipsam omnis possimus eveniet alias repudiandae 
        reprehenderit explicabo pariatur! Incidunt error esse ab animi velit, minus itaque rerum laboriosam tenetur modi sit.</p>
    </div>
    <hr>

    <div v-for="image in 9" :key="image" class="square img_1">
    </div>

    <!-- <div class="container"> -->
      <!-- <div class="row">
        <div v-for="slide in 9" :key="slide" class="col-4 my-4">
          <img src="@/assets/images/img-placeholder.png" alt="image" style="width: 100%; height: 130%">
        </div>
      </div> -->
    <!-- </div> -->
  </div>
</template>

<script>
export default {
  
}
</script>

<style>
.square {
    float:left;
    position: relative;
    width: 30%;
    padding-bottom : 30%; /* = width for a 1:1 aspect ratio */
    margin:1.66%;
    background-position:center center;
    background-repeat:no-repeat;
    background-size:cover; /* you change this to "contain" if you don't want the images to be cropped */
}

/* .img_1{background-image:url('img-placeholder.png');} */
.img_1{background-image:url('../../assets/images/img-placeholder.png');}
</style>