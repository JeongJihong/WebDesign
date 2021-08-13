<template>
  <div class="scale">
    <div class="mt-3 mx-4 fs-1">
      <span class="fw-bold"></span>
    </div>
    <div class="mb-2">
      <b-avatar src="https://placekitten.com/300/300" size="2rem"></b-avatar><span> {{ article.userNickname }}</span>
      <span>
        <button v-if=" article.userId === article.articleDetail.id" @click="articleDelete()" class="btn-danger badge">삭제</button>
      </span>
    </div>
    <div  v-if="!article.articleDetail.promiseid"> 
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
          <b-carousel-slide v-for ="(image,idx) in article.articleDetail.images" :key="idx">
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
    <div v-if="article.articleDetail.promiseid">
            <p>약속 인원 : {{ article.promiseDetail.num }} 명</p>
            <p>약속 장소 : {{ article.promiseDetail.place }}</p>
            <p>약속 시간 : {{ article.promiseDetail.promisetime }}</p>
            <p>유형 : {{ article.promiseDetail.type }}</p>
    </div>
    <p style="margin:10px">{{ article.articleDetail.review }}</p>
    <p>생성시간: {{ article.articleDetail.createdtime }} </p>
    <p>수정시간: {{ article.articleDetail.updatedtime }} </p>
    <p>{{ article.likeCount }} 명이 좋아해요! <b-avatar src="https://placekitten.com/300/300" size="2rem"></b-avatar>
    <b-button style="height:35px">스크랩하기</b-button></p>
    <li v-if="article.scrapCheck"><b-icon @click="undoScrap({ articleid: article.articleDetail.articleid })" icon="tags-fill" scale="1.5" variant="primary"></b-icon></li>
      <li v-else><b-icon @click="doScrap({ articleid: article.articleDetail.articleid })" icon="tags" scale="1.5" variant="secondary"></b-icon></li>
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
      url:`https://i5b302.p.ssafy.io/api/article/`+this.$route.params.articleid,
      method:'GET',
      headers: {
            'x-auth-token': `${localStorage.getItem('token')}`,
          },
    })
      .then(res=>{
        this.article= res.data
        console.log(this.article,this.article.articleDetail.id, '왜못읽음?')
        console.log(this.article,this.article.articleDetail, '왜못읽음?', typeof this.article.articleDetail)
      })
      .catch(err=>{
        console.log(err)
        console.log("왜안됨?")
      })
  },
  methods:{
    getArticleFeeImgUrl (payload) {
      return {
        ...this.article,
        icon: this.article && `https://i5b302.p.ssafy.io/img/${payload.imgURL}`
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