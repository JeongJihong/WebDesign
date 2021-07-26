import axios from "axios"

export default {
  // 뒤로가기 -형식
  back() {
    this.$router.go(-1)
  },

  // 로그인 - 두호
  login({ commit }, credentials) {
    axios({
      url:'http://127.0.0.1:8080/account/login',
      method:'post',
      data:{
        email: credentials.email,
        password: credentials.password
      },
    })
      .then(res=>{
        console.log('성공!', res.data)
        commit('UPDATE_TOKEN', res.data)
        localStorage.setItem('token', res.data)
        commit('LOGIN', true)
      })
      .catch(err=>{
        console.log('에러!', err)
        // alert(JSON.stringify(err.data))
        alert(err)
      })
  }
}
