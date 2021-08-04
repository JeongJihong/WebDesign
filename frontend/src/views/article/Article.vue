<template>
  <div class="feed newsfeed">
    <div class="wrapB">
      <h1>뉴스피드</h1>
      <button @click="logout()">로그아웃</button>
      <div v-for="article in articles" :key="article.review" >
        <b-avatar src="https://placekitten.com/300/300" size="2rem"></b-avatar><span> 냥사마</span>
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
          <b-carousel-slide v-for ="image in article.images" :key="image.id">
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
        <p> {{ article.review }} </p>
        <ul class="d-flex justify-content-left" style="padding-left:3px;">
          <li><b-icon icon="hand-thumbs-up" scale="1.5" variant="primary"></b-icon></li>
          <li><b-icon icon="tags-fill" scale="1.5" variant="primary"></b-icon></li>
          <li><b-icon icon="tags" scale="1.5" variant="primary"></b-icon></li>
          <li @click="getComments(article.articleid)"><b-icon icon="chat-dots" scale="1.5" variant="primary"></b-icon></li><span>{{ article.comments.length }}</span>
        </ul>
        <p>? 명의 유저가 이글을 좋아합니다.</p>
        <br>
      </div>
      <infinite-loading @infinite="infiniteHandler"></infinite-loading>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { mapActions,mapState } from 'vuex'
import "../../components/css/feed/feed-item.scss";
import "../../components/css/feed/newsfeed.scss";
import InfiniteLoading from 'vue-infinite-loading';
export default {
  props: ["keyword"],
  components: {
    InfiniteLoading,
  },
  // components: { FeedItem },
  data() {
      return {
        pageNum: 0,
        list: [],
        slide: 0,
        sliding: null,
        tests:4,
        articles:[],
      }
  },
  methods:{
    infiniteHandler($state) {
      console.log('살아있음1');
      axios.get('http://i5b302.p.ssafy.io/api/article/main', {
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
                  const data = res.data.content;
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
    // infiniteHandler($state) { 
    //   axios({
    //     url:c',
    //     method:'get',
    //     params: { page: this.page, }, 
    //   })
    //   .then(({ data }) => { 
    //     if (data.hits.length) { 
    //       this.page += 1; 
    //       this.list.push(...data.hits); 
    //       $state.loaded(); } 
    //     else { $state.complete(); } });
    //     },
        
    // test(){
    //   axios 
    //     .get('http://i5b302.p.ssafy.io/article', 
    //     { lastArticleId: lastArticleId, size: 3, }) 
    //     .then(response => response.data) 
    //     .then(data => { this.articles = (data.data) })
    // },
    
  ...mapActions([
      'logout'
    ]),
    getArticle(articleid){
      // console.log(articleId)
      this.$router.push({ name:'ArticleDetail', params:{ articleid:articleid }})
    },
    getComments(articleid){
      // console.log(articleId)
      this.$router.push({ name:'Comments', params:{ articleid:articleid }})
    },
    onSlideStart(slide) {
        this.sliding = true
    },
    onSlideEnd(slide) {
      this.sliding = false
    },
  },
  // created(){
  //   axios({
  //     url:'http://i5b302.p.ssafy.io/article',
  //     method:'get',
  //     headers: {
  //         'x-auth-token': `${localStorage.getItem('token')}`,
  //       },
  //   })
  //     .then(res=>{
  //       this.articles = (res.data)
  //     })
  //     .catch(err=>{
  //       console.log(err)
  //     })
  // }
};
</script>
<style scoped>
  li {
    margin-right: 12px;
  }
</style>
