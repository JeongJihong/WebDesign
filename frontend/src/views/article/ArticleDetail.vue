<template>
  <div class="scale">
    <div class="mt-3 mx-4 fs-1">
      <span class="fw-bold"></span>
    </div>
    <div class="mb-2">
      <b-avatar v-if="article.articleDetail" class="me-2"
        :src="getThumbnailImgUrl({ imgURL: article.articleDetail.user.thumbnail }).thumbnail"></b-avatar>
      <b-avatar v-else class="me-2"></b-avatar>
      <span> {{ article.articleDetail.user.nickname }}</span>
      <span>
        <button v-if="article.userId === detail.id" @click="articleDelete()" class="btn-danger badge">삭제</button>
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
    <div v-if="detail.promiseid">
            <p>약속 인원 : {{ article.promiseDetail.num }} 명</p>
            <p>약속 장소 : {{ article.promiseDetail.place }}</p>
            <p>약속 시간 : {{ article.promiseDetail.promisetime }}</p>
            <p>유형 : {{ article.promiseDetail.type }}</p>
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
      url:`http://127.0.0.1:8080/article/`+this.$route.params.articleid,
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
        icon: this.article && require(`@/assets/images/${payload.imgURL}`)
      }
    },
    goBack() {
      this.$router.go(-1)
    },
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
    articleDelete(){
      axios({
      url: 'http://127.0.0.1:8080/article/'+this.$route.params.articleid,
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
        url: `http://127.0.0.1:8080/scrap/${payload.articleid}`,
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
        url: 'http://127.0.0.1:8080/scrap',
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
            url: `http://127.0.0.1:8080/scrap/${scrapid}`,
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
        thumbnail: this.article.articleDetail.user.thumbnail && require(`@/assets/images/${payload.imgURL}`)
      }
    }
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