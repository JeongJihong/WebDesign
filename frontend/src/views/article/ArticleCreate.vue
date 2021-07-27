<template>
  <div class="scale">
    <button @click="back"><b-icon-arrow-left></b-icon-arrow-left></button><span>게시글 생성하기</span>
    <form enctype = "multipart/form-data">
      <div class="d-flex flex-row">
        <button style="display:inline-block; margin-right:5%; margin-left:2%" @click="clickInputTag()" id='addimage'><b-icon-plus class="h1"></b-icon-plus></button>
        <input hidden ref="plus" id="image" multiple type="file" name="image" accept="image/*" @change="uploadImage($event)">
        <div id="image_container"></div>
      </div>
      <div>
        <b-form-textarea v-model="content" id="textarea-rows" placeholder="Tall textarea" rows="8"></b-form-textarea>
        <b-col lg="4" class="pb-2" style="margin:10px"><b-button @click="ArticleCreate()">create</b-button></b-col>
      </div>
    </form>
  </div>
</template>

<script>
import { mapActions } from 'vuex'
import axios from 'axios'
import jwt_decode from "jwt-decode"
export default {
  data() {
        return {
          mainProps: { blank: true, blankColor: '#777', width: 75, height: 75, class: 'm1' },
          content:'',
          user:jwt_decode(localStorage.getItem('jwt')).user_id,
          image:[],
        }
      },
   props:{
    isLogin:{
      type:Boolean
    }
  },
  methods:{
    ...mapActions([
      "back",
    ]),
    clickInputTag() {
      this.$refs['plus'].click()
    },
    uploadImage(event) { 
      console.log(event)
      var reader = new FileReader(); 
      reader.onload = function(event) 
      { 
        var img = document.createElement("img"); 
        img.setAttribute("src", event.target.result); 
        img.setAttribute("width","25%"); 
        document.querySelector("div#image_container").appendChild(img); 
      }; 
      reader.readAsDataURL(event.target.files[0]); 
    },
    ArticleCreate(){
      const userid = jwt_decode(localStorage.getItem('jwt')).user_id
      axios({
        url:'http://127.0.0.1:8000/article/create/',
        method:'post',
        headers: {
          Authorization: `JWT ${localStorage.getItem('jwt')}`,
        },
        data: {
          content: this.content,
          img: this.image,
          user: userid,
        }
      })
        .then(res=>{
          this.$router.push({ name:'article'})
          console.log(res.data)
          console.log(this.image)
        })
        .catch(err=>{
          console.log(this.image)
          console.log(err)
        })
    },
  }
}
</script>

<style>
  .scale {
    margin: 5%;
    align-content: center;
    justify-content: center;
  }
</style>
