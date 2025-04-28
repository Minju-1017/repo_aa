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
@RequestMapping(value={"/hoxdm/member/", "/whxdm/member/"})
public class MemberController {

	private String path_hoAdmin = "hoxdm/member/";	// 본사(Head Office)
	private String path_whAdmin = "whxdm/member/";	// 창고(Warehouse)
	
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
	private void hoxdmSignIn(HttpSession httpSession, MemberDto memberDto) throws Exception {
		httpSession.setMaxInactiveInterval(Constants.SESSION_MINUTE);
		httpSession.setAttribute("sessSeqHOXdm", memberDto.getuSeq());
		httpSession.setAttribute("sessIdHOXdm", memberDto.getuId());
		httpSession.setAttribute("sessNameHOXdm", memberDto.getuName());
	}
	
	/**
	 * 로그아웃 세션 처리 - HO Xdm
	 * @param httpSession
	 */
	private void hoxdmSignOut(HttpSession httpSession) {
		httpSession.setAttribute("sessSeqHOXdm", null);
		httpSession.setAttribute("sessIdHOXdm", null);
		httpSession.setAttribute("sessNameHOXdm", null);
	}
	
	/**
	 * 로그인 세션 처리 - WH Xdm
	 * @param httpSession
	 * @param memberDto
	 * @throws Exception
	 */
	private void whxdmSignIn(HttpSession httpSession, MemberDto memberDto) throws Exception {
		httpSession.setMaxInactiveInterval(Constants.SESSION_MINUTE);
		httpSession.setAttribute("sessSeqWHXdm", memberDto.getuSeq());
		httpSession.setAttribute("sessIdWHXdm", memberDto.getuId());
		httpSession.setAttribute("sessNameWHXdm", memberDto.getuName());
	}
	
	/**
	 * 로그아웃 세션 처리 - WH Xdm
	 * @param httpSession
	 */
	private void whxdmSignOut(HttpSession httpSession) {
		httpSession.setAttribute("sessSeqWHXdm", null);
		httpSession.setAttribute("sessIdWHXdm", null);
		httpSession.setAttribute("sessNameWHXdm", null);
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
	@RequestMapping(value = "MemberHOXdmSignIn")	
	public String memberHOXdmSignIn() throws Exception {				
		return path_hoAdmin + "MemberHOXdmSignIn";
	}
	
	/**
	 * Ajax를 통한 로그인 처리 - HO Xdm
	 * @param memberDto
	 * @return
	 * @throws Exception
	 */
	@ResponseBody // Ajax 코드는 무조건 써준다.
	@RequestMapping(value = "MemberHOXdmSignInProc")
	public Map<String, Object> memberHOXdmSignInProc(MemberDto memberDto, HttpSession httpSession) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		MemberDto mDto = service.selectSignInMember(memberDto); // MyBatis에서 디비 검색 후 결과값이 없으면 NULL이 떨어짐
		
		if (mDto == null) { 
			returnMap.put("rt", "fail");
		} else {
			if (matchesBcrypt(memberDto.getuPwd(), mDto.getuPwd(), 10)) {
				hoxdmSignIn(httpSession, mDto);
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
	@RequestMapping(value = "MemberHOXdmSignOutProc")	
	public Map<String, Object> memberHOXdmSignOutProc(HttpSession httpSession) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		hoxdmSignOut(httpSession);
		returnMap.put("rt", "success");
		
		return returnMap;
	}
	
	////////////////////////////////////////////////////////////////
	
	/**
	 * 로그인 화면 이동 - WH Xdm
	 * @return
	 */
	@RequestMapping(value = "MemberWHXdmSignIn")	
	public String memberWHXdmSignIn() throws Exception {				
		return path_whAdmin + "MemberWHXdmSignIn";
	}
	
	/**
	 * Ajax를 통한 로그인 처리 - WH Xdm
	 * @param memberDto
	 * @return
	 * @throws Exception
	 */
	@ResponseBody // Ajax 코드는 무조건 써준다.
	@RequestMapping(value = "MemberWHXdmSignInProc")
	public Map<String, Object> memberWHXdmSignInProc(MemberDto memberDto, HttpSession httpSession) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		MemberDto mDto = service.selectSignInMember(memberDto); // MyBatis에서 디비 검색 후 결과값이 없으면 NULL이 떨어짐
		
		if (mDto == null) { 
			returnMap.put("rt", "fail");
		} else {
			if (matchesBcrypt(memberDto.getuPwd(), mDto.getuPwd(), 10)) {
				whxdmSignIn(httpSession, mDto);
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
	@RequestMapping(value = "MemberWHXdmSignOutProc")	
	public Map<String, Object> memberWHXdmSignOutProc(HttpSession httpSession) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		whxdmSignOut(httpSession);
		returnMap.put("rt", "success");
		
		return returnMap;
	}

}
