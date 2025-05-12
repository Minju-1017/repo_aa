package com.aa.module.token;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aa.module.miss.MissDto;
import com.aa.module.notification.NotificationService;

@RestController
public class TokenController {
	
	@Autowired
	NotificationService notificationService;
	@Autowired
	TokenService service;
	
		@RequestMapping(value ="/api/saveToken")
		public ResponseEntity<?> tokenInsert(@RequestBody TokenDto dto) {
			
				service.tokenInsert(dto);
				return ResponseEntity.ok("토큰 저장 완료");
			
		}
		
		@RequestMapping("/api/sendTestPush")
	    public ResponseEntity<?> sendTestPush(MissDto missDto) {
	        List<TokenDto> tokenList = service.tokenList();

	        for (TokenDto token : tokenList) {
	        	if(token.getToken() != null && !token.getToken().equals("")) {
	            notificationService.sendNotification(token.getToken(), "📢 실종 알림 ", "반려동물 이름: "+
	        	missDto.getUaName()+"/ 품종: "+missDto.getUaBreed()+"/ 실종 위치: "+missDto.getMbDetailAddr());
	        	}
	        }

	        return ResponseEntity.ok("전체 푸시 전송 완료");
	    }

}
