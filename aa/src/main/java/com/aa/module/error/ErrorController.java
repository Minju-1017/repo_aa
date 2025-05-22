package com.aa.module.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {
		
	
	@RequestMapping(value="/error400")
	public String error400() {
		return "error/400";
	}
}
