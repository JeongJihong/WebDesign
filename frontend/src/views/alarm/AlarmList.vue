<template>
  <div style="height:100vh">
    <!-- 헤더 -->
    <div class="mt-3 mx-4 d-flex justify-content-between align-items-center">
      <span class="fs-1">
        <button @click="goBack"><b-icon id="icon" icon="arrow-left" class="me-4"></b-icon></button>
        <span class="fw-bold">알림</span>
      </span>
    </div>

    <div class="mt-4 pt-4">
        <div v-if="this.click === 'Like'">
          <div v-if="this.likeList.length === 0"
            class="d-flex justify-content-center">
          <p>좋아요 알람이 존재하지 않습니다. 😥</p>
        </div>
        <div v-else>
          <b-list-group>
            <b-list-group-item
              class="border-0 my-1" v-for="(user, idx) in likeList" :key="user.detail"
              @click="goToArticle(user.detail)">
              <div class="d-flex align-items-center">
                <span class="me-2">
                  <b-avatar v-if="user.thumbnail" class="me-2"
                    :src="getLikeThumbnailImgUrl({ idx, imgURL: user.thumbnail }).thumbnail"></b-avatar>
                  <b-avatar v-else class="me-2"></b-avatar>
                </span>
                <span>{{ user.senderNickname }}님이 좋아요를 누르셨습니다.</span>
              </div>
            </b-list-group-item>
          </b-list-group>
        </div>
      </div>
      <div v-else-if="this.click === 'Follow'">
        <div v-if="this.followList.length === 0"
          class="d-flex justify-content-center">
          <p>팔로우 요청이 존재하지 않습니다. 😥</p>
        </div>
        <div v-else>
          <b-list-group>
            <b-list-group-item
              class="border-0 my-1" v-for="(user, idx) in followList" :key="user.senderUid"
              @click="goToProfile(user.senderNickname)">
              <div class="d-flex align-items-center">
                <span class="me-2">
                  <b-avatar v-if="user.thumbnail" class="me-2"
                    :src="getFollowThumbnailImgUrl({ idx, imgURL: user.thumbnail }).thumbnail"></b-avatar>
                  <b-avatar v-else class="me-2"></b-avatar>
                </span>
                <span>{{ user.senderNickname }}님의 팔로우 요청이 왔습니다.</span>
              </div>
            </b-list-group-item>
          </b-list-group>
        </div>
      </div>
      <div v-else>
        <div v-if="promiseList.length !== 0 && promiseList.Travel.length === 0
          && promiseList.Restaurant.length === 0
          && promiseList.Study.length === 0
          && promiseList.Art.length === 0
          && promiseList.Game.length === 0
          && promiseList.Exercise.length === 0
          && promiseList.Etc.length === 0"
          class="d-flex justify-content-center">
          <p>약속 요청이 존재하지 않습니다. 😥</p>
        </div>
        <div v-else>
          <div v-for="(category, idx) in promiseList" :key="'category'+idx">
            <div v-if="category.length !== 0">
              <b-list-group>
                <b-list-group-item
                  class="border-0 my-1" v-for="user in category" :key="user.detail"
                  @click="goToPromise(user.detail)">
                  <div class="d-flex align-items-center">
                    <span class="fw-bold fs-2 me-4">{{ idx }}
                      <!-- <b-avatar class="me-2"
                        :src="getPromiseThumbnailImgUrl({ idx }).thumbnail"></b-avatar> -->
                    </span>
                    <span>{{ user.senderNickname }}님의 약속 초대가 왔습니다.</span>
                  </div>
                </b-list-group-item>
              </b-list-group>
            </div>
          </div>
        </div>
      </div>
    </div>


    <!-- 하단 navBar -> button 으로 custom -->
    <div id="custom-button-tab" style="background-color: #FEFEFA;">
      <button id="btn-like" @click="clickLike" class="fw-bold font-monospace"
        style="width: 33.33%; height: 2.5rem; background-color: #93CCEA;">Like</button>
      <button id="btn-follow" @click="clickFollow" class="fw-bold font-monospace"
        style="width: 33.34%; height: 2.5rem;">Follow</button>
      <button id="btn-promise" @click="clickPromise" class="fw-bold font-monospace"
        style="width: 33.33%; height: 2.5rem;">Promise</button>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  data() {
    return {
      click: 'Like'
    }
  },
  computed: {
    ...mapState([
      'token',
      'likeList',
      'followList',
      'promiseList'
    ])
  },
  watch: {
    click: function() {
      if (this.click === 'Like') {
        document.getElementById('btn-like').style.backgroundColor = '#93CCEA'
        document.getElementById('btn-follow').style.backgroundColor = ''
        document.getElementById('btn-promise').style.backgroundColor = ''

        this.$store.dispatch('alarmLikeGet', this.token)
      } else if (this.click === 'Follow') {
        document.getElementById('btn-like').style.backgroundColor = ''
        document.getElementById('btn-follow').style.backgroundColor = '#93CCEA'
        document.getElementById('btn-promise').style.backgroundColor = ''

        this.$store.dispatch('alarmFollowGet', this.token)
      } else if (this.click === 'Promise') {
        document.getElementById('btn-like').style.backgroundColor = ''
        document.getElementById('btn-follow').style.backgroundColor = ''
        document.getElementById('btn-promise').style.backgroundColor = '#93CCEA'

        this.$store.dispatch('alarmPromiseGet', this.token)
      }
    }
  },
  mounted() {
    this.scroll()
  },
  created() {
    this.$store.dispatch('alarmLikeGet', this.token)
  },
  beforeDestroy() {
    this.disableScroll()
  },
  methods: {
    goBack() {
      this.$router.go(-1)
    },
    clickLike() {
      this.click = 'Like'
    },
    clickFollow() {
      this.click = 'Follow'
    },
    clickPromise() {
      this.click = 'Promise'
    },
    scroll() {
      window.onscroll = () => {
        let bottomOfWindow = Math.max(window.pageYOffset, document.documentElement.scrollTop,
          document.body.scrollTop) + window.innerHeight >= document.documentElement.offsetHeight - 50
        if (bottomOfWindow) {
          document.getElementById('custom-button-tab').style.position = 'static'
        } else {
          document.getElementById('custom-button-tab').style.position = 'fixed'
          document.getElementById('custom-button-tab').style.bottom = 0
        }
      }
    },
    disableScroll() {
      window.onscroll = () => {
        // empty
      }
    },
    goToArticle(articleid) {
      this.$router.push({ name: 'ArticleDetail', params: { articleid } })
    },
    goToProfile(nickname) {
      this.$router.push({ name: 'ProfileDetail', params: { nickname } })
    },
    goToPromise(promiseid) {
      this.$router.push({ name: 'PromiseDetail', params: { promiseid }})
    },
    getLikeThumbnailImgUrl (payload) {
      return {
        ...this.likeList[payload.idx],
        thumbnail: this.likeList[payload.idx].thumbnail && `https://i5b302.p.ssafy.io/img/${payload.imgURL}`
      }
    },
    getFollowThumbnailImgUrl (payload) {
      return {
        ...this.followList[payload.idx],
        thumbnail: this.followList[payload.idx].thumbnail && `https://i5b302.p.ssafy.io/img/${payload.imgURL}`
      }
    },
    getPromiseThumbnailImgUrl (payload) {
      const typeList = ['Travel', 'Restaurante', 'study', 'Art', 'game', 'Exercise', 'Etc']
      const hashList = ['d51b70dd', '47b19d87', '611d4729', '8a36a18c', '0dc95fa8', '9324f5ac', '4abb6ca0']

      let type = payload.idx
      if (payload.idx) {
        if (payload.idx.charAt(0) === 'G') {
          const l = payload.idx.length
          type = 'g' + payload.idx.substr(1, l-1)
        } else if (payload.idx.charAt(0) === 'S') {
          const l = payload.idx.length
          type = 's' + payload.idx.substr(1, l)
        }
      }

      const typeIdx = typeList.indexOf(type)
      const typeHash = hashList[typeIdx]
      console.log('TypeIcon', `${payload.idx}-icon.${typeHash}`)
      return {
        thumbnail: payload.idx && `https://i5b302.p.ssafy.io/img/${payload.idx}-icon.${typeHash}.svg`
      }
    }
  }
}
</script>

<style scoped>
#custom-button-tab {
  overflow: hidden;
  position: fixed;
  bottom: 0;
  width: 100%
}

#custom-button-tab button {
  float: left;
  display: block;
}
</style>