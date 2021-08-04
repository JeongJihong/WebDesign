<template>
  <div class="scale">
    <div class="mt-3 mx-4 fs-1">
      <span class="fw-bold"></span>
    </div>
    <div class="mb-2">
      <!-- <p>생성시간 : {{ article.createdtime }}</p> -->
      <b-avatar src="https://placekitten.com/300/300" size="2rem"></b-avatar><span> {{ article.userNickname }}</span>
      <span>
        <!-- <button @click="articleUpdate()" class="btn-warning badge text-dark fw-bold me-2">수정</button> -->
        <!-- <button v-if=" article.userId === article.articleDetail.id" @click="articleDelete()" class="btn-danger badge">삭제</button> -->
      </span>
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
          <b-carousel-slide v-for ="(idx,image) in article.imageLocation" :key="idx">
            <template #img>
              <img
                class="d-block img-fluid w-100"
                width="1024"
                height="480"
                :src= image
                alt="image slot"
              >
            </template>
          </b-carousel-slide>
        </b-carousel>
    </div>
    <p style="margin:10px">{{ article.articleDetail.review }}</p>
    <p>생성시간: {{ article.articleDetail.createdtime }} </p>
    <p>수정시간: {{ article.articleDetail.updatedtime }} </p>
    <p>{{ article.likeCount }} 명이 좋아해요! <b-avatar src="https://placekitten.com/300/300" size="2rem"></b-avatar><b-button style="height:35px">스크랩하기</b-button></p>
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
    }
  },
  created(){
    axios({
      url:`http://i5b302.p.ssafy.io/api/article/`+this.$route.params.articleid+'/',
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
    goBack() {
      this.$router.go(-1)
    },
    articleDetail(){
      axios({
      url: 'http://i5b302.p.ssafy.io/api/article/'+this.$route.params.articleid,
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
    // articleUpdate(){
    //   axios({
    //   url: 'http://i5b302.p.ssafy.io/api/article/'+this.$route.params.articleid,
    //   method:'put',
    //   headers: {
    //       'x-auth-token': `${localStorage.getItem('token')}`,
    //     }
    //   })
    //   .then(res=>{
    //     this.article= res.data
    //     console.log(res.data)
    //   })
    //   .catch(err=>{
    //     console.log(err)
    //   })
    // },
    articleDelete(){
      axios({
      url: 'http://i5b302.p.ssafy.io/api/article/'+this.$route.params.articleid,
      method:'delete',
      headers: {
          'x-auth-token': `${localStorage.getItem('token')}`,
        }
      })
      .then(res=>{
        console.log(res.data)
        this.$router.push({ name:'FeedMain'})
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