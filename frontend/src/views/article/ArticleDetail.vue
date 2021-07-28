<template>
  <div class="scale">
    <div class="mb-2">
      <b-avatar src="https://placekitten.com/300/300" size="2rem"></b-avatar><span> 냥사마</span>
    </div>
    <div>
      <b-carousel
          id="carousel-1"
          v-model="slide"
          :interval="0"
          controls
          indicators
          background="#ababab"
          img-width="1024"
          img-height="480"
          style="text-shadow: 1px 1px 2px #333;"
          @sliding-start="onSlideStart"
          @sliding-end="onSlideEnd"
        >
          <b-carousel-slide v-for ="image in images" :key="image.id">
            <template #img>
              <img
                class="d-block img-fluid w-100"
                width="1024"
                height="480"
                :src= image.src
                alt="image slot"
              >
            </template>
          </b-carousel-slide>
        </b-carousel>
    </div>
    <p style="margin:10px">Lorem ipsum dosdlor sit amet consectetur adipisicing elit. Autem, quibusdam eum. Officiis accusantium eum beatae dolore labore pariatur, cupiditate veritatis ipsa. Necessitatibus id laboriosam voluptatibus veritatis delectus, rem error quidem.</p>
    <p>?명이 좋아해요! <b-avatar src="https://placekitten.com/300/300" size="2rem"></b-avatar><b-button style="height:35px">스크랩하기</b-button></p>
    <hr>
    <div class="d-flex justify-content-left align-items-center">
      <b-avatar src="https://placekitten.com/300/300" size="2rem"></b-avatar>
      <input type="text" placeholder="하고싶은말을 적어주세요">
    </div>
    <b-list-group>
      <b-list-group-item class="d-flex justify-content-left align-items-center">
        <b-avatar src="https://placekitten.com/300/300" size="2rem"></b-avatar>| Cras justo odio
      </b-list-group-item>

      <b-list-group-item class="d-flex justify-content-left align-items-center">
        <b-avatar src="https://placekitten.com/300/300" size="2rem"></b-avatar>| Dapibus ac facilisis in
      </b-list-group-item>

      <b-list-group-item class="d-flex justify-content-left align-items-center">
        <b-avatar src="https://placekitten.com/300/300" size="2rem"></b-avatar>| Morbi leo risus
      </b-list-group-item>
    </b-list-group>
  </div>
</template>

<script>
export default {
  // data:{
  //   articleid:'',
  //   images:[],
  //   content:'',
  //   id:''
  // },
  // methods:{
    // ArticleDetail(){
    //   const userid = jwt_decode(localStorage.getItem('jwt')).user_id
    //   axios({
    //     url:`http://127.0.0.1:8000/article/`+this.$route.params.uid,
    //     method:'get',
    //     headers: {
    //       Authorization: `JWT ${localStorage.getItem('jwt')}`,
    //     },
    //   })
    //     .then(res=>{
    //       this.articleDetail= res.data
    //       console.log(res.data)
    //     })
    //     .catch(err=>{
    //       console.log(err)
    //     })
    // },
  // }

}
</script>

Textarea
<style>
  .scale {
    margin: 10%;
    margin-top: 4%;
    align-content: center;
    justify-content: center;
  }
</style>