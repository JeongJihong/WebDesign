<template>
  <div class="page" style="margin-bottom:60px;">
    <div class="mt-3 mx-4 d-flex justify-content-between align-items-center">
      <span class="fs-1">
        <button @click="goBack"><b-icon id="icon" icon="arrow-left" class="me-4"></b-icon></button>
        <span class="fw-bold">게시글 정보</span>
      </span>
    </div>
    <div class="mt-3 mx-4 fs-1">
      <span class="fw-bold"></span>
    </div>
    <div class="mb-2">
      <b-avatar v-if="article.articleDetail" class="me-2"
        :src="getThumbnailImgUrl({ imgURL: article.articleDetail.user.thumbnail }).thumbnail"></b-avatar>
      <b-avatar v-else class="me-2"></b-avatar>
      <span> {{ article.articleDetail.user.nickname }}</span>
      <span style = "float: right; ">
        <button v-if="article.userId === detail.id" @click="articleDelete()" class="btn-danger badge" style="margin:5px;">삭제</button>
      </span>
    </div>
    <div  v-if="imgOn"> 
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
          <b-carousel-slide v-for ="(image,idx) in detail.images" :key="idx">
            <template #img>
              <img
                class="d-block img-fluid w-100"
                width="1024"
                height="480"
                :src="getArticleFeeImgUrl({ imgURL: image.imgURL }).icon"
                alt="image slot"
              >
            </template>
          </b-carousel-slide>
        </b-carousel>
    </div>
    <div v-if="article.articleDetail.promiseid" style="positon:relative;">
        <div id="demo">
          <div class="post-it">
            <div class="inner" style="color:black;" >
              Title : {{ article.promiseDetail.title }} <br>
              약속 인원 : {{ article.promiseDetail.num }} 명<br>
              약속 장소 : {{ article.promiseDetail.place }} <br>
              약속 시간 : {{ article.promiseDetail.promisetime.substr(5,2) }}.{{article.promiseDetail.promisetime.substr(8,2)}} {{article.promiseDetail.promisetime.substr(11,5) }} <br>
              유형 : {{ article.promiseDetail.type }} <br>
            </div>
          </div>
        </div>
    </div>
    <p style="margin:10px">{{ article.articleDetail.review }}</p>
    <p>생성시간: {{ article.articleDetail.createdtime }} </p>
    <p>수정시간: {{ article.articleDetail.updatedtime }} </p>
    <p>{{ article.likeCount }} 명이 좋아해요!
    <b-button style="height:35px">스크랩하기</b-button></p>
    <li v-if="article.scrapCheck"><b-icon @click="undoScrap({ articleid: detail.articleid })" icon="tags-fill" scale="1.5" variant="primary"></b-icon></li>
      <li v-else><b-icon @click="doScrap({ articleid: detail.articleid })" icon="tags" scale="1.5" variant="secondary"></b-icon></li>
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
      detail:[],
      imgOn:0,
    }
  },
  created(){
    axios({
      url:`https://i5b302.p.ssafy.io/api/article/`+this.$route.params.articleid,
      method:'GET',
      headers: {
            'x-auth-token': `${localStorage.getItem('token')}`,
          },
    })
      .then(res=>{
        this.article = res.data
        this.detail = res.data.articleDetail
        this.imgOn= res.data.articleDetail.images.length
        console.log(res)
      })
      .catch(err=>{
        console.log(err)
      })
  },
  methods:{
    getArticleFeeImgUrl (payload) {
      return {
        ...this.article,
        icon: this.article && require(`https://i5b302.p.ssafy.io/img/${payload.imgURL}`)
      }
    },
    goBack() {
      this.$router.go(-1)
    },
    articleDetail(){
      axios({
      url: 'https://i5b302.p.ssafy.io/api/article/'+this.$route.params.articleid,
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
    articleDelete(){
      axios({
      url: 'https://i5b302.p.ssafy.io/api/article/'+this.$route.params.articleid,
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
        console.log(this.$route.params.articleid)
      })
    },
    onSlideStart(slide) {
        this.sliding = true
    },
      onSlideEnd(slide) {
        this.sliding = false
    },
    doScrap(payload) {
      axios({
        url: `https://i5b302.p.ssafy.io/api/scrap/${payload.articleid}`,
        method: 'post',
        headers: {
          'x-auth-token': `${localStorage.getItem('token')}`,
        }
      })
        .then(() => {
          this.article.scrapCheck = true
        })
    },
    undoScrap(payload) {
      axios({
        url: 'https://i5b302.p.ssafy.io/api/scrap',
        method: 'get',
        headers: {
          "Content-Type": "application/json",
          'x-auth-token': `${localStorage.getItem('token')}`,
        }
      })
        .then(res => {
          console.log(res)
          console.log(payload.articleid)
          let scrapid = -1
          for (let i = 0; i < res.data.length; i++) {
            if (res.data[i].articleid === payload.articleid) {
              scrapid = res.data[i].scrapid
            }
          }
          return scrapid
        })
        .then(scrapid => {
          axios({
            url: `https://i5b302.p.ssafy.io/api/scrap/${scrapid}`,
            method: "delete",
            headers: {
              "Content-Type": "application/json",
              'x-auth-token': `${localStorage.getItem('token')}`,
            },
          })
            .then(() => {
              this.article.scrapCheck = false
            })
        })
    },
    getThumbnailImgUrl (payload) {
      return {
        ...this.article,
        thumbnail: this.article.articleDetail.user.thumbnail && require(`https://i5b302.p.ssafy.io/img/${payload.imgURL}`)
      }
    }
  },
};
</script>

<style src="../../App.css">
</style>