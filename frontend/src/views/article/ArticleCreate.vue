<template>
  <div class="scale">
    <p>게시글 생성하기</p>
    <form enctype = "multipart/form-data" method="post" >
    <!-- <form> -->
      <div class="d-flex flex-row">
        <button style="display:inline-block; margin-right:5%; margin-left:2%" @click.prevent="clickInputTag()" id='addimage'><b-icon-plus class="h1"></b-icon-plus></button>
        <input hidden ref="plus" id="file" type="file"  accept="image/*" @change.prevent="uploadImage($event)" multiple>
        <div id="image_container"></div>
      </div>
      <div>
        <b-textarea v-model="content"  placeholder="Tall textarea" rows="8"></b-textarea>
        <b-button @click="articleCreate()">전송</b-button>
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
        afiles:File,
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
      console.log(event.target.files)
      console.log(event.target.files[0], typeof event.target.files[0])
      this.afiles = event.target.files[0]

      for (var image of event.target.files) {
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
      console.log(this.files, typeof this.files)
    },
    articleCreate(){
      const formData = new FormData();
      formData.append("content", this.content);
      formData.append("files", this.afiles);
      console.log(this.content, this.afiles, typeof this.afiles)
      axios({
        url:'https://i5b302.p.ssafy.io/api/article',
        method:'post',
        headers: {
          'x-auth-token': `${localStorage.getItem('token')}`,
          'Content-Type': 'multipart/form-data'
        },
        data:formData,
        // params: {
        //   content: this.content,
        // //   files: this.files,
        // }
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

<style>
  .scale {
    margin: 5%;
    align-content: center;
    justify-content: center;
  }
</style>
