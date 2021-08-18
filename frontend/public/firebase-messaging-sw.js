// are not available in the service worker.
importScripts("https://www.gstatic.com/firebasejs/7.6.1/firebase-app.js");
importScripts("https://www.gstatic.com/firebasejs/7.6.1/firebase-messaging.js");

// Initialize the Firebase app in the service worker by passing in the
// messagingSenderId.
firebase.initializeApp({
  apiKey: "AIzaSyBF2i3yoTsOyPq8ftdBLVtxrSCUTMX1cvM",
  projectId: "fcm-springboot-dbe2f",
  messagingSenderId: "263809922923",
  appId: "1:263809922923:web:cd880ed771dc7122813e2e",
});

// Retrieve an instance of Firebase Messaging so that it can handle background
// messages.
const messaging = firebase.messaging();

// 백그라운드 상태에서 받은 알림 처리
messaging.setBackgroundMessageHandler((payload) => {
  console.log("[firebase-messaging-sw.js] Received background message ", payload);
  // Customize notification here
  const notificationTitle = "Background Message Title";
  const notificationOptions = {
    body: "Background Message body.",
    icon: "/firebase-logo.png",
  };
  return self.registration.showNotification(notificationTitle, notificationOptions);
});

// Handle received push notification at foreground (수정)
// messaging.onMessage((payload) => {
//   console.log('onMessage: ', payload);
//         var title = "고라니 서비스";
//         var options = {
//                 body: payload.notification.body
//         };

//         var notification = new Notification(title, options);
//   console.log(payload);
//   alert(payload.data.message);
// });
