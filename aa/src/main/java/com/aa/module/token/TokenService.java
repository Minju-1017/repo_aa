package com.aa.module.token;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

	@Autowired
	TokenDao dao;
	
	public int tokenOne(TokenDto dto) {
		return dao.tokenOne(dto); 
	}
	
	public int tokenInsert(TokenDto dto) {
		int count = tokenOne(dto);
		if(count == 0) {
		return dao.tokenInsert(dto);
		}else {
			return 1;
		}
	}
	
	public List<TokenDto> tokenList(){
		return dao.tokenList();
	}
	
	public int deleteToken(String dto) {
		return dao.deleteToken(dto);
	}
}
