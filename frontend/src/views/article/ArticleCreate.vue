<template>
  <div class="page" style="margin-bottom:80px;">
    <div class="mt-3 mx-4 d-flex justify-content-between align-items-center">
      <span class="fs-1">
        <button @click="goBack"><b-icon id="icon" icon="arrow-left" class="me-4"></b-icon></button>
        <span class="fw-bold">게시글 생성</span>
      </span>
    </div>
    <br>
    <form enctype = "multipart/form-data" method="post">
      <div class="d-flex flex-row" style="margin:10px height:5rem">
        <button style="display:inline-block; margin-right:5%; margin-left:2%; height:70px" @click.prevent="clickInputTag()" id='addimage'><b-icon-plus-square id="icon" class="h1"></b-icon-plus-square><p id="icon">이미지 업로드</p></button>
        <input hidden ref="plus" id="file" type="file"  accept="image/*" @change.prevent="uploadImage($event)" multiple>
        <div id="image_container"></div>
      </div>
      <div style="margin:10px">
        <b-textarea v-model="content"  placeholder="게시글 내용을 적어주세요!" rows="10"></b-textarea>
        <b-button style="height: 2.7rem; width: 100%; margin-bottom:10px; background-color:#002E4F;"
          class="d-flex justify-content-center align-items-center"
          @click="articleCreate()">
          전송</b-button>
      </div>
    </form>
  </div>
</template>

<script>
  import axios from 'axios'
  export default {
    name:'ArticleCreate',
    data(){
      return{
        content:"",
        afiles:[],
        formData: new FormData()
      }
    },
    methods:{
    goBack() {
      this.$router.go(-1)
    },
    clickInputTag() {
      this.$refs['plus'].click()
    },
    uploadImage(event) { 
      for (var image of event.target.files){
        this.formData.append("files",image)
        var reader = new FileReader(); 
        reader.onload = function(event) 
        { 
          var img = document.createElement("img"); 
          img.setAttribute("src", event.target.result); 
          img.setAttribute("width","25%"); 
          document.querySelector("div#image_container").appendChild(img); 
        }; 
        reader.readAsDataURL(image);
      }
    },
    articleCreate(){
      this.formData.append("content", this.content);
      axios({
        url:'https://i5b302.p.ssafy.io/api/article',
        method:'post',
        headers: {
          'x-auth-token': `${localStorage.getItem('token')}`,
          'Content-Type': 'multipart/form-data'
        },
        data:this.formData,
      })
        .then(res=>{
          this.$router.push({ name:'FeedMain'})
          console.log(res.data)
          console.log(this.files)
        })
        .catch(err=>{
          console.log(`${localStorage.getItem('token')}`)
          console.log(this.files)
          console.log(this.content)
          console.log(err)
        })
      },
    }
  }
</script>

<style src="../../App.css">
</style>
