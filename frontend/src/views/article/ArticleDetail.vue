<template>
  <div class="scale">
    <div class="mb-2">
      <!-- <p>생성시간 : {{ article.createdtime }}</p> -->
      <b-avatar src="https://placekitten.com/300/300" size="2rem"></b-avatar><span> 냥사마</span>
    </div>
    <div>
      <b-carousel
          id="carousel-1"
          v-model="slide"
          :interval="0"
          controls
          indicators
          background="#ababab"
          img-width="1024"
          img-height="480"
          style="text-shadow: 1px 1px 2px #333;"
          @sliding-start="onSlideStart"
          @sliding-end="onSlideEnd"
        >
          <b-carousel-slide v-for ="image in images" :key="image.id">
            <template #img>
              <img
                class="d-block img-fluid w-100"
                width="1024"
                height="480"
                :src= image.src
                alt="image slot"
              >
            </template>
          </b-carousel-slide>
        </b-carousel>
    </div>
    <p style="margin:10px">{{ articledetail.article.content }}</p>
    <p>생성시간: {{ articledetail.article.createdtime }} </p>
    <p>수정시간: {{ articledetail.article.updatedtime }} </p>
    <p>{{ articledetail.articlelikecount }}명이 좋아해요! <b-avatar src="https://placekitten.com/300/300" size="2rem"></b-avatar><b-button style="height:35px">스크랩하기</b-button></p>
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
import axios from 'axios'
export default {
  name:'ArticleDetail',
  data() {
    return {
      slide: 0,
      sliding: null,
      tests:4,
      articledetail:[],
      images:[
            {src: "https://picsum.photos/1024/480/?image=52"},
            {src: "https://picsum.photos/1024/480/?image=54"},
            {src: "https://picsum.photos/1024/480/?image=58"},
            {src: "https://picsum.photos/1024/480/?image=55"}]
    }
  },
  created(){
    axios({
      url:`http://127.0.0.1:8080/article/`+this.$route.params.articleid,
      method:'get',
    })
      .then(res=>{
        this.articledetail= res.data
        console.log(res.data)
      })
      .catch(err=>{
        console.log(err)
      })
  },
  methods:{
    articleDetail(){
      axios({
      url:`http://127.0.0.1:8080/article/`+this.$route.params.articleid,
      method:'get',
      })
      .then(res=>{
        this.articledetail= res.data
        console.log(res.data)
      })
      .catch(err=>{
        console.log(err)
      })
    },
    onSlideStart(slide) {
        this.sliding = true
    },
      onSlideEnd(slide) {
        this.sliding = false
    },
  },
};
</script>

<style>
  .scale {
    margin: 10%;
    margin-top: 4%;
    align-content: center;
    justify-content: center;
  }
</style>