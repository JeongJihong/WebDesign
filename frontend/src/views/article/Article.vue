<template>
  <div class="feed newsfeed">
    <div class="wrapB">
      <h1>뉴스피드</h1>
      
      <div v-for="(article,idx) in articles" :key="idx" style="z-index:-1;">
        <div>
          <b-avatar v-if="article.articleDetail.user.thumbnail" class="me-2"
            :src="getThumbnailImgUrl({ idx, imgURL: article.articleDetail.user.thumbnail }).thumbnail"></b-avatar>
          <b-avatar v-else class="me-2"></b-avatar>
          <span>{{article.articleDetail.user.nickname}}</span>
          <b-carousel
            id="carousel-1"
            v-model="slide"
            :interval="0"
            controls
            indicators
            background="#ababab"
            img-width="1024"
            img-height="480"
            style="text-shadow: 1px 1px 2px #333; z-index: -2;"
            @sliding-start="onSlideStart"
            @sliding-end="onSlideEnd"
          >
            <b-carousel-slide v-for="(image,idnum) in article.articleDetail.images" :key="idnum"
              style="z-index: -1;"> 
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
          <p> {{ article.articleDetail.review }}</p>
          <div v-if="article.articleDetail.promiseid">
            <p>약속 인원 : {{ article.promiseDetail.num }} 명</p>
            <p>약속 장소 : {{ article.promiseDetail.place }}</p>
            <p>약속 시간 : {{ article.promiseDetail.promisetime }}</p>
            <!-- <p>유형 : {{ article.promiseDetail.type }}</p> -->
          </div>
          <ul class="d-flex justify-content-left" style="padding-left:3px;">
            <li v-if="article.likeCheck" ><b-icon  @click="articleLike({ articleid: article.articleDetail.articleid, nickname: article.articleDetail.user.nickname, likeCheck:article.likeCheck, idx:idx  })" id="likestat" icon="hand-thumbs-up" scale="1.5" variant="danger"></b-icon></li>
            <li v-else ><b-icon  @click="articleLike({ articleid: article.articleDetail.articleid, nickname: article.articleDetail.user.nickname,idx:idx })" id="likestat" icon="hand-thumbs-up" scale="1.5" variant="secondary"></b-icon></li>
            
            <li v-if="article.scrapCheck"><b-icon @click="undoScrap({ articleid: article.articleDetail.articleid, idx: idx })" icon="tags-fill" scale="1.5" variant="primary"></b-icon></li>
            <li v-else><b-icon @click="doScrap({ articleid: article.articleDetail.articleid, idx: idx })" icon="tags" scale="1.5" variant="secondary"></b-icon></li>
            <li @click="getComments(article.articleDetail.articleid)"><b-icon icon="chat-dots" scale="1.5" variant="primary"></b-icon></li><span>{{ article.articleDetail.comments.length }}</span>
          </ul>
          <p>{{ article.likeCount }} 명의 유저가 이글을 좋아합니다.</p>
        </div>
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
      axios.get('http://127.0.0.1:8080/article/main', {
          headers: {
            'x-auth-token': `${localStorage.getItem('token')}`,
          },
          params: {
            pageNum: this.pageNum,
          },
        })
        .then(res => {
          if(res.data.totalPages == this.pageNum){
              console.log($state)
              $state.complete();
          }else{
              setTimeout(() => {
                  console.log(res.data)
                  const data = res.data.pageList;
                            for(let key in data){
                                
                                this.articles.push(data[key])
                                console.log(this.articles)
                            }
                  this.pageNum++;
                  $state.loaded();
                  console.log($state)
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
          url:`http://127.0.0.1:8080/article/`+payload.articleid+'/like',
          method:'post',
          headers: {
                'x-auth-token': `${localStorage.getItem('token')}`,
              },
        })
        .then(res=>{
          console.log(res.data)
          this.articles[payload.idx].likeCheck = !this.articles[payload.idx].likeCheck
          this.articles[payload.idx].likeCount +=1
          console.log("싫어요 -> 좋아요")
        })
        .catch(err=>{
          console.log(err)
        })

        // Like Alarm POST
        if (payload.nickname !== this.username) {
          axios({
            url: 'http://127.0.0.1:8080/alarm',
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
          url:`http://127.0.0.1:8080/article/`+payload.articleid+'/like',
          method:'delete',
          headers: {
                'x-auth-token': `${localStorage.getItem('token')}`,
              },
        })
        .then(res=>{
          console.log(res.data)
          console.log("좋아요 -> 싫어요")
          this.articles[payload.idx].likeCheck = !this.articles[payload.idx].likeCheck
          this.articles[payload.idx].likeCount -= 1
        })
        .catch(err=>{
          console.log(err)
        })
      }
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
          this.articles[payload.idx].scrapCheck = true
        })
    },
    undoScrap(payload) {
      axios({
        url: 'http://127.0.0.1:8080/scrap',
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
            url: `http://127.0.0.1:8080/scrap/${scrapid}`,
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
      console.log( this.articles[payload.idx] && require(`@/assets/images/${payload.imgURL}`))
      return {
        ...this.articles,
        icon: this.articles[payload.idx] && require(`@/assets/images/${payload.imgURL}`)
      }
    },
    getThumbnailImgUrl (payload) {
      return {
        ...this.articles[payload.idx],
        thumbnail: this.articles[payload.idx].articleDetail.user.thumbnail && require(`@/assets/images/${payload.imgURL}`)
      }
    }
  },
};
//
</script>
<style scoped>
li {
  margin-right: 12px;
}


</style>
