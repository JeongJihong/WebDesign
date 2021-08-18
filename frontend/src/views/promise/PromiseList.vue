<template>
  <div class="page" style="margin-bottom:80px;">
    <div class="mt-3 mx-4 d-flex justify-content-between align-items-center">
      <span class="fs-1">
        <button @click="goBack"><b-icon id="icon" icon="arrow-left" class="me-4"></b-icon></button>
        <span class="fw-bold">약속 정보</span>
      </span>
      <button @click="goToPromiseCreate" style="color:#0d6efd;">약속 생성</button>
    </div>

    <ul class="p-0">
      <p class="mt-3 my-0 mx-3 fs-4 fw-bold">Waiting</p>
      <div v-if="waitingPromises.length !== 0" class="mt-2 mx-3 px-3 border rounded bg-light">
        <div v-for="(waitingPromise,idx) in waitingPromises"
          :key="waitingPromise.promiseid" class="my-4 mx-1">
          <router-link class="text-decoration-none"
            :to="{ name: 'PromiseDetail', params: { promiseid: waitingPromise.promiseid } }">
            <li>
              <span class="d-flex justify-content-start align-items-center">
                <span>
                  <img class="me-4"
                    :src="getwaitingPromiseFeeImgUrl({idx, imgURL: waitingPromise.type }).icon"
                    :alt="waitingPromise.type">
                </span>
                <span class="d-flex flex-column" style="color: black;">
                  <span class="me-2" style="font-size: 1.2rem;">{{ waitingPromise.title }}</span>
                  <span style="font-size: 0.9rem;">
                    <span>{{ waitingPromise.promisetime.substr(5,2) }}</span>
                    <span>.</span>
                    <span class="me-2">{{ waitingPromise.promisetime.substr(8,2) }}</span>
                    <span>{{ waitingPromise.promisetime.substr(11,5) }}</span>
                  </span>
                </span>
              </span>
            </li>
          </router-link>
        </div>
      </div>
      <div v-else>
        <p class="mt-2 mx-3 px-3">대기중인 약속이 없습니다.</p>
      </div>
    </ul>

    <div class="d-flex justify-content-center">
      <hr class="style-one" style="width: 90%;">
    </div>

    <ul class="p-0">
      <p class="mt-3 my-0 mx-3 fs-4 fw-bold">Upcoming</p>
      <div v-if="upcomingPromises.length !== 0" class="mt-2 mx-3 px-3 border rounded bg-light">
        <div v-for="(upcomingPromise,idx) in upcomingPromises"
          :key="upcomingPromise.promiseid" class="my-4 mx-1">
          <router-link class="text-decoration-none"
            :to="{ name: 'PromiseDetail', params: { promiseid: upcomingPromise.promiseid } }">
            <li>
              <span class="d-flex justify-content-start align-items-center">
                <span>
                  <img class="me-4"
                    :src="getupcomingPromisesFeeImgUrl({ idx, imgURL: upcomingPromise.type }).icon"
                    :alt="upcomingPromise.type">
                </span>
                <span class="d-flex flex-column" style="color: black;">
                  <span class="me-2" style="font-size: 1.2rem;">{{ upcomingPromise.title }}</span>
                  <span style="font-size: 0.9rem;">
                    <span>{{ upcomingPromise.promisetime.substr(5,2) }}</span>
                    <span>.</span>
                    <span class="me-2">{{ upcomingPromise.promisetime.substr(8,2) }}</span>
                    <span>{{ upcomingPromise.promisetime.substr(11,5) }}</span>
                  </span>
                </span>
              </span>
            </li>
          </router-link>
        </div>
      </div>
      <div v-else>
        <p class="mt-2 mx-3 px-3">대기중인 약속이 없습니다.</p>
      </div>
    </ul>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name:"PromiseList",
  data(){
    return{
    }
  },
  computed: {
    ...mapState([
      'upcomingPromises',
      'waitingPromises'
    ]),
  },
  created(){
    this.$store.dispatch('promiseListGet')
  },
  methods:{
    goBack() {
      this.$router.go(-1)
    },
    goToPromiseCreate() {
      this.$router.push({ name: 'PromiseCreate' })
    },
    getwaitingPromiseFeeImgUrl(payload) {
      return {
        ...this.waitingPromises,
        icon: this.waitingPromises[payload.idx] && `https://i5b302.p.ssafy.io/img/${payload.imgURL}-icon.svg`
      }
    },
    getupcomingPromisesFeeImgUrl(payload) {
      return {
        ...this.upcomingPromises,
        icon: this.upcomingPromises[payload.idx] && `https://i5b302.p.ssafy.io/img/${payload.imgURL}-icon.svg`
      }
    }
  }
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');
li { font-family: 'Jua', sans-serif; }
img {
  width: 50px;
  height: 50px;

}

hr.style-one {
  border: 0;
  height: 0.5px;
  background: #333;
  background-image: linear-gradient(to right, #ccc, #333, #ccc);
}
</style>