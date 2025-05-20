package com.aa.module.token;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface TokenDao {
	
	public int tokenOne(TokenDto dto);
	public int tokenInsert(TokenDto dto);
	public List<TokenDto> tokenList();
	public int deleteToken(String dto);
}
