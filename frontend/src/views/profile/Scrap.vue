<template>
  <div>
    <div class="d-flex m-4">
      <b-icon-arrow-left class="h2"></b-icon-arrow-left>
      &nbsp;&nbsp;&nbsp;&nbsp;
      <h2>스크랩한 게시물</h2>
    </div>

    <div v-for="image in 9" :key="image" class="square img_1">
    </div>
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