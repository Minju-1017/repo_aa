package com.aa;

import org.springframework.stereotype.Component;

@Component
public class Constants {
	
	// Url Path
	public static String ABBREVIATION_XDM = "xdm";	// 어드민
	public static String ABBREVIATION_USR = "usr";	// 유저
	
	// Login Form URL
	public static String URL_LOGIN_FORM_XDM = "/xdm/member/MemberXdmSignIn";
	public static String URL_LOGIN_FORM_USR = "/usr/member/MemberUsrSignIn";
	
	// Login User Seq
	public static String SESSION_SEQ_NAME_XDM = "sessSeqXdm";
	public static String SESSION_SEQ_NAME_USR = "sessSeqUsr";
	
	// Login Session Time
	public static int SESSION_MINUTE = 60 * 30; // 30분
	
	// Animal에서 사용하는 코드 그룹
	public final static int USER_ANIMAL_CATECORY = 1; // uaCateCd
	
}
