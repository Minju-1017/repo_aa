package com.aa.module.miss;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MissController {
	
	@RequestMapping(value = "/missUsrList")
	public String missUsrList() {
		return "/usr/miss/MissUsrList";
	}
	@RequestMapping(value = "/missUsrView")
	public String missUsrView() {
		return "/usr/miss/MissUsrView";
	}
	@RequestMapping(value = "/missUsrForm")
	public String missUsrForm() {
		return "/usr/miss/MissUsrForm";
	}
}
