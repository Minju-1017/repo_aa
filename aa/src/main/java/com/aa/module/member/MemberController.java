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
@RequestMapping(value={"/xdm/member/", "/usr/member/"})
public class MemberController {

	private String path_admin = "xdm/member/";	// Admin
	private String path_user = "usr/member/";	// User
	
	@Autowired
	MemberService service;
	
	@Autowired
	CodeService codeService;
	
	////////////////////////////////////////////////////////////////
	
	/**
	 * 로그인 세션 처리 - User
	 * @param httpSession
	 * @param memberDto
	 * @throws Exception
	 */
	private void usrSignIn(HttpSession httpSession, MemberDto memberDto) throws Exception {
		httpSession.setMaxInactiveInterval(Constants.SESSION_MINUTE);
		httpSession.setAttribute("sessSeqUsr", memberDto.getuSeq());
		httpSession.setAttribute("sessIdUsr", memberDto.getuId());
		httpSession.setAttribute("sessNicknameUsr", memberDto.getuNickname());
	}
	
	/**
	 * 로그아웃 세션 처리 - User
	 * @param httpSession
	 */
	private void usrSignOut(HttpSession httpSession) {
		httpSession.setAttribute("sessSeqUsr", null);
		httpSession.setAttribute("sessIdUsr", null);
		httpSession.setAttribute("sessNicknameUsr", null);
	}
	
	/**
	 * 로그인 세션 처리 - Xdm
	 * @param httpSession
	 * @param memberDto
	 * @throws Exception
	 */
	private void xdmSignIn(HttpSession httpSession, MemberDto memberDto) throws Exception {
		httpSession.setMaxInactiveInterval(Constants.SESSION_MINUTE);
		httpSession.setAttribute("sessSeqXdm", memberDto.getuSeq());
		httpSession.setAttribute("sessIdXdm", memberDto.getuId());
		httpSession.setAttribute("sessNicknameXdm", memberDto.getuNickname());
	}
	
	/**
	 * 로그아웃 세션 처리 - Xdm
	 * @param httpSession
	 */
	private void xdmSignOut(HttpSession httpSession) {
		httpSession.setAttribute("sessSeqXdm", null);
		httpSession.setAttribute("sessIdXdm", null);
		httpSession.setAttribute("sessNicknameXdm", null);
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
	 * 회원가입 화면 이동 - User
	 * @return
	 */
	@RequestMapping(value = "MemberUsrSignUpForm")	
	public String memberUsrSignUpForm() throws Exception {				
		return path_user + "MemberUsrSignUpForm";
	}
	
	/**
	 * Ajax를 통한 회원가입 Id 체크 - User
	 * @param memberDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "MemberUsrInstIdCheckProc")
	public Map<String, Object> memberUsrInstIdCheckProc(MemberDto memberDto) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		int cntId = service.insertCheckId(memberDto);
		
		if (cntId == 0) {
			returnMap.put("rt", "success");
		} else {
			returnMap.put("rt", "fail_id");
		}
		
		return returnMap;
	}
	
	/**
	 * Ajax를 통한 회원가입 - User
	 * @param memberDto
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "MemberUsrInstProc")
	public Map<String, Object> memberUsrInstProc(MemberDto memberDto) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		int cntId = service.insertCheckId(memberDto);
		
		if (cntId == 0) {
			memberDto.setuPwd(encodeBcrypt(memberDto.getuPwd(), 10)); // 패스워드 암호화
			int cntSuccess = service.insert(memberDto);
			
			if (cntSuccess == 1) {
				returnMap.put("rt", "success");
			} else {
				returnMap.put("rt", "fail");
			}
		} else {
			returnMap.put("rt", "fail_id");
		}
		
		return returnMap;
	}
	
	/**
	 * 로그인 화면 이동 - User
	 * @return
	 */
	@RequestMapping(value = "MemberUsrSignIn")	
	public String memberUsrSignIn() throws Exception {				
		return path_user + "MemberUsrSignIn";
	}
	
	/**
	 * Ajax를 통한 로그인 처리 - User
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
	 * 비밀번호 재설정 화면 이동 - User
	 * @return
	 */
	@RequestMapping(value = "MemberUsrSignInForgotPwdForm")	
	public String memberUsrSignInForgotPwdForm() throws Exception {	
		return path_user + "MemberUsrSignInForgotPwdForm";
	}
	
	/**
	 * Ajax를 통한 비밀번호 재설정 처리 - User
	 * @param memberDto
	 * @return
	 * @throws Exception
	 */
	@ResponseBody // Ajax 코드는 무조건 써준다.
	@RequestMapping(value = "MemberUsrSignInForgotPwdProc")
	public Map<String, Object> memberUsrSignInForgotPwdProc(MemberDto memberDto) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		memberDto.setuPwd(encodeBcrypt(memberDto.getuPwd(), 10)); // 패스워드 암호화
		int cntSuccess = service.updateForgotPwd(memberDto);
		
		if (cntSuccess == 1) { 
			returnMap.put("rt", "success");
		} else {
			returnMap.put("rt", "fail");
		}
		
		return returnMap;
	}
	
	/**
	 * Ajax를 통한 로그아웃 처리 - User
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
	
	/**
	 * 회원 기본 정보 수정 화면 - User
	 * @param model
	 * @param memberDto
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "MemberUsrAccountForm")
	public String memberUsrAccountForm(Model model, MemberDto memberDto, HttpSession httpSession) {
		if (httpSession.getAttribute("sessSeqUsr") == null) {
			usrSignOut(httpSession);
			return "redirect:MemberUsrSignIn";
		}
		
		memberDto.setuSeq(String.valueOf(httpSession.getAttribute("sessSeqUsr")));
		model.addAttribute("memberItem", service.selectOne(memberDto));
		
		return path_user + "MemberUsrAccountForm";
	}
	
	/**
	 * Ajax를 통한 회원 기본 정보 수정 - User
	 * @param memberDto
	 * @param httpSession
	 * @return 
	 * @throws Exception
	 */
	@ResponseBody // Ajax 코드는 무조건 써준다.
	@RequestMapping(value = "MemberUsrUpdt")
	public Map<String, Object> memberUsrUpdt(MemberDto memberDto, HttpSession httpSession) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		int successCnt = service.update(memberDto);
		
		if (successCnt == 1) {
			if (httpSession.getAttribute("sessSeqUsr") != null
					 && String.valueOf(httpSession.getAttribute("sessSeqUsr")).equals(memberDto.getuSeq())) {
				httpSession.setAttribute("sessNicknameUsr", memberDto.getuNickname());
			}
			
			returnMap.put("rt", "success");
		} else {
			returnMap.put("rt", "fail");
		}

		return returnMap;
	}
	
	/**
	 * 회원 비밀번호 수정 화면 - User
	 * @param model
	 * @param memberDto
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "MemberUsrAccountPwdForm")
	public String memberUsrAccountPwdForm(Model model, MemberDto memberDto, HttpSession httpSession) {
		if (httpSession.getAttribute("sessSeqUsr") == null) {
			usrSignOut(httpSession);
			return "redirect:MemberUsrSignIn";
		}
		
		return path_user + "MemberUsrAccountPwdForm";
	}
	
	/**
	 * Ajax를 통한 회원 비밀번호 수정 처리 - User
	 * @param memberDto
	 * @return
	 * @throws Exception
	 */
	@ResponseBody // Ajax 코드는 무조건 써준다.
	@RequestMapping(value = "MemberUsrPwdUpdtProc")
	public Map<String, Object> memberUsrPwdUpdtProc(
			MemberDto memberDto, @RequestParam(value="uPwdNew") String uPwdNew) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();

		if (uPwdNew == null || (uPwdNew != null && uPwdNew.equals(""))) {
			returnMap.put("rt", "fail");
		} else {
			MemberDto mDto = service.updatePwdCheck(memberDto);
			
			if (mDto != null) {
				if (matchesBcrypt(memberDto.getuPwd(), mDto.getuPwd(), 10)) {
					memberDto.setuPwd(encodeBcrypt(uPwdNew, 10)); // 패스워드 암호화
					int successCnt = service.updatePwd(memberDto);
					
					if (successCnt == 1) {
						returnMap.put("rt", "success");
					} else {
						returnMap.put("rt", "fail");
					}
				} else {
					returnMap.put("rt", "fail");
				}
			} else {
				returnMap.put("rt", "fail");
			}
		}

		return returnMap;
	}
	
	/**
	 * 회원 탈퇴 화면 - User
	 * @param model
	 * @param memberDto
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "MemberUsrWithdrawal")
	public String memberUsrWithdrawal(Model model, MemberDto memberDto, HttpSession httpSession) {
		if (httpSession.getAttribute("sessSeqUsr") == null) {
			usrSignOut(httpSession);
			return "redirect:MemberUsrSignIn";
		}
		
		return path_user + "MemberUsrWithdrawal";
	}
	
	/**
	 * Ajax를 통한 회원 탈퇴 처리 - User(update 이용)
	 * @param memberDto
	 * @param httpSession
	 * @return
	 */
	@ResponseBody // Ajax 코드는 무조건 써준다.
	@RequestMapping(value = "MemberUsrUeleProc")
	public Map<String, Object> memberUsrUeleProc(MemberDto memberDto, HttpSession httpSession) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		int successCnt = service.uelete(memberDto);	
		
		if (successCnt == 1) {
			returnMap.put("rt", "success");
		} else {
			returnMap.put("rt", "fail");
		}

		return returnMap;
	}
	
////////////////////////////////////////////////////////////////
	
	/**
	 * 로그인 화면 이동 - Admin
	 * @return
	 */
	@RequestMapping(value = "MemberXdmSignIn")	
	public String memberXdmSignIn() throws Exception {				
		return path_admin + "MemberXdmSignIn";
	}
	
	/**
	 * Ajax를 통한 로그인 처리 - Admin
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
	 * Ajax를 통한 로그아웃 처리 - Admin
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
	
	/**
	 * 회원관리 - 전체 데이터 읽어오기(페이징 기능 들어감)
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "MemberXdmList")
	public String memberXdmList(Model model, @ModelAttribute("vo") MemberVo vo,
			HttpSession httpSession) throws Exception {
		// addAttribute 하기 전에 미리 실행되야함
		vo.setParamsPaging(service.selectOneCount(vo));

		if (vo.getTotalRows() > 0) {
			model.addAttribute("memberList", service.selectList(vo));
		}
		
		return path_admin + "MemberXdmList";
	}
	
	/**
	 * 회원관리 - 데이터 추가/수정 폼 - Admin
	 * @return
	 */
	@RequestMapping(value = "MemberXdmForm")
	public String memberXdmForm(@ModelAttribute("vo") MemberVo vo,
			Model model, MemberDto memberDto) throws Exception {
		if (vo.getuSeq().equals("0") || vo.getuSeq().equals("")) {
			// insert mode
		} else {
			// update mode	
			model.addAttribute("memberItem", service.selectOne(memberDto));
		}
		
		return path_admin + "MemberXdmForm";
	}
	
	/**
	 * 회원관리 - 입력한 데이터 수정하기 - Admin
	 * @return redirect: 데이터 저장 후 돌아갈 주소(List)
	 */
	@RequestMapping(value = "MemberXdmUpdt")
	public String memberXdmUpdt(MemberDto memberDto, HttpSession httpSession) throws Exception {
		int successCnt = service.update(memberDto);
		
		if (successCnt == 1
				 && httpSession.getAttribute("sessSeqXdm") != null
				 && String.valueOf(httpSession.getAttribute("sessSeqXdm")).equals(memberDto.getuSeq())) {
			httpSession.setAttribute("sessNicknameXdm", memberDto.getuNickname());
		}

		return "redirect:MemberXdmList";
	}
	
	/**
	 * 회원관리 - 데이터 삭제하기 - Admin
	 * @return redirect: 데이터 삭제 후 돌아갈 주소(List)
	 */
	@RequestMapping(value = "MemberXdmDele")
	public String memberXdmDele(MemberDto memberDto) {
		service.delete(memberDto);	

		return "redirect:MemberXdmList";
	}
	
	/**
	 * 회원관리 - 데이터 삭제 옵션 세팅(update 이용) - Admin
	 * @return redirect: 데이터 저장 후 돌아갈 주소(List)
	 */
	@RequestMapping(value = "MemberXdmUele")
	public String memberXdmUele(MemberDto memberDto) {
		service.uelete(memberDto);	

		return "redirect:MemberXdmList";
	}
	
	/**
	 * Ajax를 통한 여러건 데이터 삭제 - Admin
	 * @param seqList
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "MemberListXdmDeleProc")
	public Map<String, Object> memberListXdmDeleProc(
			@RequestParam(value="chbox") List<String> seqList) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		if (seqList == null || (seqList != null && seqList.size() == 0)) {
			returnMap.put("rt", "fail");
		} else {
			int successCnt = service.listDelete(seqList);
			
			if (successCnt > 0) {
				returnMap.put("rt", "success");
			} else {
				returnMap.put("rt", "fail");
			}
		}

		return returnMap;
	}
	
	/**
	 * Ajax를 통한 여러건 데이터 삭제 옵션 세팅 - update 이용 - Admin
	 * @param seqList
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "MemberListXdmUeleProc")
	public Map<String, Object> memberListXdmUeleProc(
			@RequestParam(value="chbox") List<String> seqList) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		if (seqList == null || (seqList != null && seqList.size() == 0)) {
			returnMap.put("rt", "fail");
		} else {
			int successCnt = service.listUelete(seqList);
			
			if (successCnt > 0) {
				returnMap.put("rt", "success");
			} else {
				returnMap.put("rt", "fail");
			}
		}

		return returnMap;
	}
	
}
