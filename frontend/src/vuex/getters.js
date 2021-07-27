export default {
  loginState: state => state.loginState,

  searchGet: state => {
    return state.searchGet.slice().reverse()
  }
}