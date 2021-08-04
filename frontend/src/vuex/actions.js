import axios from "axios";
import router from "../routes";

export default {
  // 뒤로가기 -형식
  // back() {
  //   this.$router.go(-1)
  // },

  // 로그인 - 두호
  login({ commit }, credentials) {
    axios({
      url: "http://i5b302.p.ssafy.io/api/account/login",
      method: "post",
      data: {
        email: credentials.email,
        password: credentials.password,
      },
    })
      .then((res) => {
        // console.log('성공!', res.data)
        commit("UPDATE_TOKEN", res.data);
        localStorage.setItem("token", res.data);
        // vuex 및 localStorage 에 로그인한 유저의 nickname 저장
        axios({
          url: `http://i5b302.p.ssafy.io/api/account/checkJWT`,
          method: "get",
          headers: {
            "Content-Type": "application/json",
            "X-AUTH-TOKEN": res.data,
          },
        })
          .then((res) => {
            commit("LOGGED_USER_NAME", res.data.nickname);
            localStorage.setItem("username", res.data.nickname);
            router.push({ name: "FeedMain" });
          })
          .catch((err) => {
            alert(err);
          });
      })
      .catch((err) => {
        // console.log('에러!', err)
        alert(err);
      });
  },

  searchGet({ commit }, token) {
    axios({
      url: `http://i5b302.p.ssafy.io/api/account/checkJWT`,
      method: "get",
      headers: {
        "Content-Type": "application/json",
        "X-AUTH-TOKEN": token,
      },
    })
      .then((res) => {
        axios({
          url: `http://i5b302.p.ssafy.io/search?id=${res.data.uid}`,
          method: "get",
          headers: {
            "Content-Type": "application/json",
            "X-AUTH-TOKEN": token,
          },
        })
          .then((res) => {
            commit("SEARCH_GET", res.data);
          })
          .catch((err) => {
            alert("JWT는 인증했지만 SEARCH 로그 GET 실패", err);
          });
      })
      .catch((err) => {
        alert("JWT 인증 실패", err);
      });
  },
  searchLive({ commit }, data) {
    axios({
      url: `http://i5b302.p.ssafy.io/search/live?nickname=${data.nickname}`,
      method: "get",
    })
      .then((res) => {
        commit("SEARCH_LIVE", res.data);
      })
      .catch((err) => {
        alert(err);
      });
  },

  scrapGet({ commit }, token) {
    axios({
      url: `http://i5b302.p.ssafy.io/account/checkJWT`,
      method: "get",
      headers: {
        "Content-Type": "application/json",
        "X-AUTH-TOKEN": token,
      },
    })
      .then(() => {
        axios({
          url: `http://i5b302.p.ssafy.io/scrap`,
          method: "get",
          headers: {
            "Content-Type": "application/json",
            "X-AUTH-TOKEN": token,
          },
        })
          .then((res) => {
            commit("SCRAP_GET", res.data);
          })
          .catch((err) => {
            alert("JWT 인증했지만 SCRAP GET 실패", err);
          });
      })
      .catch((err) => {
        alert("JWT 인증 실패", err);
      });
  },
  scrapDeleteMode({ commit }) {
    commit("SCRAP_DELETE_MODE");
  },
  scrapDelete({ commit }, payload) {
    axios({
      url: `http://i5b302.p.ssafy.io/scrap/${payload.scrapid}`,
      method: "delete",
      headers: {
        "Content-Type": "application/json",
        "X-AUTH-TOKEN": payload.token,
      },
    })
      .then(() => {
        axios({
          url: `http://i5b302.p.ssafy.io/account/checkJWT`,
          method: "get",
          headers: {
            "Content-Type": "application/json",
            "X-AUTH-TOKEN": payload.token,
          },
        })
          .then((res) => {
            axios({
              url: `http://i5b302.p.ssafy.io/scrap?userid=${res.data.uid}`,
              method: "get",
            })
              .then((res) => {
                commit("SCRAP_GET", res.data);
              })
              .catch((err) => {
                alert("DELETE 성공, JWT 인증 성공, GET 실패", err);
              });
          })
          .catch((err) => {
            alert("DELETE 성공 JWT 인증 실패", err);
          });
      })
      .catch((err) => {
        alert("DELETE 실패", err);
      });
  },
  logout({ commit }) {
    commit("LOGOUT");
  },
};
