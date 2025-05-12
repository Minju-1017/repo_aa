 import { initializeApp } from "https://www.gstatic.com/firebasejs/11.7.1/firebase-app.js";
  import { getAnalytics } from "https://www.gstatic.com/firebasejs/11.7.1/firebase-analytics.js";
  import { getMessaging, getToken, onMessage } from "https://www.gstatic.com/firebasejs/11.7.1/firebase-messaging.js";
import Toastify from "https://cdn.jsdelivr.net/npm/toastify-js/src/toastify-es.js";

// fire base 프로젝트 설정에 가면 다있음
  const firebaseConfig = {
    apiKey: "AIzaSyDKNh1XkBF1NRlT2I0gW52fUX_T6fawfmw",
    authDomain: "aaproject-2e7b5.firebaseapp.com", 
    projectId: "aaproject-2e7b5", 
    storageBucket: "aaproject-2e7b5.firebasestorage.app", 
    messagingSenderId: "158450897846", 
    appId: "1:158450897846:web:fb3fdad8c254c33f356717", 
    measurementId: "G-R8YTN63V3N"
  };
	
  // 이건 프로젝트설정 -> 클라우드 메시징에 가면 있음 아래쪽에
  const VAPID_KEY = "BKfz4cyI9xqNB3cH38UGasAP3uC3yj1IT34Pnwc9H5XRtIdD8nY1dqyXbO0KmL089lJ2TTAIZkxWh2yEz7Z8yIo";

  const app = initializeApp(firebaseConfig);
  const analytics = getAnalytics(app);
  const messaging = getMessaging(app);




  async function requestPermissionAndGetToken(uSeq) {
    const permission = await Notification.requestPermission();
    if (permission === 'granted') { //동의 했을때
      const token = await getToken(messaging, { vapidKey: VAPID_KEY });//토큰생성
	
      console.log("✅ FCM Token:", token);
	
	// 서버로 전송
	await fetch("/api/saveToken", {
  	method: "POST",
  	headers: {
   	 "Content-Type": "application/json"
  	},
  	body: JSON.stringify({ uSeq, token })
	});

    } else {
      console.warn("❌ 알림 권한 거부됨");
    }
  }

//ui 상단에다 실시간 알림 보여주기 
  onMessage(messaging, (payload) => {
    new Notification(payload.notification.title, {
      body: payload.notification.body,
    });
Toastify({
	  text: payload.notification.title + ": " + payload.notification.body,
	  duration: 10000,
	  gravity: "top",
	  position: "right",
	  backgroundColor: "#ff6b6b"
	}).showToast();
  });

  //세션에 로그인값이 없다면 ! 알림은 물어보지도 말자!
let uSeq = document.querySelector("input[name=uSeq]")?.value;
if(uSeq != null && uSeq !== ""){
  requestPermissionAndGetToken(uSeq);
}

