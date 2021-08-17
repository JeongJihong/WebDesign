<template>
  <div style="margin-bottom:60px;">
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

    <!-- 피드 or 게시글 상세정보 보기 -->
    <div v-if="scrapList.length !== 0">

      <div class="container mt-4">
        <div class="row align-items-center p-0">

          <div v-for="(scrap, idx) in scrapList" :key="idx" class="col-4 my-2 px-2">
            <div v-if="scrap.thumnailURL" class="border rounded"
              :style="{ overflow: 'hidden', height: width + 'px' }">

              <b-icon v-if="scrapMode" icon="x-circle-fill" variant="warning"
                style="position: absolute; z-index: 100;"
                @click="scrapDelete({ scrapid: scrap.scrapid, token, idx })"></b-icon>

              <img v-if="scrap.thumnailURL" style="width: 100%; height: 100%; object-fit: cover;"
                :src="getArticleFeeImgUrl({ idx, imgURL: scrap.thumnailURL }).thumnail"
                :alt="scrap.articleid+'번 게시글'"
                @click="!scrapMode && goToArticleDetail(scrap.articleid)">

            </div>
            <div v-else :style="{ overflow: 'hidden', height: width + 'px' }">

              <b-icon v-if="scrapMode" icon="x-circle-fill" variant="warning"
                style="position: absolute; z-index: 100;"
                @click="scrapDelete({ scrapid: scrap.scrapid, token, idx })"></b-icon>

              <div class="d-flex align-items-center justify-content-center"
                style="width: 100%; height: 100%;"
                @click="!scrapMode && goToArticleDetail(scrap.articleid)">
                <b-icon icon="calendar2-check-fill" variant="primary" scale="6"></b-icon>
              </div>

            </div>
          </div>

        </div>
      </div>

    </div>
    <div v-else>
      <p class="text-center mt-4 pt-4">스크랩한 게시물이 없습니다.</p>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { mapState, mapActions } from 'vuex'

export default {
  data() {
    return {
      width: (window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth) / 3 * 0.9 
    }
  },
  created() {
    this.$store.state.scrapMode = false
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
    ...mapActions([
      'scrapDeleteMode',
    ]),
    goBack() {
      this.$router.go(-1)
    },
    goToArticleDetail(articleid) {
      console.log(articleid+'클릭!')
      this.$router.push({ name: 'ArticleDetail', params: { articleid: articleid } })
    },
    // 애매한 상태관리여서 직접 작성
    scrapDelete(payload) {
      axios({
        url: `https://i5b302.p.ssafy.io/api/scrap/${payload.scrapid}`,
        method: "delete",
        headers: {
          "Content-Type": "application/json",
          "X-AUTH-TOKEN": payload.token,
        },
      })
        .then(() => {
          this.$store.state.scrapList.splice(payload.idx, 1)
        })
    },
    getArticleFeeImgUrl (payload) {
      return {
        ...this.scrapList,
        thumnail: this.scrapList[payload.idx] && require(`https://i5b302.p.ssafy.io/img/${payload.imgURL}`)
      }
    }
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