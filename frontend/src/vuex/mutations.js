export default {
  // 로그인 (토큰 생성) - 두호
  UPDATE_TOKEN(state, token) {
    state.token = token
  },
  LOGIN(state, bool) {
    state.loginState = bool
  }
}