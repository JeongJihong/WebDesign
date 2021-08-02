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
    <p style="margin:10px">{{ article }}</p>
    <!-- <p>생성시간: {{ articledetail.article.createdtime }} </p> -->
    <!-- <p>수정시간: {{ articledetail.article.updatedtime }} </p> -->
    <!-- <p>{{ articledetail[0] }}명이 좋아해요! <b-avatar src="https://placekitten.com/300/300" size="2rem"></b-avatar><b-button style="height:35px">스크랩하기</b-button></p> -->
    <hr>
    <Comments/>
  </div>
</template>


<script>
import Comments  from "../../views/article/Comments.vue"
import axios from 'axios'
export default {
  name:'ArticleDetail',
  components: { Comments },
  data() {
    return {
      slide: 0,
      sliding: null,
      tests:4,
      article:[],
      images:[
            {src: "https://picsum.photos/1024/480/?image=52"},
            {src: "https://picsum.photos/1024/480/?image=54"},
            {src: "https://picsum.photos/1024/480/?image=58"},
            {src: "https://picsum.photos/1024/480/?image=55"}]
    }
  },
  created(){
    axios({
      url:`http://127.0.0.1:8080/article/`+this.$route.params.articleid+`/`,
      method:'GET',
      headers: {
            'x-auth-token': `${localStorage.getItem('token')}`,
          },
    })
      .then(res=>{
        this.article= res.data
        console.log(res.data)
      })
      .catch(err=>{
        console.log(err)
        console.log("왜안됨?")
      })
  },
  methods:{
    articleDetail(){
      axios({
      url: 'http://127.0.0.1:8080/article/'+this.$route.params.articleid,
      method:'get',
      })
      .then(res=>{
        this.article= res.data
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