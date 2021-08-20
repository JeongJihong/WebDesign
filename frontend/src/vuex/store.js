import Vue from "vue";
import Vuex from "vuex";
import getters from "./getters";
import actions from "./actions";
import mutations from "./mutations";

Vue.use(Vuex);

const state = {
  isUser: false,
  // 로그인 관련 - 두호
  token: localStorage.getItem("token") || "",
  username: localStorage.getItem("username") || "",
  firebaseToken: localStorage.getItem('firebaseToken') || '',
  promiseDeleteMode: false,

  likeList: [],
  followList: [],
  promiseList: [],

  searchGet: [],
  searchLive: {},
  scrapList: [],
  scrapMode: false,

  upcomingPromises: [],
  waitingPromises: [],
  promiseDetail: {},

  isLoginByKakao: false,

  attendantsInfo: {},
  attendantsLength: 0,
};

export default new Vuex.Store({
  state,
  mutations,
  getters,
  actions,
});
