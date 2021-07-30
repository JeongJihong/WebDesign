<template>
  <div class="scale">
    <button><b-icon-arrow-left></b-icon-arrow-left></button><span>게시글 생성하기</span>
    <form enctype = "multipart/form-data" method="post" >
      <div class="d-flex flex-row">
        <button style="display:inline-block; margin-right:5%; margin-left:2%" @click.prevent="clickInputTag()" id='addimage'><b-icon-plus class="h1"></b-icon-plus></button>
        <input hidden ref="plus" id="file" type="file"  accept="image/*" @change.prevent="uploadImage($event)" multiple>
        <div id="image_container"></div>
      </div>
      <div>
        <b-textarea v-model="content"  placeholder="Tall textarea" rows="8"></b-textarea>
        <b-button @click="articleCreate()" >전송</b-button>
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
        files:[],
      }
    },
    methods:{
      clickInputTag() {
        this.$refs['plus'].click()
      },
      uploadImage(event) { 
        console.log(event.target.files)
        this.files.push(event.target.files)

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
        axios({
          url:'http://127.0.0.1:8080/article',
          method:'post',
          headers: {
            'x-access-token': `${localStorage.getItem('token')}`,
          },
          params: {
            content: this.content,
            files: this.files,
          }
        })
          .then(res=>{
            this.$router.push({ name:'article'})
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
