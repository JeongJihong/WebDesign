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
      <div v-for="waitingPromise in waitingPromises" :key="waitingPromise.promiseid">
        <router-link :to="{ name: 'PromiseDetail', params: {promiseid: waitingPromise.promiseid } }">
          <li><img :src="require(`../../assets/images/${ waitingPromise.type }-icon.svg`)" alt=type> {{ waitingPromise.title }} {{ waitingPromise.promisetime }}</li>
        </router-link>
      </div>
    </ul>
    <hr>
    <ul>
      <h2>다가오는 약속!</h2>
      <br>
      <div v-for="upcomingPromise in upcomingPromises" :key="upcomingPromise.promiseid">
        <router-link :to="{ name: 'PromiseDetail', params: {promiseid:upcomingPromise.promiseid } }">
          <li><img :src="require(`../../assets/images/${ upcomingPromise.type }-icon.svg`)"  alt=type> {{ upcomingPromise.title }} {{ upcomingPromise.promisetime }}</li>
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
          url:'https://i5b302.p.ssafy.io/api/promise',
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
              // upcomings[key].type = upcomings[key].type.toLowerCase();
              this.upcomingPromises.push(upcomings[key])
            }
            for( let key in waitings){
              this.waitingPromises.push(waitings[key])
            }
            console.log(this.upcomingPromises,this.waitingPromises)
          })
          .catch(err=>{
            console.log(err)
          })
  },
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');
li { font-family: 'Jua', sans-serif; }
</style>