<template>
  <div style="background-color: rgba(0, 163, 255, 0.76); height:100vh;" >

    <VueSlickCarousel v-bind="settings">
      <div class=" d-flex flex-column" ><img src="../../assets/images/onboarding1.png" alt="1">
      <p class="\- ">일일히 약속을 물어보며 잡나요?</p></div>
      <div class="d-flex flex-column"><img src="../../assets/images/onboarding2.png" alt="2" >
      <p class="\-">손쉽게 약속을 올려서 가고싶은 사람끼리 모여봐요!</p></div>
      <div class="d-flex flex-column"><img src="../../assets/images/onboarding3.png" alt="3">
      <p class="\-"> 만남들을 기록해서 다른사람과 나눠보세요</p></div>
      <div class="d-flex flex-column"><img src="../../assets/images/onboarding4.png" alt="4">
      <p class="\-">지금 바로 약속 잡으로 가죠!</p></div>
    </VueSlickCarousel>

    <div class="d-flex flex-row justify-content-center ">
      <router-link :to="{ name: 'Login' }">
          <div id="btn" class="Rectangle-42">로그인</div>
        </router-link>
      <router-link :to="{ name: 'Signup' }">
          <div id="btn" class="Rectangle-42">회원가입</div>
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
  font-family: Roboto;
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
  margin: 0 11px 0 0;
  padding: 6px 41px;
  border-radius: 5px;
  background-color: #005084;
  color: #fff;
}

</style>