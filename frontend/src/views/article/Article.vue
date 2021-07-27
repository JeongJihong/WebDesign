<template>
  <div class="feed newsfeed">
    <div class="wrapB">
      <b-button @click="onClick()">피드생성</b-button>
      <h1>뉴스피드</h1>
      <!-- <div v-for="article in articles"  :key="article.id" @click="getArticle(article.id)" :article="article">
        <p>{{article.image}}</p>
        <FeedItem/>
      </div> -->
      <div v-for = " (test,index) in tests" :key="index" >
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
        <p> {{ content[index].text }} </p>
        <ul class="d-flex justify-content-left" style="padding:3px;">
          <li><b-icon icon="hand-thumbs-up" scale="1.5" variant="primary"></b-icon></li>
          <li><b-icon icon="tags-fill" scale="1.5" variant="primary"></b-icon></li>
          <li><b-icon icon="tags" scale="1.5" variant="primary"></b-icon></li>
          <li><b-icon icon="chevron-double-down" scale="1.5" variant="dark"></b-icon></li>
        </ul>
        <br>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import axios from 'axios'
import "../../components/css/feed/feed-item.scss";
import "../../components/css/feed/newsfeed.scss";
import FeedItem from "../../components/feed/FeedItem.vue";
export default {
  props: ["keyword"],
  // components: { FeedItem },
  data() {
      return {
        images:[
            {src: "https://picsum.photos/1024/480/?image=52"},
            {src: "https://picsum.photos/1024/480/?image=54"},
            {src: "https://picsum.photos/1024/480/?image=58"},
            {src: "https://picsum.photos/1024/480/?image=55"}
        ],
        content:[
          {text: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse eros felis, tincidunta tincidunt eget, convallis vel est. Ut pellentesque ut lacus vel interdum."},
          {text: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse eros felis, tincidunta tincidunt eget, convallis vel est. Ut pellentesque ut lacus vel interdum."},
          {text: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse eros felis, tincidunta tincidunt eget, convallis vel est. Ut pellentesque ut lacus vel interdum."},
          {text: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse eros felis, tincidunta tincidunt eget, convallis vel est. Ut pellentesque ut lacus vel interdum."},
        ],
        slide: 0,
        sliding: null,
        tests:4,
      }
  },
  methods:{
    onClick(){
      if (!this.isLogin){
        alert('Login이 필요합니다!')
        this.$router.push({name:'Login'})
      }else{
        this.$router.push({ name:'ArticlCreate'} )
      }
    },
    getArticle(articleId){
      console.log(articleId)
      this.$router.push({ name:'ArticleDetail', params:{ articleId:articleId }})
    },
    onSlideStart(slide) {
        this.sliding = true
    },
      onSlideEnd(slide) {
        this.sliding = false
    },
  },
  created(){
    axios({
      url:'http://127.0.0.1:8080/feed/main',
      method:'get',
    })
      .then(res=>{
        this.articles = (res.data)
        // console.log(this.reviews)

      })
      .catch(err=>{
        console.log(err)
      })
  }
};
</script>
<style scoped>
  li {
    margin-right: 12px;
  }
</style>
