package com.aa.module.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aa.Constants;
import com.aa.module.code.CodeService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(value={"/xdm/member/"})
public class MemberController {

	private String path_admin = "xdm/member/";	// Admin
	private String path_user = "usr/member/";	// User
	
	@Autowired
	MemberService service;
	
	@Autowired
	CodeService codeService;
	
	////////////////////////////////////////////////////////////////
	
	/**
	 * 로그인 세션 처리 - HO Xdm
	 * @param httpSession
	 * @param memberDto
	 * @throws Exception
	 */
	private void xdmSignIn(HttpSession httpSession, MemberDto memberDto) throws Exception {
		httpSession.setMaxInactiveInterval(Constants.SESSION_MINUTE);
		httpSession.setAttribute("sessSeqXdm", memberDto.getuSeq());
		httpSession.setAttribute("sessIdXdm", memberDto.getuId());
		httpSession.setAttribute("sessNameXdm", memberDto.getuName());
	}
	
	/**
	 * 로그아웃 세션 처리 - HO Xdm
	 * @param httpSession
	 */
	private void xdmSignOut(HttpSession httpSession) {
		httpSession.setAttribute("sessSeqXdm", null);
		httpSession.setAttribute("sessIdXdm", null);
		httpSession.setAttribute("sessNameXdm", null);
	}
	
	/**
	 * 로그인 세션 처리 - WH Xdm
	 * @param httpSession
	 * @param memberDto
	 * @throws Exception
	 */
	private void usrSignIn(HttpSession httpSession, MemberDto memberDto) throws Exception {
		httpSession.setMaxInactiveInterval(Constants.SESSION_MINUTE);
		httpSession.setAttribute("sessSeqUsr", memberDto.getuSeq());
		httpSession.setAttribute("sessIdUsr", memberDto.getuId());
		httpSession.setAttribute("sessNameUsr", memberDto.getuName());
	}
	
	/**
	 * 로그아웃 세션 처리 - WH Xdm
	 * @param httpSession
	 */
	private void usrSignOut(HttpSession httpSession) {
		httpSession.setAttribute("sessSeqUsr", null);
		httpSession.setAttribute("sessIdUsr", null);
		httpSession.setAttribute("sessNameUsr", null);
	}
	
	/**
	 * 암호화
	 * @param str
	 * @param length
	 * @return
	 */
	public String encodeBcrypt(String str, int length) {
		return new BCryptPasswordEncoder(length).encode(str);
	}
	
	/**
	 * 암호화된 문자열 체크
	 * @param str
	 * @param length
	 * @return
	 */
	public boolean matchesBcrypt(String inputStr, String str, int length) {
		return new BCryptPasswordEncoder(length).matches(inputStr, str);
	}
	
	////////////////////////////////////////////////////////////////
	
	/**
	 * 로그인 화면 이동 - HO Xdm
	 * @return
	 */
	@RequestMapping(value = "MemberXdmSignIn")	
	public String memberXdmSignIn() throws Exception {				
		return path_admin + "MemberXdmSignIn";
	}
	
	/**
	 * Ajax를 통한 로그인 처리 - HO Xdm
	 * @param memberDto
	 * @return
	 * @throws Exception
	 */
	@ResponseBody // Ajax 코드는 무조건 써준다.
	@RequestMapping(value = "MemberXdmSignInProc")
	public Map<String, Object> memberXdmSignInProc(MemberDto memberDto, HttpSession httpSession) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		MemberDto mDto = service.selectSignInMember(memberDto); // MyBatis에서 디비 검색 후 결과값이 없으면 NULL이 떨어짐
		
		if (mDto == null) { 
			returnMap.put("rt", "fail");
		} else {
			if (matchesBcrypt(memberDto.getuPwd(), mDto.getuPwd(), 10)) {
				xdmSignIn(httpSession, mDto);
				returnMap.put("rt", "success");
			} else {
				returnMap.put("rt", "fail");
			}
		}
		
		return returnMap;
	}
	
	/**
	 * Ajax를 통한 로그아웃 처리 - HO Xdm
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "MemberXdmSignOutProc")	
	public Map<String, Object> memberXdmSignOutProc(HttpSession httpSession) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		xdmSignOut(httpSession);
		returnMap.put("rt", "success");
		
		return returnMap;
	}
	
	////////////////////////////////////////////////////////////////
	
	/**
	 * 로그인 화면 이동 - Usr Xdm
	 * @return
	 */
	@RequestMapping(value = "MemberUsrSignIn")	
	public String memberUsrSignIn() throws Exception {				
		return path_user + "MemberUsrSignIn";
	}
	
	/**
	 * Ajax를 통한 로그인 처리 - WH Xdm
	 * @param memberDto
	 * @return
	 * @throws Exception
	 */
	@ResponseBody // Ajax 코드는 무조건 써준다.
	@RequestMapping(value = "MemberUsrSignInProc")
	public Map<String, Object> memberUsrSignInProc(MemberDto memberDto, HttpSession httpSession) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		MemberDto mDto = service.selectSignInMember(memberDto); // MyBatis에서 디비 검색 후 결과값이 없으면 NULL이 떨어짐
		
		if (mDto == null) { 
			returnMap.put("rt", "fail");
		} else {
			if (matchesBcrypt(memberDto.getuPwd(), mDto.getuPwd(), 10)) {
				usrSignIn(httpSession, mDto);
				returnMap.put("rt", "success");
			} else {
				returnMap.put("rt", "fail");
			}
		}
		
		return returnMap;
	}
	
	/**
	 * Ajax를 통한 로그아웃 처리 - WH Xdm
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "MemberUsrSignOutProc")	
	public Map<String, Object> memberUsrSignOutProc(HttpSession httpSession) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		usrSignOut(httpSession);
		returnMap.put("rt", "success");
		
		return returnMap;
	}

}
