<template>
  <!-- <div class="scale"> -->
  <div>
     <!-- 헤더 -->
    <div class="mt-3 mx-4 fs-1">
      <button @click="goBack"><b-icon icon="arrow-left" class="me-4"></b-icon></button>
      <span class="fw-bold">댓글</span>
    </div>

    <!-- 댓글 입력 -->
    <div class="d-flex justify-content-left align-items-center mx-3 mt-4">
      <b-avatar src="https://placekitten.com/300/300" size="2.5rem" class="me-3"></b-avatar>
      <span class="d-flex justify-content-between" style="width: 100%;">
        <b-form-input v-model="content" type="text" placeholder="하고싶은말을 적어주세요"
          style="height: 38px;" class="me-3"></b-form-input>
        <button @click="createComment()" class="text-primary">게시</button>
      </span>
    </div>

    <!-- 댓글들 -->
    <b-list-group class="mt-4">
      <b-list-group-item class="d-flex justify-content-left align-items-center border-0 my-1"
        v-for="comment in comments" :key="comment.commentid" :comment="comment">
        <b-avatar src="https://placekitten.com/300/300" size="2.5rem" class="me-3"></b-avatar>
        <span class="d-flex justify-content-between" style="width: 100%;">
          <span>{{ comment.comment }}</span>
          <span v-if="comment.createdtime == comment.updatedtime ">생성시간:{{ comment.createdtime}}</span>
          <span v-else>수정시간:{{ comment.updatedtime}}</span>
          <span>
            <button v-b-modal.modal-1 variant="none" class="btn-warning badge text-dark fw-bold me-2">수정</button>
            <b-modal id="modal-1" title="댓글수정">
                <form @submit.prevent="UpdateComment(comment.commentid)">
                  <input type="text" v-model="newcontent" :placeholder="comment.comment">
                  <button class="btn btn-primary btn-sm">수정하기</button>
                </form>
            </b-modal>
            <button @click ="CommentDelete(comment.commentid)" class="btn-danger badge">삭제</button>
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
    }
  },
  created() {
    axios({
        url:'http://127.0.0.1:8080/article/1/comment',
        method:'get',
        headers: {
            'x-auth-token': `${localStorage.getItem('token')}`,
        },
      })
        .then(res=>{
          console.log(res.data)
          this.comments = res.data
          console.log(this.comment)
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
        url:`http://127.0.0.1:8080/article/1/comment`,
        method:'get',
        headers: {
          'x-auth-token': `${localStorage.getItem('token')}`,
        },
      })
        .then(res=>{
          this.comments = res.data
          
        })
        .catch(err => {
          console.log(err)
        })
    },
    createComment(){
      axios({
        url:'http://127.0.0.1:8080/article/'+this.$route.params.articleid+'/comment',
        method:'post',
        headers: {
          'x-auth-token': `${localStorage.getItem('token')}`,
        },
        data:{
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
        url:`http://127.0.0.1:8080/article/comment/`+commentid,
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
        url:`http://127.0.0.1:8080/article/comment/`+commentid,
        method:'put',
        headers: {
          'x-auth-token': `${localStorage.getItem('token')}`,
        },
        data:{
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
  }
}
</script>

<style>
.scale {
    margin: 10%;
    margin-top: 4%;
    align-content: center;
    justify-content: center;
  }

</style>