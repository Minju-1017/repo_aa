package com.aa.module.naver;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class NaverService {
	 private final WebClient webClient = WebClient.create(); // baseUrl 없이 사용

	 	@Value("${naver.client.id}")
	    private String clientId;
	 	
	 	@Value("${naver.client.secret}")
	    private String clientSecret;

	    public NaverDto getUserInfo(String code,String state) throws Exception {
	        // 1. access token 요청
	        String tokenResponse = webClient.post()
	            .uri(uriBuilder -> uriBuilder
	                .scheme("https")
	                .host("nid.naver.com")
	                .path("/oauth2.0/token")
	                .queryParam("grant_type", "authorization_code")
	                .queryParam("client_id", clientId)
	                .queryParam("client_secret", clientSecret)
	                .queryParam("code", code)
	                .queryParam("state", state)
	                .build())
	            .retrieve()
	            .bodyToMono(String.class)
	            .block();
	        ObjectMapper mapper = new ObjectMapper();
	        JsonNode tokenJson = mapper.readTree(tokenResponse);
	        String accessToken = tokenJson.get("access_token").asText();

	        // 2. 사용자 정보 요청
	        String userInfo = webClient.get()
	            .uri("https://openapi.naver.com/v1/nid/me")
	            .header("Authorization", "Bearer " + accessToken)
	            .retrieve()
	            .bodyToMono(String.class)
	            .block();
	        JsonNode userJson = mapper.readTree(userInfo).get("response");

	        NaverDto dto = new NaverDto();
	       
	        dto.setId(userJson.get("id").asText());
	        dto.setEmail(userJson.get("email").asText());
	        dto.setName(userJson.get("name").asText());
	        dto.setNickName(userJson.get("nickname").asText());
	        dto.setMessage(mapper.readTree(userInfo).get("message").asText());

	        return dto;
	    }

}
