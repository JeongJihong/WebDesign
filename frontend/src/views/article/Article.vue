<template>
  <div class="feed newsfeed">
    <div class="wrapB">
      <h1>뉴스피드</h1>
      
      <div v-for="article in articles" :key="article.review" style="z-index:-1;">
        <b-avatar src="https://placekitten.com/300/300" size="2rem"></b-avatar><span>{{article.articleDetail.user.nickname}}</span>
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
          <b-carousel-slide v-for ="image in article.articleDetail.images" :key="image.id">
            <template #img>
              <img
                class="d-block img-fluid w-100"
                width="1024"
                height="480"
                :src= image.imgURL
                alt="image slot"
              >
            </template>
          </b-carousel-slide>
        </b-carousel>
        <p> {{ article.articleDetail.review }} </p>
        <ul class="d-flex justify-content-left" style="padding-left:3px;">
          <li v-if="article.likeCheck"><b-icon  @click="articleLike({ articleid: article.articleDetail.articleid, nickname: article.articleDetail.user.nickname })" id="likestat" icon="hand-thumbs-up" scale="1.5" variant="danger"></b-icon></li>
          <li v-else><b-icon @click="articleLike({ articleid: article.articleDetail.articleid, nickname: article.articleDetail.user.nickname })" id="likestat" icon="hand-thumbs-up" scale="1.5" variant="secondary"></b-icon></li>
          
          <li v-if="article.scrapCheck"><b-icon icon="tags-fill" scale="1.5" variant="primary"></b-icon></li>
          <li v-else><b-icon icon="tags" scale="1.5" variant="primary"></b-icon></li>
          <li @click="getComments(article.articleDetail.articleid)"><b-icon icon="chat-dots" scale="1.5" variant="primary"></b-icon></li><span>{{ article.articleDetail.comments.length }}</span>
        </ul>
        <p>{{ article.likeCount }} 명의 유저가 이글을 좋아합니다.</p>
        <br>
      </div>
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
      }
  },
  watch:{
    // likestat: function(v){
    //   let likestat = document.getElementById('likestat')
    //   if(this.article.likeCheck){
    //   likestat.variant = "secondary"
    //   }else{
    //     likestat.variant = "secondary"
    //   }
    // }

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
              $state.complete();
          }else{
              setTimeout(() => {
                  console.log(res.data)
                  const data = res.data.pageList;
                            for(let key in data){
                                this.articles.push(data[key])
                            }
                  this.pageNum++;
                  $state.loaded();
              }, 1000)
          }
      })
      .catch(err => {
          console.log(err)
          alert('에러');
          localStorage.clear();
          this.$store.state.loginState = false;
          this.$store.state.token = null;
          this.$router.push('/');
      })
    },
    articleLike(payload){
      let likestat = document.getElementById('likestat')
      if (likestat.variant == "secondary"){
        axios({
          url:`http://127.0.0.1:8080/article/`+payload.articleid+'/like',
          method:'post',
          headers: {
                'x-auth-token': `${localStorage.getItem('token')}`,
              },
        })
        .then(res=>{
          console.log(res.data)
          console.log("싫어요 -> 좋아요")
          // this.article.likeCheck = false
          likestat.variant = "danger"
          likestat.setAttribute('variant','danger')
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
          // this.article.likeCheck = true
          console.log("좋아요 -> 싫어요")
          likestat.variant = "secondary"
          likestat.setAttribute('variant','secondary')
        })
        .catch(err=>{
          console.log(err)
        })
      }
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
  },
};
//
</script>
<style scoped>
#carousel-1{
  z-index: -1;
}
  li {
    margin-right: 12px;
  }
</style>
