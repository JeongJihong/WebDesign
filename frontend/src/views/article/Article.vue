<template>
  <div class="feed newsfeed app" style="margin-top: 5rem; margin-bottom:80px;">
    <div class="wrapB">
      <div v-for="(article,idx) in articles" :key="idx">
        <div>
          <b-avatar v-if="article.articleDetail.user.thumbnail" class="mx-3"
            :src="getThumbnailImgUrl({ idx, imgURL: article.articleDetail.user.thumbnail }).thumbnail"></b-avatar>
          <b-avatar v-else class="mx-3"></b-avatar>

          <span>{{article.articleDetail.user.nickname}}</span>
          <b-carousel
            id="carousel-1"
            v-model="slide"
            :interval="0"
            indicators
            background="#ababab"
            img-width="1024"
            img-height="480"
            style="text-shadow: 1px 1px 2px #333; position:relative;"
            @sliding-start="onSlideStart"
            @sliding-end="onSlideEnd"
          >
            <b-carousel-slide v-for="(image,idnum) in article.articleDetail.images" :key="idnum"
              > 
              <template #img >
                <img
                  class="d-block img-fluid w-100"
                  width="1024"
                  height="480"
                  :src="getArticleFeeImgUrl({ idx, imgURL: image.imgURL }).icon"
                  alt="image slot"
                >
              </template>
            </b-carousel-slide>
          </b-carousel>

          <div v-if="article.articleDetail.promiseid">
              <div id="demo">
                <div class="post-it">
                  <div class="inner" style="color:black; font-size: 1.1rem;">
                    Title : {{ article.promiseDetail.title }} <br>
                    약속 인원 : {{ article.promiseDetail.num }} 명<br>
                    약속 장소 : {{ article.promiseDetail.place }} <br>
                    약속 시간 : {{ article.promiseDetail.promisetime.substr(5,2) }}.{{article.promiseDetail.promisetime.substr(8,2)}} {{article.promiseDetail.promisetime.substr(11,5) }} <br>
                    유형 : {{ article.promiseDetail.type }} <br>
                  </div>
                </div>
              </div>
          </div>

          <div style="margin: 1.5rem;">
            <p> {{ article.articleDetail.review }}</p>
          </div>

          <div>
            <ul class="ms-3 pt-2 d-flex justify-content-left article" style="padding-left:3px;">
              <li class="me-4" v-if="article.likeCheck" ><b-icon @click="articleLike({ articleid: article.articleDetail.articleid, nickname: article.articleDetail.user.nickname, likeCheck:article.likeCheck, idx:idx  })" icon="hand-thumbs-up" scale="1.5" variant="danger"></b-icon></li>
              <li class="me-4" v-else ><b-icon id="icon" @click="articleLike({ articleid: article.articleDetail.articleid, nickname: article.articleDetail.user.nickname,idx:idx })"  icon="hand-thumbs-up" scale="1.5"></b-icon></li>
              
              <li v-if="article.scrapCheck"><b-icon @click="undoScrap({ articleid: article.articleDetail.articleid, idx: idx })" icon="tags-fill" scale="1.5" ></b-icon></li>
              <li v-else><b-icon id="icon" @click="doScrap({ articleid: article.articleDetail.articleid, idx: idx })" icon="tags" scale="1.5" ></b-icon></li>
              <li @click="getComments(article.articleDetail.articleid)"><b-icon icon="chat-dots-fill" scale="1.5" style="color:#10598;"></b-icon></li><span>{{ article.articleDetail.comments.length }}</span>
            </ul>
          </div>
          <p class="mx-3 mt-2">{{ article.likeCount }} 명의 유저가 이글을 좋아합니다.</p>
        </div>
        <hr>
      </div>
      <br>
      <infinite-loading @infinite="infiniteHandler"></infinite-loading>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import InfiniteLoading from 'vue-infinite-loading';
import { mapState } from 'vuex'
export default {
  components: {
    InfiniteLoading,
  },
  data() {
      return {
        pageNum: 0,
        list: [],
        slide: 0,
        sliding: null,
        tests:4,
        articles:[],
        likeCheck:false,
        imgURL:[],
        place:""
      }
  },
  computed: {
    ...mapState([
      'token',
      'username'
    ])
  },
  methods:{
    infiniteHandler($state) {
      axios.get('https://i5b302.p.ssafy.io/api/article/main', {
          headers: {
            'x-auth-token': `${localStorage.getItem('token')}`,
          },
          params: {
            pageNum: this.pageNum,
          },
        })
        .then(res => {
          if(res.data.totalPages == this.pageNum){
              // console.log($state)
              $state.complete();
          }else{
              setTimeout(() => {
                  // console.log(res.data)
                  const data = res.data.pageList;
                            for(let key in data){
                              if(data[key].articleDetail.promiseid){
                                  if(data[key].promiseDetail.place == ""){
                                    data[key].promiseDetail.place = "화상 모임"
                                  }
                                }
                                this.articles.push(data[key])
                                // console.log(this.articles)
                            }
                  this.pageNum++;
                  $state.loaded();
                  // console.log($state)
              }, 1000)
          }
      })
      .catch(err => {
          if (err.response.status === 401) {
                    localStorage.removeItem('token')
                    localStorage.removeItem('username')
                    this.$router.push({ name: 'Login' })
                  }
      })
    },
    articleLike(payload){
      if (!this.articles[payload.idx].likeCheck){
        axios({
          url:`https://i5b302.p.ssafy.io/api/article/`+payload.articleid+'/like',
          method:'post',
          headers: {
                'x-auth-token': `${localStorage.getItem('token')}`,
              },
        })
        .then(res=>{
          // console.log(res.data)
          this.articles[payload.idx].likeCheck = !this.articles[payload.idx].likeCheck
          this.articles[payload.idx].likeCount +=1
          // console.log("싫어요 -> 좋아요")
        })
        // .catch(err=>{
        //   console.log(err)
        // })

        // Like Alarm POST
        if (payload.nickname !== this.username) {
          axios({
            url: 'https://i5b302.p.ssafy.io/api/alarm',
            method: 'post',
            headers: {
              'x-auth-token': this.token
            },
            data: {
              body: '게시글에 좋아요를 눌렀습니다.',
              category: 'Like',
              detail: payload.articleid,
              receiverNickname: payload.nickname,
              title: '좋아요 알림'
            }
          })
        }
      }
      else{
        axios({
          url:`https://i5b302.p.ssafy.io/api/article/`+payload.articleid+'/like',
          method:'delete',
          headers: {
                'x-auth-token': `${localStorage.getItem('token')}`,
              },
        })
        .then(res=>{
          // console.log(res.data)
          // console.log("좋아요 -> 싫어요")
          this.articles[payload.idx].likeCheck = !this.articles[payload.idx].likeCheck
          this.articles[payload.idx].likeCount -= 1
        })
        // .catch(err=>{
        //   console.log(err)
        // })
      }
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
          this.articles[payload.idx].scrapCheck = true
        })
    },
    undoScrap(payload) {
      axios({
        url: 'https://i5b302.p.ssafy.io/api/scrap',
        method: 'get',
        headers: {
          "Content-Type": "application/json",
          "X-AUTH-TOKEN": this.token,
        }
      })
        .then(res => {
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
              "X-AUTH-TOKEN": this.token,
            },
          })
            .then(() => {
              this.articles[payload.idx].scrapCheck = false
            })
        })
    },
    getArticle(articleid){
      this.$router.push({ name:'ArticleDetail', params:{ articleid:articleid }})
    },
    getComments(articleid){
      this.$router.push({ name:'Comments', params:{ articleid:articleid }})
    },
    onSlideStart(slide) {
        this.sliding = true
    },
    onSlideEnd(slide) {
      this.sliding = false
    },
    getArticleFeeImgUrl (payload) {
      // console.log( this.articles[payload.idx] && require(`https://i5b302.p.ssafy.io/img/${payload.imgURL}`))
      // console.log( this.articles[payload.idx] && `https://i5b302.p.ssafy.io/img/${payload.imgURL}`)
      return {
        ...this.articles,
        icon: this.articles[payload.idx] && `https://i5b302.p.ssafy.io/img/${payload.imgURL}`
      }
    },
    getThumbnailImgUrl (payload) {
      return {
        ...this.articles[payload.idx],
        thumbnail: this.articles[payload.idx].articleDetail.user.thumbnail && `https://i5b302.p.ssafy.io/img/${payload.imgURL}`
      }
    }
  },
};
//
</script>
<style src="../../App.css">
</style>
