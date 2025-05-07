package com.aa.module;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;import com.aa.module.miss.MissDto;
import com.aa.module.miss.MissService;
import com.aa.module.miss.MissVo;

@Controller
public class IndexController {
	
	@Autowired
	MissService missService;
	
	private String path_admin = "xdm/";
	private String path_user = "usr/";
	
	@RequestMapping(value = "/xdm/index")	
	public String xdmIndex() {
		return path_admin + "index";
	}
	
	@RequestMapping(value = "/usr/index")	
	public String usrIndex(Model model,@ModelAttribute("vo")MissVo vo) {	
		List<MissDto> asdf = missService.mbList(vo);
		System.out.println("#################" + asdf.size());
		model.addAttribute("list",asdf);
		return path_user + "index";
	}
}
