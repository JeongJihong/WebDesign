<template>
  <div style="margin-bottom:60px;">
    <!-- 헤더 -->
    <div class="mt-3 mx-4 d-flex justify-content-between align-items-center">
      <span class="fs-1">
        <button @click="goBack"><b-icon id="icon" icon="arrow-left" class="me-4"></b-icon></button>
        <span class="fw-bold">유저 검색</span>
      </span>
    </div>

    <!-- 검색 : POST search 로직을 생각해보면 검색 버튼 없어도 될 것 같다. -->
    <div class="mt-4 mx-3">
      <b-form-input v-model="nickname" placeholder="검색" type="text"></b-form-input>
    </div>

    <div class="mt-4">
      <!-- 최근 검색 -->
      <div v-if="this.nickname === ''">
        <p class="fw-bold mx-3">최근 검색</p>
        <div v-if="this.searchLive.length !== 0">
          <b-list-group>
            <b-list-group-item
              class="border-0 my-1" v-for="(user, idx) in searchGet" :key="user.searchid">
              <div class="d-flex justify-content-between">
                <!-- <b-link :href="`/#/account/profile/${user.name}`" -->
                <b-link
                  class="text-decoration-none text-dark pe-5 me-5">
                  <span class="d-flex align-items-center" @click="searchPost({token, user})">
                    <b-avatar v-if="user.thumbnail" class="me-2"
                      :src="getThumbnailImgUrl({ idx, imgURL: user.thumbnail }).thumbnail"></b-avatar>
                    <b-avatar v-else class="me-2"></b-avatar>
                    <span>{{ user.name }}</span>
                  </span>
                </b-link>
                <span class="d-flex align-items-center me-2">
                  <b-icon icon="trash" @click="searchDelete({token, user})"></b-icon>
                </span>
              </div>
            </b-list-group-item>
          </b-list-group>  
        </div>
      </div>
      <!-- 검색 결과 -->
      <div v-else>
        <p class="fw-bold mx-3">검색 결과</p>
        <b-list-group>
          <!-- <b-list-group-item :href="`/#/account/profile/${user.name}`" -->
          <b-list-group-item
            class="border-0 my-1" v-for="(user, idx) in searchLive" :key="user.searchid"
            @click="searchPost({token, user})">
            <div class="d-flex align-items-center">
              <b-avatar v-if="user.thumbnail" class="me-2"
                :src="getLiveThumbnailImgUrl({ idx, imgURL: user.thumbnail }).thumbnail"></b-avatar>
              <b-avatar v-else class="me-2"></b-avatar>
              <span>{{ user.name }}</span>
            </div>
          </b-list-group-item>
        </b-list-group>  
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { mapState, mapGetters } from 'vuex'

export default {
  data() {
    return {
      nickname: '',
    }
  },
  created() {
    this.$store.dispatch('searchGet', this.token)
  },
  computed: {
    ...mapState([
      'token',
      'searchLive'
    ]),
    ...mapGetters([
      'searchGet'
    ])
  },
  watch: {
    nickname: function () {
      const data = {
        token: this.token,
        nickname: this.nickname
      }
      this.$store.dispatch('searchLive', data)
    }
  },
  methods: {
    searchPost({ token, user }) {
      axios({
        url: `http://127.0.0.1:8080/account/checkJWT`,
        method: 'get',
        headers: {
          'Content-Type': 'application/json',
          'X-AUTH-TOKEN': token
        }
      })
        .then(res => {
          axios({
            url: 'http://127.0.0.1:8080/search',
            method: 'post',
            data: {
              id: res.data.uid,
              name: user.name
            }
          })
            .then(() => {
              this.$store.dispatch('searchGet', token)
              this.$router.push({ name: 'ProfileDetail', params: { nickname: user.name } })
            })
            .catch(err => {
              alert(err)
            })
        })
        .catch(err => {
          alert('JWT 인증 실패', err)
        })
    },
    searchDelete({ token, user }) {
      axios({
        url: `http://127.0.0.1:8080/account/checkJWT`,
        method: 'get',
        headers: {
          'Content-Type': 'application/json',
          'X-AUTH-TOKEN': token
        }
      })
        .then(() => {
          axios({
            url: `http://127.0.0.1:8080/search`,
            method: 'delete',
            headers: {
              'Content-Type': 'application/json',
              'X-AUTH-TOKEN': token
            },
            params: {
              searchid: user.searchid
            }
          })
            .then(() => {
              this.$store.dispatch('searchGet', token)
            })
            .catch(err => {
              alert(err)
            })
        })
        .catch(err => {
          alert('JWT 인증 실패', err)
        })
    },
    goBack() {
      this.$router.go(-1)
    },
    getThumbnailImgUrl (payload) {
      return {
        ...this.searchGet,
        thumbnail: this.searchGet[payload.idx].thumbnail && require(`@/assets/images/${payload.imgURL}`)
      }
    },
    getLiveThumbnailImgUrl (payload) {
      return {
        ...this.searchGet,
        thumbnail: this.searchGet[payload.idx].thumbnail && require(`@/assets/images/${payload.imgURL}`)
      }
    }
  }
}
</script>

<style scoped>
/* 프로필 이미지 들어가기 전 디버깅 용 */
.dot {
  height: 50px;
  width: 50px;
  background-color: #bbb;
  border-radius: 50%;
  display: inline-block;
}
</style>