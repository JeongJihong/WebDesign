<template>
  <div>
    <!-- 헤더 -->
    <div class="mt-3 mx-4 fs-1" style="position: relative;">
      <button><b-icon icon="arrow-left" class="me-4"></b-icon></button>
      <span class="fw-bold">알림</span>
    </div>

    <div class="mt-4 pt-4">
      <div v-if="this.click === 'Like'">
        <b-list-group>
          <b-list-group-item
            class="border-0 my-1" v-for="user in likeList" :key="user.senderUid"
            @click="goToArticle(user.detail)">
            <div class="d-flex align-items-center">
              <span>
                <img v-if="user.thumnail" :src="user.thumnail"
                  :alt="user.senderNickname + '님의 프로필'">
              </span>
              <span>{{ user.senderNickname }}님이 좋아요를 누르셨습니다.</span>
            </div>
          </b-list-group-item>
        </b-list-group>
      </div>
      <div v-else-if="this.click === 'Follow'">
        <b-list-group>
          <b-list-group-item
            class="border-0 my-1" v-for="user in followList" :key="user.senderUid"
            @click="goToProfile(user.senderNickname)">
            <div class="d-flex align-items-center">
              <span>
                <img v-if="user.thumnail" :src="user.thumnail"
                  :alt="user.senderNickname + '님의 프로필'">
              </span>
              <span>{{ user.senderNickname }}님의 팔로우 요청이 왔습니다.</span>
            </div>
          </b-list-group-item>
        </b-list-group>
      </div>
      <div v-else>
        <b-list-group>
          <b-list-group-item
            class="border-0 my-1" v-for="user in promiseList" :key="user.senderUid"
            @click="goToPromise(user.detail)">
            <div class="d-flex align-items-center">
              <span>
                <img v-if="user.thumnail" :src="user.thumnail"
                  :alt="user.senderNickname + '님의 프로필'">
              </span>
              <span>{{ user.senderNickname }}님의 약속 초대가 왔습니다.</span>
            </div>
          </b-list-group-item>
        </b-list-group>
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
  methods: {
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
    goToArticle(articleid) {
      this.$router.push({ name: 'ArticleDetail', params: { articleid } })
    },
    goToProfile(nickname) {
      this.$router.push({ name: 'ProfileDetail', params: { nickname } })
    },
    goToPromise(promiseid) {
      this.$router.push({ name: 'PromiseDetail', params: { promiseid }})
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