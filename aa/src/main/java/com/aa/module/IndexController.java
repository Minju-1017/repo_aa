package com.aa.module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aa.module.product.ProductService;

@Controller
public class IndexController {
	
	@Autowired
	ProductService productService;
	
	private String path_admin = "xdm/";
	private String path_user = "usr/";
	
	@RequestMapping(value = "/xdm/index")	
	public String xdmIndex(Model model) {
		model.addAttribute("totalPrice",productService.totalPrice());
		model.addAttribute("totalOrder",productService.totalOrder());
		return path_admin + "index";
	}
	
	@RequestMapping(value = "/usr/index")	
	public String usrIndex() {	
		
		return path_user + "index";
	}
}
