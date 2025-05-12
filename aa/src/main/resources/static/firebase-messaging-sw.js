// firebase-messaging-sw.js (최소 예제)
importScripts("https://www.gstatic.com/firebasejs/11.7.1/firebase-app-compat.js");
importScripts("https://www.gstatic.com/firebasejs/11.7.1/firebase-messaging-compat.js");

firebase.initializeApp({
	apiKey: "AIzaSyDKNh1XkBF1NRlT2I0gW52fUX_T6fawfmw",
	   authDomain: "aaproject-2e7b5.firebaseapp.com",
	   projectId: "aaproject-2e7b5",
	   storageBucket: "aaproject-2e7b5.firebasestorage.app",
	   messagingSenderId: "158450897846",
	   appId: "1:158450897846:web:fb3fdad8c254c33f356717",
	   measurementId: "G-R8YTN63V3N"
});

const messaging = firebase.messaging();

messaging.onBackgroundMessage(function(payload) {
  console.log("📩 백그라운드 알림 수신:", payload);
  self.registration.showNotification(payload.notification.title, {
    body: payload.notification.body,
  });
});


