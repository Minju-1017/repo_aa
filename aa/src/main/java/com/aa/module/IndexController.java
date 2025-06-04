package com.aa.module;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aa.module.miss.MissDto;
import com.aa.module.miss.MissService;
import com.aa.module.miss.MissVo;

@Controller
public class IndexController {
	
	@Autowired
	MissService missService;
	
	@Value("${kakao.api.key}")
    private String kakaoApiKey;
	
	private String path_admin = "xdm/";
	private String path_user = "usr/";
	
	@RequestMapping(value = "/xdm/index")	
	public String xdmIndex() {
		return path_admin + "index";
	}
	
	@RequestMapping(value = "/usr/index")	
	public String usrIndex(Model model,@ModelAttribute("vo")MissVo vo) {	
		List<MissDto> asdf = missService.mbList(vo);
		
		if(!asdf.isEmpty()) {
			asdf.get(0).setUp("1");
		}
		
		model.addAttribute("list",asdf);
		model.addAttribute("kakaoApiKey", kakaoApiKey);
		
		return path_user + "index";
	}
}
