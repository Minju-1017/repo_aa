package com.aa.module.token;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aa.module.miss.MissDto;
import com.aa.module.notification.NotificationService;
import com.google.firebase.messaging.FirebaseMessagingException;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class TokenController {
	
	@Autowired
	NotificationService notificationService;
	@Autowired
	TokenService service;
	
		@RequestMapping(value ="/api/saveToken")
		public ResponseEntity<?> tokenInsert(@RequestBody TokenDto dto,HttpServletRequest httpServletRequest) {
			String userAgent = httpServletRequest.getHeader("User-Agent");
			if(userAgent != null && !userAgent.isEmpty() && userAgent.matches(".*(Mobi|Android|iPhone|iPad|iPod).*")) {
				dto.setDevicetype("mobile");
			}else {
				dto.setDevicetype("desktop");
			}
			
				service.tokenInsert(dto);
				return ResponseEntity.ok("토큰 저장 완료");
			
		}
		
		@RequestMapping("/api/sendTestPush")
	    public ResponseEntity<?> sendTestPush(MissDto missDto) {
	        List<TokenDto> tokenList = service.tokenList();

	        for (TokenDto token : tokenList) {
	            String fcmToken = token.getToken();
	            if (fcmToken != null && !fcmToken.isEmpty()) {
	                try {
	                    notificationService.sendNotification(
	                        fcmToken,
	                        "📢 실종 알림",
	                        "반려동물 이름: " + missDto.getUaName()
	                        + "/ 품종: " + missDto.getUaBreed()
	                        + "/ 실종 위치: " + missDto.getMbDetailAddr()
	                    );
	                } catch (FirebaseMessagingException e) {
	                    if ("UNREGISTERED".equals(e.getMessagingErrorCode().name())) {
	                        System.out.println("⛔ 유효하지 않은 토큰: " + fcmToken);
	                        // DB에서 해당 토큰 삭제 또는 무효 처리
	                        service.deleteToken(fcmToken);
	                    } else {
	                        // 기타 예외는 로그만 출력
	                        System.out.println("⚠️ 푸시 전송 오류: " + e.getMessage());
	                    }
	                } catch (Exception e) {
	                    // 예기치 않은 예외 방지
	                    System.out.println("⚠️ 예외 발생: " + e.getMessage());
	                }
	            }
	        }

	        return ResponseEntity.ok("전체 푸시 전송 완료");
	    }

}
