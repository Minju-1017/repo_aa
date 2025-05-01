package com.aa.module.miss;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class MissController {
	
	@Autowired
	MissService service;
	
	@RequestMapping(value = "/missUsrList")
	public String missUsrList() {
		return "/usr/miss/MissUsrList";
	}
	@RequestMapping(value = "/missUsrView")
	public String missUsrView() {
		return "/usr/miss/MissUsrView";
	}
	@RequestMapping(value = "/missUsrForm")
	public String missUsrForm(HttpSession httpSession,HttpServletRequest request, MissDto dto,Model model) {
		dto.setUser_uSeq(String.valueOf(httpSession.getAttribute("sessSeqUsr")));
		model.addAttribute("uaList",service.uaList(dto));
		model.addAttribute("item",service.uaOne(dto));
		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
	        return "usr/miss/MissUsrForm :: uaFragment";
	    }
		return "/usr/miss/MissUsrForm";
	}
	@RequestMapping(value = "/missUsrInst")
	@ResponseBody
	public Map<String,Object> missUsrInst(MissDto dto){
		Map<String,Object> result = new HashMap<String,Object>();
		int inst = service.mbInsert(dto);
		if(inst > 0 ) {
			result.put("rt", "success");
		}else {
			result.put("rt", "fail");
		}
		return result;
		
	}
}
