<template>
  <div style="margin-bottom:80px;">
    <!-- 댓글 입력 -->
    <div class="d-flex justify-content-left align-items-center mx-3 mt-4">
      <span class="d-flex justify-content-between" style="width: 100%;">
        <b-form-input v-model="content" type="text" placeholder="하고싶은말을 적어주세요"
          style="height: 38px;" class="me-3"></b-form-input>
        <button @click="createComment()" class="text-primary">게시</button>
      </span>
    </div>

    <!-- 댓글들 -->
    <b-list-group class="mt-4">
      <b-list-group-item class="d-flex justify-content-left align-items-center border-0 my-1" id="app"
        v-for="(comment, idx) in comments" :key="comment.commentid" :comment="comment">
        <b-avatar v-if="comment.thumbnail" class="me-2"
          :src="getThumbnailImgUrl({ imgURL: comment.thumbnail }).thumbnail"></b-avatar>
        <b-avatar v-else class="me-2"></b-avatar>
        <span  class="d-flex justify-content-between align-items-center" style="width: 100%;">
          <span>{{ comment.comment }}</span>
          <span>
            <button v-if="comment.nickname === username" v-b-modal="idx.toString()" variant="none" class="btn-warning badge text-dark fw-bold me-2">수정</button>
            <b-modal  :id="idx.toString()" title="댓글수정">
                <form @submit.prevent="UpdateComment(comment.commentid)">
                  <input type="text" v-model="newcontent" :placeholder="comment.comment">
                  <button class="btn btn-primary btn-sm">수정하기</button>
                </form>
            </b-modal>
            <button v-if="comment.nickname === username" @click ="CommentDelete(comment.commentid)" class="btn-danger badge">삭제</button>
          </span>
        </span>
      </b-list-group-item>
    </b-list-group>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'Comments',
  data(){
    return {
      comments:[],
      content:"",
      newcontent:"",
      username:"",
    }
  },
  created() {
    axios({
        url:`https://i5b302.p.ssafy.io/api/article/`+this.$route.params.articleid+`/comment`,
        method:'get',
        headers: {
            'x-auth-token': `${localStorage.getItem('token')}`,
        },
      })
        .then(res=>{
          console.log(res.data)
          this.comments = res.data
          console.log(this.comment)
          this.username = `${localStorage.getItem('username')}`
        })
        .catch(err=>{
          console.log(err.response.data)
          console.log("에러임?")
        })
  },
  methods: {
    goBack() {
      this.$router.go(-1)
    },
    getComments: function(){
      axios({
        url:'https://i5b302.p.ssafy.io/api/article/'+this.$route.params.articleid+'/comment',
        method:'get',
        headers: {
          'x-auth-token': `${localStorage.getItem('token')}`,
        },
      })
        .then(res=>{
          this.comments = res.data
          this.username = `${localStorage.getItem('username')}`
          
        })
        .catch(err => {
          console.log(err)
        })
    },
    createComment(){
      axios({
        url:'https://i5b302.p.ssafy.io/api/article/'+this.$route.params.articleid+'/comment',
        method:'post',
        headers: {
          'x-auth-token': `${localStorage.getItem('token')}`,
        },
        params:{
          comment: this.content,
        }
      })
        .then(res=>{
          console.log(res.data)
          this.content = ''
          this.getComments()
        })
        .catch(err=>{
          console.log(err)
        })
    },
    CommentDelete(commentid){
      axios({
        url:`https://i5b302.p.ssafy.io/api/article/comment/`+commentid,
        method:'delete',
        headers: {
          'x-auth-token': `${localStorage.getItem('token')}`,
        }
      })
        .then(res=>{
          console.log(res)
          this.getComments()
        })
        .catch(err=>{
          console.log(err)
        })
    },
    UpdateComment(commentid){
      // const username = jwt_decode(localStorage.getItem('jwt')).username
      // const userid = jwt_decode(localStorage.getItem('jwt')).user_id
      axios({
        url:`https://i5b302.p.ssafy.io/api/article/comment/`+commentid,
        method:'put',
        headers: {
          'x-auth-token': `${localStorage.getItem('token')}`,
        },
        params:{
          comment: this.newcontent,
        }
      })
        .then(res=>{
          console.log(res)
          this.content =''
          this.getComments()
        })
        .catch(err=>{
          console.log(err)
        })
    },
    getThumbnailImgUrl (payload) {
      return {
        ...this.comments,
        thumbnail: this.comments.length && `https://i5b302.p.ssafy.io/img/${payload.imgURL}`
      }
    }
  }
}
</script>

<style src="../../App.css">
</style>