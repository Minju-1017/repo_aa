package com.aa.module.member;


import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/naver")
public class NaverLoginController {


	    @Value("${naver.client.id}")
	    private String clientId;

	    @Value("${naver.client.secret}")
	    private String clientSecret;

	    @GetMapping("/callback")
	    public String naverCallback(
	            @RequestParam("code") String code,
	            @RequestParam("state") String state,
	            HttpSession session
	    ) {
	        try {
	            // ✅ 1. Access Token 요청
	            String tokenUrl = UriComponentsBuilder.fromHttpUrl("https://nid.naver.com/oauth2.0/token")
	                    .queryParam("grant_type", "authorization_code")
	                    .queryParam("client_id", clientId)
	                    .queryParam("client_secret", clientSecret)
	                    .queryParam("code", code)
	                    .queryParam("state", state)
	                    .toUriString();

	            RestTemplate restTemplate = new RestTemplate();
	            ResponseEntity<Map> tokenResponse = restTemplate.getForEntity(tokenUrl, Map.class);

	            if (!tokenResponse.getStatusCode().is2xxSuccessful()) {
	                throw new RuntimeException("토큰 요청 실패");
	            }

	            String accessToken = (String) tokenResponse.getBody().get("access_token");

	            // ✅ 2. 사용자 정보 요청
	            HttpHeaders headers = new HttpHeaders();
	            headers.add("Authorization", "Bearer " + accessToken);
	            HttpEntity<?> request = new HttpEntity<>(headers);

	            ResponseEntity<Map> profileResponse = restTemplate.exchange(
	                    "https://openapi.naver.com/v1/nid/me",
	                    HttpMethod.GET,
	                    request,
	                    Map.class
	            );

	            Map<String, Object> userInfo = (Map<String, Object>) profileResponse.getBody().get("response");

	            // ✅ 3. 사용자 정보 사용 (예: 세션에 저장)
	            session.setAttribute("loginUser", userInfo);


	            return "redirect:/usr/index";

	        } catch (Exception e) {
	            return "redirect:/error";
	        }
	    }
}
