<template>
  <div style="margin-bottom:60px;">
    <div class="mt-3 mx-4 d-flex justify-content-between align-items-center">
      <span class="fs-1">
        <button @click="goBack"><b-icon id="icon" icon="arrow-left" class="me-4"></b-icon></button>
        <span class="fw-bold">팔로잉/팔로우</span>
      </span>
    </div>
    <div class="m-4">
      <br><br><br>
      <div>
        <b-tabs content-class="mt-3" fill>
          <b-tab :title="'팔로워 (' + followers + ')'" active>
            <b-list-group>
              <b-list-group-item class="listgroupitem" variant="light" v-for="person in this.followers" :key="person">
                <div class="d-flex" style="align-items:center" @click="goToProfileDetailFromFollower({person})">
                  <b-avatar v-if="followerLs[person-1].thumbnail" class="me-2"
                    :src="getFollowersThumbnailImgUrl({ idx: person-1, imgURL: followerLs[person-1].thumbnail }).thumbnail"></b-avatar>
                  <b-avatar v-else class="me-2"></b-avatar>
                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <p class="m-0">{{ followerLs[person-1].nickname }}</p>
                </div>
              </b-list-group-item>
            </b-list-group>
          </b-tab>
          <b-tab :title="'팔로잉 (' + followings + ')'" active>
            <b-list-group>
              <b-list-group-item class="listgroupitem" variant="light" v-for="person in this.followings" :key="person">
                <div class="d-flex" style="align-items:center" @click="goToProfileDetailFromFollowing({person})">
                  <b-avatar v-if="followingLs[person-1].thumbnail" class="me-2"
                    :src="getFollowingsThumbnailImgUrl({ idx: person-1, imgURL: followingLs[person-1].thumbnail }).thumbnail"></b-avatar>
                  <b-avatar v-else class="me-2"></b-avatar>
                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <p class="m-0">{{ followingLs[person-1].nickname }}</p>
                </div>
              </b-list-group-item>
            </b-list-group>
          </b-tab>
        </b-tabs>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  data () {
    return {
      nickname: this.$route.params.nickname,
      followerLs: '',
      followingLs: '',
      followers: 0,
      followings: 0,
    }
  },
  methods: {
    goBack() {
      this.$router.go(-1)
    },
    followerList () {
      axios({
        method: 'get',
        url: `http://127.0.0.1:8080/account/profile/${this.nickname}/follower`,
        headers: {
          'Content-Type': 'application/json',
          'X-AUTH-TOKEN' : this.$store.state.token
        },
      })
      .then((res) => {
        console.log('팔로워 불러오기')
        console.log(res.data)
        this.followerLs = res.data
        console.log(this.followerLs)
        this.followers = this.followerLs.length

        this.followingList()
      })
      .catch((err) => {
        alert(err)
      })
    },
    followingList () {
      axios({
        method: 'get',
        url: `http://127.0.0.1:8080/account/profile/${this.nickname}/following`,
        headers: {
          'Content-Type': 'application/json',
          'X-AUTH-TOKEN' : this.$store.state.token
        },
      })
      .then((res) => {
        console.log('팔로잉 불러오기')
        console.log(res.data)
        this.followingLs = res.data
        this.followings = this.followingLs.length
      })
      .catch((err) => {
        alert(err)
      })
    },
    goToProfileDetailFromFollower ({person}) {
      this.$router.push({
        name: 'ProfileDetail',
        params: {nickname: this.followerLs[person-1].nickname}
      })
    },
    goToProfileDetailFromFollowing ({person}) {
      this.$router.push({
        name: 'ProfileDetail',
        params: {nickname: this.followingLs[person-1].nickname}
      })
    },
    getFollowersThumbnailImgUrl (payload) {
      return {
        ...this.followerLs,
        thumbnail: this.followerLs[payload.idx].thumbnail && require(`@/assets/images/${payload.imgURL}`)
      }
    },
    getFollowingsThumbnailImgUrl (payload) {
      return {
        ...this.followingLs,
        thumbnail: this.followingLs[payload.idx].thumbnail && require(`@/assets/images/${payload.imgURL}`)
      }
    }
  },
  created () {
    this.followerList()
  }
}
</script>

<style>
.listgroupitem {
  border: 0px solid #ddd;
}
</style>