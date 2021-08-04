export default {
  // 로그인 (토큰 생성) - 두호
  UPDATE_TOKEN(state, token) {
    state.token = token
  },
  LOGGED_USER_NAME(state, username) {
    state.username = username
  },

  SEARCH_GET(state, searchGet) {
    state.searchGet = searchGet
  },
  SEARCH_LIVE(state, userList) {
    state.searchLive = userList
  },

  SCRAP_GET(state, scrapList) {
    state.scrapList = scrapList
  },
  SCRAP_DELETE_MODE(state) {
    state.scrapMode = !state.scrapMode
  },

  PROMISE_DETAIL_GET(state, promiseDetail) {
    state.promiseDetail = promiseDetail
  },

  LOGOUT () {
    localStorage.removeItem('token') 
    location.reload(); 
  }

}