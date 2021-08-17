import Vue from "vue";
import App from "./App.vue";
// import VueRouter from 'vue-router';
// import routes from './routes'
import router from "./routes";
import store from "./vuex/store";
import { BootstrapVue, IconsPlugin } from "bootstrap-vue";

// Import Bootstrap an BootstrapVue CSS files (order is important)
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";

//firebase FCM
import firebase from "firebase/app";
import "firebase/messaging";

// Make BootstrapVue available throughout your project
Vue.use(BootstrapVue);
Vue.use(IconsPlugin);
// Optionally install the BootstrapVue icon components plugin
Vue.config.productionTip = false;

// window.Kakao.init("5e434a2e824d6fcfd6ca2b69e2cdf6c2");

// Vue.use(VueRouter)

// const router = new VueRouter({
//     routes,
// });

//firebase main.js alarm(FCM)

const firebaseConfig = {
  apiKey: "AIzaSyBF2i3yoTsOyPq8ftdBLVtxrSCUTMX1cvM",
  authDomain: "fcm-springboot-dbe2f.firebaseapp.com",
  projectId: "fcm-springboot-dbe2f",
  storageBucket: "fcm-springboot-dbe2f.appspot.com",
  messagingSenderId: "263809922923",
  appId: "1:263809922923:web:cd880ed771dc7122813e2e",
  measurementId: "G-8LXZ2P0M9D",
};

firebase.initializeApp(firebaseConfig);

const messaging = firebase.messaging();

messaging.usePublicVapidKey(
  "BAgP-_b9hSefAF5PJOWYuPR98_SJnfL2S88EpNtZMHeK1fBApqGoiMbS2VgT0HyLWW4QLhHl5o797UUetpGWEJM"
);

// 알림 수신을 위한 사용자 권한 요청
Notification.requestPermission().then((permission) => {
  // console.log("permission ", permission);
  if (permission !== "granted") {
    alert("알림을 허용해주세요");
  }
});

// TODO: Send token to server for send notification
// messaging.getToken().then(console.log);
messaging.getToken().then((res) => {
  // console.log(res);
  store.dispatch("firebaseTokenGet", res);
});

// Handle received push notification at foreground
// messaging.onMessage((payload) => {
//   console.log(payload);
//   alert(payload.data.message);
// });

// messaging.setBackgroundMessageHandler((payload) => {
//   console.log("[firebase-messaging-sw.js] Received background message ", payload);
//   // Customize notification here
//   const notificationTitle = "Background Message Title";
//   const notificationOptions = {
//     body: "Background Message body.",
//     icon: "/firebase-logo.png",
//   };

//   return self.registration.showNotification(notificationTitle, notificationOptions);
// });

messaging.onMessage((payload) => {
  const title = payload.notification.title;
  const options = {
    body: payload.notification.body,
  };
  navigator.serviceWorker.ready.then((registration) => {
    registration.showNotification(title, options);
  });
});

// 카카오 소셜 로그인
window.Kakao.init("6d8be51fab4c89e30255e5df438f02d6");

var path = window.location.pathname;
console.log(path);

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
