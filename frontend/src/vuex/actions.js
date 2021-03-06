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
      url: "http://127.0.0.1:8080/account/login",
      method: "post",
      data: {
        email: credentials.email,
        password: credentials.password,
      },
    })
      .then((res) => {
        let token = res.data

        commit("UPDATE_TOKEN", res.data);
        localStorage.setItem("token", res.data);
        // 카카오로그인인지 그냥 로그인인지 구별하기 위한 변수 - 종우
        commit("UPDATE_ISLOGINBYKAKAO", false);
        localStorage.setItem("isLoginByKakao", false);

        // vuex 및 localStorage 에 로그인한 유저의 nickname 저장
        axios({
          url: 'http://127.0.0.1:8080/account/checkJWT',
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
              url: 'http://127.0.0.1:8080/alarm/register',
              method: 'post',
              headers: {
                "Content-Type": "application/json",
                "X-AUTH-TOKEN": token,
              },
              data: localStorage.getItem('firebaseToken')

            })
              .then(() =>
                router.push({ name: "FeedMain" })
              )
              // .catch((err) => {
              //   console.log('Firebase Token POST Failed' + err)
              // })
          })
          // .catch((err) => {
          //   alert(err);
          // });
      })
      .catch((err) => {
        // console.log('에러!', err)
        // alert(err);
      });
  },
  firebaseTokenGet({ commit }, firebaseToken) {
    localStorage.setItem('firebaseToken', firebaseToken)
    commit('FIREBASE_TOKEN_GET', firebaseToken)
  },

  searchGet({ commit }, token) {
    axios({
      url: `http://127.0.0.1:8080/account/checkJWT`,
      method: "get",
      headers: {
        "Content-Type": "application/json",
        "X-AUTH-TOKEN": token,
      },
    })
      .then((res) => {
        axios({
          url: `http://127.0.0.1:8080/search?id=${res.data.uid}`,
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
      url: `http://127.0.0.1:8080/search/live?nickname=${data.nickname}`,
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
      url: 'http://127.0.0.1:8080/alarm/like',
      method: "get",
      headers: {
        "Content-Type": "application/json",
        "X-AUTH-TOKEN": token,
      },
    })
      .then(res => {
        commit("ALARM_LIKE_GET", res.data)
      })
      .catch(err => {
        alert(err)
      })
  },
  alarmFollowGet({ commit }, token) {
    axios({
      url: 'http://127.0.0.1:8080/alarm/follow',
      method: "get",
      headers: {
        "Content-Type": "application/json",
        "X-AUTH-TOKEN": token,
      },
    })
      .then(res => {
        commit("ALARM_FOLLOW_GET", res.data)
      })
      .catch(err => {
        alert(err)
      })
  },
  alarmPromiseGet({ commit }, token) {
    axios({
      url: 'http://127.0.0.1:8080/alarm/promise',
      method: 'get',
      headers: {
        "Content-Type": "application/json",
        "X-AUTH-TOKEN": token,
      }
    })
      .then(res => {
        commit("ALARM_PROMISE_GET", res.data)
      })
      .catch(err => {
        alert(err)
      })
  },

  scrapGet({ commit }, token) {
    axios({
      url: `http://127.0.0.1:8080/account/checkJWT`,
      method: "get",
      headers: {
        "Content-Type": "application/json",
        "X-AUTH-TOKEN": token,
      },
    })
      .then(() => {
        axios({
          url: `http://127.0.0.1:8080/scrap`,
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

  promiseListGet({ commit }) {
    axios({
      url: 'http://127.0.0.1:8080/promise',
      method: 'get',
      headers: {
        'x-auth-token': `${localStorage.getItem('token')}`
      }
    })
      .then(res => {
        commit('PROMISE_LIST_GET', res.data)
      })
  },
  promiseDetailGet({ commit }, payload) {
    // payload: { token, promiseid }
    axios({
      url: `http://127.0.0.1:8080/promise/${payload.promiseid}`,
      method: "get",
      headers: {
        "Content-Type": "application/json",
        "X-AUTH-TOKEN": payload.token,
      },
    })
      .then(res => {
        commit("PROMISE_DETAIL_GET", res.data)
      })
      // .catch(() => {
      //   router.push({ name: 'PromiseList' })
      // });
  },

  logout({ commit }) {
    commit("UPDATE_ISLOGINBYKAKAO", false);
    localStorage.setItem('isLoginByKakao', false)
    commit("LOGOUT")
  },

  // kakaoLogin 종우
  kakaoLogin({ commit }) {
    window.Kakao.Auth.login({
      scope : 'profile_nickname, profile_image, account_email',
      success: (authObj) => {
        window.Kakao.API.request({
          url:'/v2/user/me',
          success : res => {
            const kakao_account = res.kakao_account;
            const userInfo = {
              access_token : authObj.access_token,
              nickname : kakao_account.profile.nickname,
              email : kakao_account.email,
              thumbnail : kakao_account.profile.profile_image_url,
              password : '',
              account_type : 2,
            }
            axios({
              method: 'post',
              url: `http://127.0.0.1:8080/kakao`,
              params: {
                access_token : userInfo.access_token,
                email : userInfo.email,
                nickname : userInfo.nickname,
                thumbnail : userInfo.thumbnail,
              }
            })
            .then(res => {
              let token = res.data;
              commit("UPDATE_TOKEN", res.data);
              localStorage.setItem("token", res.data);
              commit("UPDATE_ISLOGINBYKAKAO", true);
              localStorage.setItem("isLoginByKakao", true);

              axios({
                url: 'http://127.0.0.1:8080/account/checkJWT',
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
                    url: 'http://127.0.0.1:8080/alarm/register',
                    method: 'post',
                    headers: {
                      "Content-Type": "application/json",
                      "X-AUTH-TOKEN": token,
                    },
                    data: localStorage.getItem('firebaseToken')
      
                  })
                    .then(() => {
                      router.push({ name: "FeedMain" })
                    })
                    .catch((err) => {
                      console.log('Firebase Token POST Failed' + err)
                    })
                })
                .catch((err) => {
                  alert(err);
                });
            })
            .catch(err => {
              console.log(err);
              console.log("데이터베이스에 회원 정보가 없음!");
            })
            alert("로그인 성공!");
            // this.$bvModal.hide("bv-modal-example");
          },
          fail : error => {
              this.$router.push("/errorPage");
              console.log(error);
          }
        })
      },
      fail: (error) => {
          console.log(error)
      }
    });
  },
};
