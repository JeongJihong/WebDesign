export default {
  // 로그인 (토큰 생성) - 두호
  UPDATE_TOKEN(state, token) {
    state.token = token
  },
  LOGGED_USER_NAME(state, username) {
    state.username = username
  },
  FIREBASE_TOKEN_GET(state, firebaseToken) {
    state.firebaseToken = firebaseToken
  },

  SEARCH_GET(state, searchGet) {
    state.searchGet = searchGet
  },
  SEARCH_LIVE(state, userList) {
    state.searchLive = userList
  },

  ALARM_LIKE_GET(state, likeList) {
    state.likeList = likeList
  },
  ALARM_FOLLOW_GET(state, followList) {
    state.followList = followList
  },
  ALARM_PROMISE_GET(state, promiseList) {
    state.promiseList = promiseList
  },

  SCRAP_GET(state, scrapList) {
    state.scrapList = scrapList
  },
  SCRAP_DELETE_MODE(state) {
    state.scrapMode = !state.scrapMode
  },

  PROMISE_LIST_GET(state, promiseListGet) {
    // console.log(promiseListGet)
    state.upcomingPromises = promiseListGet.upcoming
    state.waitingPromises = promiseListGet.waiting
  },
  PROMISE_DETAIL_GET(state, promiseDetail) {
    state.promiseDetail = promiseDetail
  },

  LOGOUT () {
    localStorage.removeItem('token') 
    localStorage.removeItem('username')
    location.reload(); 
  },

  // kakaoLogin 종우
  KAKAO_LOGIN(state, kakaoLogin) {
    state.kakaoLogin = kakaoLogin
  },

  // 카카오 로그인인지 그냥 로그인인지
  UPDATE_ISLOGINBYKAKAO(state, isLoginByKakao) {
    state.isLoginByKakao = isLoginByKakao
  },
}