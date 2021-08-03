<template>
  <div class="m-4">
    <div class="d-flex">
      <button>
        <h1> ← </h1>
      </button>
      &nbsp;&nbsp;&nbsp;&nbsp;
      <h1>
        팔로잉/팔로우
      </h1>
    </div>
    <br><br><br>
    <div>
      <b-tabs content-class="mt-3" fill>
        <b-tab :title="'팔로워 (' + followers + ')'" active>
          <b-list-group>
            <b-list-group-item class="listgroupitem" variant="light" href="#some-link" v-for="person in this.followers" :key="person">
              <div class="d-flex" style="align-items:center" @click="goToProfileDetailFromFollower({person})">
                <img src="@/assets/images/profile_default.png" alt="image" style="width: 40px; height: 40px" >
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <p>{{ followerLs[person-1].nickname }}</p>
              </div>
            </b-list-group-item>
          </b-list-group>
        </b-tab>
        <b-tab :title="'팔로잉 (' + followings + ')'" active>
          <b-list-group>
            <b-list-group-item class="listgroupitem" variant="light" href="#some-link" v-for="person in this.followings" :key="person">
              <div class="d-flex" style="align-items:center" @click="goToProfileDetailFromFollowing({person})">
                <img src="@/assets/images/profile_default.png" alt="image" style="width: 40px; height: 40px" >
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <p>{{ followingLs[person-1].nickname }}</p>
              </div>
            </b-list-group-item>
          </b-list-group>
        </b-tab>
      </b-tabs>
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