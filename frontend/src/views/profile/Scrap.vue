<template>
  <div>
    <div class="mt-3 mb-3 mx-4 d-flex justify-content-between align-items-center">
      <span>
        <button @click="goBack"><b-icon icon="arrow-left" class="fs-1 me-4"></b-icon></button>
        <span class="fs-1 fw-bold">스크랩한 게시물</span>
      </span>
      <span>
        <button v-if="!scrapMode" class="text-primary" @click="scrapDeleteMode">수정</button>
        <button v-else class="text-primary" @click="scrapDeleteMode">수정완료</button>
      </span>
    </div>

    <!-- 피드 or 게시글 상세정보 보기 구현 전 디버깅 용 -->
    <div v-for="idx in 9" :key="idx" class="square img_1 delete-btn-wrap">
      <b-icon v-if="scrapMode" icon="x-circle" class="delete-btn" @click="scrapDelete(idx, token)"></b-icon>
      <img src="https://picsum.photos/110" alt="debuging area" style="position: absolute;"
        @click="!scrapMode && goToArticleDetail(idx)">
    </div>
    <!-- 피드 or 게시글 상세정보 보기 구현되면 사용할 내용
         scrap 정보가 없어서 수정 필요!!!
    <div v-for="scrap in scrapList" :key="scrap.id" class="square delete-btn-wrap">
      <b-icon v-if="scrapMode" icon="x-circle" class="delete-btn"
        @click="scrapDelete(scrap.id, token)"></b-icon>
      <img :src="scrap.thumbnail" :alt="scrap.name" style="position: absolute;"
        @click="!scrapMode && goToArticleDetail(scrap.id)">
    </div> -->
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'

export default {
  data() {
    return {
    }
  },
  created() {
    this.$store.dispatch('scrapGet', this.token)
  },
  computed: {
    ...mapState([
      'token',
      'scrapList',
      'scrapMode'
    ])
  },
  methods: {
    goBack() {
      this.$router.go(-1)
    },
    // 해당 스크랩 클릭시 해당 게시글의 상세정보 보기로 이동 -- 임시라 수정 필요!!!
    goToArticleDetail(url) {
      // this.$router.push(`/#/article/${url}`)
      console.log('디버깅용:', url, '번째 클릭')
    },
    ...mapActions([
      'scrapDeleteMode',
      'scrapDelete'
    ])
  }
}
</script>

<style>

.square {
    float:left;
    position: relative;
    width: 30%;
    padding-bottom : 30%; /* = width for a 1:1 aspect ratio */
    margin:1.66%;
    background-position:center center;
    background-repeat:no-repeat;
    background-size:cover; /* you change this to "contain" if you don't want the images to be cropped */
}

/* .img_1{background-image:url('img-placeholder.png');} */
.img_1{background-image:url('../../assets/images/img-placeholder.png');}


.delete-btn-wrap {
  position: relative;
}

.delete-btn-wrap .delete-btn {
  position: absolute;
  top: 4px;
  right: 4px;
  z-index: 100;
}
</style>