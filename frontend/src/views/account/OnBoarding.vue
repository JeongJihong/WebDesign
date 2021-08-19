<template>
  <div style="background-color: rgba(0, 163, 255, 0.76); height:100vh;" >

    <VueSlickCarousel v-bind="settings">
      <div class=" d-flex flex-column" style="position:relative;"><img src="../../assets/images/onboarding1.png" alt="1">
      
      </div>
      <div class="d-flex flex-column"><img src="../../assets/images/onboarding2.png" alt="2" ></div>
      <div class="d-flex flex-column"><img src="../../assets/images/onboarding3.png" alt="3"></div>
      <div class="d-flex flex-column"><img src="../../assets/images/onboarding4.png" alt="4"></div>
    </VueSlickCarousel>

    <div class="d-flex" style= "position: fixed; bottom: 10%; left:50%; transform:translateX(-50%);">
      <router-link :to="{ name: 'Login' }">
          <div id="btn" class="Rectangle-42 d-flex justify-content-center align-items-center">로그인</div>
        </router-link>
      <router-link :to="{ name: 'Signup' }">
          <div id="btn" class="Rectangle-42 d-flex justify-content-center align-items-center">회원가입</div>
        </router-link>
    </div>
  </div>
</template>

<script>
import VueSlickCarousel from 'vue-slick-carousel'
import 'vue-slick-carousel/dist/vue-slick-carousel.css'
import "../../components/css/user.scss";
export default {
  name:'OnBoarding',
  components: {
    VueSlickCarousel,
  },
  data() {
      return {
        settings: {
          arrows: false,
          dots: false,
          infinite: false,
        },
      }
    },

}
</script>

<style scoped>
p {
  height: 69px;
  margin: 37px 0 37px 0px;
  font-size: 22px;
  font-weight: bold;
  font-stretch: normal;
  font-style: normal;
  line-height: normal;
  letter-spacing: normal;
  text-align: center;
  color: #fff;
  display: inline;
}
#btn {
  width: 138px;
  height: 35px;
  flex-grow: 0;
  margin: 0 11px 0 11px;
  padding: 6px 41px;
  border-radius: 5px;
  background-color: #005084;
  color: #fff;
}

</style>