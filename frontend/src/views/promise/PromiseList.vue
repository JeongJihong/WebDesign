<template>
  <div>
    <br>
    <router-link :to="{ name: 'PromiseCreate' }"  class="text-decoration-none me-3 text-dark d-flex justify-content-center">
          <h1>약속 생성하기</h1>
    </router-link>
    <br>
    <ul>
      <h2>대기중인 약속!</h2>
      <br>
      <div v-for="(waitingPromise,idx) in waitingPromises" :key="waitingPromise.promiseid">
        <router-link :to="{ name: 'PromiseDetail', params: {promiseid: waitingPromise.promiseid } }">
          <li><img :src="getwaitingPromiseFeeImgUrl({idx, imgURL: waitingPromise.type }).icon" alt=type> {{ waitingPromise.title }} {{ waitingPromise.promisetime }}</li>
        </router-link>
      </div>
    </ul>
    <hr>
    <ul>
      <h2>다가오는 약속!</h2>
      <br>
      <div v-for="(upcomingPromise,idx) in upcomingPromises" :key="upcomingPromise.promiseid">
        <router-link :to="{ name: 'PromiseDetail', params: {promiseid:upcomingPromise.promiseid } }">
          <li><img :src="getupcomingPromisesFeeImgUrl({ idx, imgURL: upcomingPromise.type }).icon" alt=type> {{ upcomingPromise.title }} {{ upcomingPromise.promisetime }}</li>
        </router-link>
      </div>
    </ul>
  </div>
</template>

<script>
import axios from 'axios'
export default {
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
            console.log(res.data)

            const upcomings = res.data.upcoming
            const waitings = res.data.waiting
            for( let key in upcomings){
              this.upcomingPromises.push(upcomings[key])
            }
            for( let key in waitings){
              waitings[key].type = waitings[key].type.toLowerCase();
              this.waitingPromises.push(waitings[key])
            }
            console.log(this.upcomingPromises,this.waitingPromises)
          })
          .catch(err=>{
            console.log(err)
          })
  },
  methods:{
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
</style>