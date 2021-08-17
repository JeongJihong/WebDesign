<template>
  <div class="page" style="margin-bottom:60px;">
    <div class="mt-3 mx-4 d-flex justify-content-between align-items-center">
      <span class="fs-1">
        <button @click="goBack"><b-icon icon="arrow-left" class="me-4"></b-icon></button>
        <span class="fw-bold">약속 정보</span>
      </span>
      <button @click="goToPromiseCreate" style="color: #0d6efd;">약속 생성</button>
    </div>

    <ul class="p-0">
      <p class="mt-3 my-0 mx-3 fs-4 fw-bold">Waiting</p>
      <div class="mt-2 mx-3 px-3 border rounded bg-light">
        <div v-for="(waitingPromise,idx) in waitingPromises"
          :key="waitingPromise.promiseid" class="my-4 mx-1">
          <!-- <router-link class="text-decoration-none"
            :to="{ name: 'PromiseDetail', params: {promiseid: waitingPromise.promiseid } }"> -->
          <div
            @click="goToPromiseDetail({ place: waitingPromise, promiseid: waitingPromise.promiseid })"
            class="text-decoration-none">
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
          </div>
          <!-- </router-link> -->
        </div>
      </div>
    </ul>

    <div class="d-flex justify-content-center">
      <hr class="style-one" style="width: 90%;">
    </div>

    <ul class="p-0">
      <p class="mt-3 my-0 mx-3 fs-4 fw-bold">Upcoming</p>
      <div class="mt-2 mx-3 px-3 border rounded bg-light">
        <div v-for="(upcomingPromise,idx) in upcomingPromises"
          :key="upcomingPromise.promiseid" class="my-4 mx-1">
          <!-- <router-link class="text-decoration-none"
            :to="{ name: 'PromiseDetail', params: {promiseid:upcomingPromise.promiseid } }"> -->
          <div
            @click="goToPromiseDetail({ place: upcomingPromise, promiseid: upcomingPromise.promiseid })"
            class="text-decoration-none">
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
          </div>
          <!-- </router-link> -->
        </div>
      </div>
    </ul>
  </div>
</template>

<script>
import axios from 'axios'
import PromiseDetailWithoutMapVue from './PromiseDetailWithoutMap.vue'
export default {
  name:"PromiseList",
  data(){
    return{
      upcomingPromises:[],
      waitingPromises:[]
    }
  },
  created(){
    axios({
        url:'http://127.0.0.1:8080/promise',
        method:'get',
        headers: {
          'x-auth-token': `${localStorage.getItem('token')}`,
        },
      })
        .then(res=>{
          // console.log(res.data)

          const upcomings = res.data.upcoming
          const waitings = res.data.waiting
          for( let key in upcomings){
            this.upcomingPromises.push(upcomings[key])
          }
          for( let key in waitings){
            waitings[key].type = waitings[key].type.toLowerCase();
            this.waitingPromises.push(waitings[key])
          }
          // console.log(this.upcomingPromises,this.waitingPromises)
        })
  },
  methods:{
    goBack() {
      this.$router.go(-1)
    },
    gotoPromiseDetail(payload) {
      if (payload.place) {
        this.$router.push({ name: 'PromiseDetailWithoutMap', params: { promiseid: payload.promiseid } })
      } else {
        this.$router.push({ name: 'PromiseDetail', params: { promiseid: payload.promiseid } })
      }
    },
    goToPromiseCreate() {
      this.$router.push({ name: 'PromiseCreate' })
    },
    getwaitingPromiseFeeImgUrl(payload) {

      const firstChar = payload.imgURL[0];
      const firstCharUpper = firstChar.toUpperCase();
      const leftChar = payload.imgURL.slice(1, payload.imgURL.length); 
      payload.imgURL = firstCharUpper + leftChar; 
      
      return {
        ...this.waitingPromises,
        icon: this.waitingPromises[payload.idx] && require(`@/assets/images/${payload.imgURL}-icon.svg`)
      }
    },
    getupcomingPromisesFeeImgUrl(payload) {
      const firstChar = payload.imgURL[0];
      const firstCharUpper = firstChar.toUpperCase();
      const leftChar = payload.imgURL.slice(1, payload.imgURL.length); 
      payload.imgURL = firstCharUpper + leftChar; 
      
      return {
        ...this.upcomingPromises,
        icon: this.upcomingPromises[payload.idx] && require(`@/assets/images/${payload.imgURL}-icon.svg`)
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