import axios from "axios"
import router from '../routes'

export default {
  // 뒤로가기 -형식
  // back() {
  //   this.$router.go(-1)
  // },


  // 로그인 - 두호
  login({ commit }, credentials) {
    axios({
      url: 'http://127.0.0.1:8080/account/login',
      method: 'post',
      data:{
        email: credentials.email,
        password: credentials.password
      },
    })
      .then(res => {
        // console.log('성공!', res.data)
        commit('UPDATE_TOKEN', res.data)
        localStorage.setItem('token', res.data)
        router.push({ name: 'FeedMain' })
      })
      .catch(err => {
        // console.log('에러!', err)
        alert(err)
      })
  },

  searchGet({ commit }, token) {
    axios({
      url: `http://127.0.0.1:8080/account/checkJWT`,
      method: 'get',
      headers: {
        'Content-Type': 'application/json',
        'X-AUTH-TOKEN': token
      }
    })
      .then(res => {
        axios({
          url: `http://127.0.0.1:8080/search?id=${res.data.uid}`,
          method: 'get',
          headers: {
            'Content-Type': 'application/json',
            'X-AUTH-TOKEN': token
          }
        })
          .then(res => {
            commit('SEARCH_GET', res.data)
          })
          .catch(err => {
            alert('JWT는 인증했지만 SEARCH 로그 GET 실패', err)
          })
      })
      .catch(err => {
        alert('JWT 인증 실패', err)
      })
  },
  searchLive({ commit }, data) {
    axios({
      url: `http://127.0.0.1:8080/search/live?nickname=${data.nickname}`,
      method: 'get',
    })
      .then(res => {
        commit('SEARCH_LIVE', res.data)
      })
      .catch(err => {
        alert(err)
      })
  },

  scrapGet({ commit }, token) {
    axios({
      url: `http://127.0.0.1:8080/account/checkJWT`,
      method: 'get',
      headers: {
        'Content-Type': 'application/json',
        'X-AUTH-TOKEN': token
      }
    })
      .then(res => {
        const range = res.data.indexOf('/') - 1
        const id = res.data.substr(0, range)
        axios({
          url: `http://127.0.0.1:8080/scrap?userid=${id}`,
          method: 'get'
        })
          .then(res => {
            commit('SCRAP_GET', res.data)
          })
          .catch(err => {
            alert('JWT 인증했지만 SCRAP GET 실패', err)
          })
      })
      .catch(err => {
        alert('JWT 인증 실패', err)
      })
  },
  scrapDeleteMode({ commit }) {
    commit('SCRAP_DELETE_MODE')
  },
  scrapDelete({ commit }, scrapId, token) {
    axios({
      url: `http://127.0.0.1:8080/scrap/${scrapId}`,
      method: 'delete',
    })
      .then(() => {
        axios({
          url: `http://127.0.0.1:8080/account/checkJWT`,
          method: 'get',
          headers: {
            'Content-Type': 'application/json',
            'X-AUTH-TOKEN': token
          }
        })
          .then(res => {
            const range = res.data.indexOf('/') - 1
            const id = res.data.substr(0, range)
            axios({
              url: `http://127.0.0.1:8080/scrap?userid=${id}`,
              method: 'get'
            })
              .then(res => {
                commit('SCRAP_GET', res.data)
              })
              .catch(err => {
                alert('DELETE 성공, JWT 인증 성공, GET 실패', err)
              })
            })
          .catch(err => {
            alert('DELETE 성공 JWT 인증 실패', err)
          })
      })
      .catch(err => {
        alert('DELETE 실패', err)
      })
  },
  logout ({ commit }) { 
    commit('LOGOUT') 
  },
}