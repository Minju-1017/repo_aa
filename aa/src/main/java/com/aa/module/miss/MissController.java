package com.aa.module.miss;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aa.common.mail.MailService;
import com.aa.module.member.MemberDto;
import com.aa.module.member.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class MissController {
	
	@Autowired
	MissService service;
	
	@Autowired
	MailService mailService;
	
	@Autowired
	MemberService memberService;
	
	@Value("${kakao.api.key}")
    private String kakaoApiKey;
	
	@RequestMapping(value = "/missUsrList")
	public String missUsrList(@ModelAttribute("vo")MissVo vo,Model model) {
		vo.setParamsPaging(service.selectCount(vo));
		model.addAttribute("list",service.mbList(vo));
		return "usr/miss/MissUsrList";
	}
	@RequestMapping(value = "/missUsrView")
	public String missUsrView(@RequestParam(value = "fragmentType" ,required=false) String fragmentType,@ModelAttribute("vo")MissVo vo,Model model, MissDto dto,HttpServletRequest request,
			HttpSession httpSession) {
		if(vo.getMbSeq() == null || dto.getMbSeq() == null ){
			vo.setMbSeq(String.valueOf(httpSession.getAttribute("sessmbSeq")));
			dto.setMbSeq(String.valueOf(httpSession.getAttribute("sessmbSeq")));
		}
		vo.setParamsPaging(service.reviewCount(vo));
		model.addAttribute("one",service.mbOne(dto));
		model.addAttribute("list",service.mbList(vo));
		model.addAttribute("reviewList",service.reviewList(vo));
		model.addAttribute("reviewCount",service.reviewCount(vo));
		httpSession.setAttribute("sessmbSeq", dto.getMbSeq());
		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
	        if("review".equals(fragmentType)) {
	        	
	        	return "usr/miss/MissUsrView :: reviewFragment";
	        }
	    }
		return "usr/miss/MissUsrView";
	}
	@RequestMapping(value = "/missUsrForm")
	public String missUsrForm(HttpSession httpSession,HttpServletRequest request, MissDto dto,Model model) {
		dto.setUser_uSeq(String.valueOf(httpSession.getAttribute("sessSeqUsr")));
		model.addAttribute("uaList",service.uaList(dto));
		model.addAttribute("item",service.uaOne(dto));
		model.addAttribute("one",service.mbOne(dto));
		model.addAttribute("kakaoApiKey", kakaoApiKey);
		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
	        return "usr/miss/MissUsrForm :: uaFragment";
	    }
		return "usr/miss/MissUsrForm";
	}
	@RequestMapping(value = "/missUsrInst")
	@ResponseBody
	public Map<String,Object> missUsrInst(MissDto dto){
		Map<String,Object> result = new HashMap<String,Object>();
		int inst = service.mbInsert(dto);
		if(inst > 0 ) {
			result.put("rt", "success");
			
			// 실종 알림 메일 보내기(SMTP 이용) - 오래 걸리므로, 새로운 쓰레드에서 보낸다.
			new Thread() {
				public void run() {
					try {
						// 회원 전체 리스트
						List<MemberDto> memberDtoList = memberService.selectAllList();
						
						// 추가한 실종 신고 정보
						dto.setMbSeq(dto.getMbSeq());
						MissDto missDto = service.mbOne(dto);
						
						// 메일 보내기
						for (MemberDto memberDto : memberDtoList) {
							mailService.sendMailMissAlert(memberDto, missDto);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}.start();
		}else {
			result.put("rt", "fail");
		}
		return result;
	}
	@RequestMapping(value = "/missUsrUpdt")
	public String missUsrUpdt(MissDto dto) {
		service.mbUpdate(dto);
		return "redirect:/missUsrView";
	}
	@RequestMapping(value = "/missUsrUele")
	public String missusrUele(MissDto dto) {
		service.mbUelete(dto);
		return "redirect:/missUsrList";
	}
	@RequestMapping(value = "/reviewUsrInst")
	public String reviewUstInst(MissDto dto, HttpSession httpSession) {
		service.reviewInsert(dto);
		
		return "redirect:/missUsrView";
	}
	
}
