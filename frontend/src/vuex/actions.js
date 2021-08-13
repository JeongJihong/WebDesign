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
      url: "https://i5b302.p.ssafy.io/api/account/login",
      method: "post",
      data: {
        email: credentials.email,
        password: credentials.password,
      },
    })
      .then((res) => {
        let token = res.data;

        commit("UPDATE_TOKEN", res.data);
        localStorage.setItem("token", res.data);
        // vuex 및 localStorage 에 로그인한 유저의 nickname 저장
        axios({
          url: "https://i5b302.p.ssafy.io/api/account/checkJWT",
          method: "get",
          headers: {
            "Content-Type": "application/json",
            "X-AUTH-TOKEN": res.data,
          },
        })
          .then((res) => {
            commit("LOGGED_USER_NAME", res.data.nickname);
            localStorage.setItem("username", res.data.nickname);

            axios({
              url: "https://i5b302.p.ssafy.io/api/alarm/register",
              method: "post",
              headers: {
                "Content-Type": "application/json",
                "X-AUTH-TOKEN": token,
              },
              data: localStorage.getItem("firebaseToken"),
            })
              .then(() => router.push({ name: "FeedMain" }))
              .catch((err) => {
                console.log("Firebase Token POST Failed" + err);
              });
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
  firebaseTokenGet({ commit }, firebaseToken) {
    localStorage.setItem("firebaseToken", firebaseToken);
    commit("FIREBASE_TOKEN_GET", firebaseToken);
  },

  searchGet({ commit }, token) {
    axios({
      url: `https://i5b302.p.ssafy.io/api/account/checkJWT`,
      method: "get",
      headers: {
        "Content-Type": "application/json",
        "X-AUTH-TOKEN": token,
      },
    })
      .then((res) => {
        axios({
          url: `https://i5b302.p.ssafy.io/api/search?id=${res.data.uid}`,
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
      url: `https://i5b302.p.ssafy.io/api/search/live?nickname=${data.nickname}`,
      method: "get",
    })
      .then((res) => {
        commit("SEARCH_LIVE", res.data);
      })
      .catch((err) => {
        alert(err);
      });
  },

  alarmLikeGet({ commit }, token) {
    axios({
      url: "https://i5b302.p.ssafy.io/api/alarm/like",
      method: "get",
      headers: {
        "Content-Type": "application/json",
        "X-AUTH-TOKEN": token,
      },
    })
      .then((res) => {
        commit("ALARM_LIKE_GET", res.data);
      })
      .catch((err) => {
        alert(err);
      });
  },
  alarmFollowGet({ commit }, token) {
    axios({
      url: "https://i5b302.p.ssafy.io/api/alarm/follow",
      method: "get",
      headers: {
        "Content-Type": "application/json",
        "X-AUTH-TOKEN": token,
      },
    })
      .then((res) => {
        commit("ALARM_FOLLOW_GET", res.data);
      })
      .catch((err) => {
        alert(err);
      });
  },
  alarmPromiseGet({ commit }, token) {
    axios({
      url: "https://i5b302.p.ssafy.io/api/alarm/promise",
      method: "get",
      headers: {
        "Content-Type": "application/json",
        "X-AUTH-TOKEN": token,
      },
    })
      .then((res) => {
        commit("ALARM_PROMISE_GET", res.data);
      })
      .catch((err) => {
        alert(err);
      });
  },

  scrapGet({ commit }, token) {
    axios({
      url: `https://i5b302.p.ssafy.io/api/account/checkJWT`,
      method: "get",
      headers: {
        "Content-Type": "application/json",
        "X-AUTH-TOKEN": token,
      },
    })
      .then(() => {
        axios({
          url: `https://i5b302.p.ssafy.io/api/scrap`,
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

  promiseDetailGet({ commit }, payload) {
    // payload: { token, promiseid }
    axios({
      url: `https://i5b302.p.ssafy.io/api/promise/${payload.promiseid}`,
      method: "get",
      headers: {
        "Content-Type": "application/json",
        "X-AUTH-TOKEN": payload.token,
      },
    })
      .then((res) => {
        commit("PROMISE_DETAIL_GET", res.data);
      })
      .catch(() => {
        router.push({ name: "PromiseList" });
      });
  },

  logout({ commit }) {
    commit("LOGOUT");
  },
};
