package com.aa.module.miss;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MissController {
	
	@RequestMapping(value = "/missUsrList")
	public String missUsrList() {
		return "/usr/miss/MissUsrList";
	}
}
