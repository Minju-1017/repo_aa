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
	
	private String path_hoAdmin = "hoxdm/";
	private String path_whAdmin = "whxdm/";
	
	@RequestMapping(value = "/hoxdm/index")	
	public String hoxdmIndex(Model model) {
		model.addAttribute("totalPrice",productService.totalPrice());
		model.addAttribute("totalOrder",productService.totalOrder());
		return path_hoAdmin + "index";
	}
	
	@RequestMapping(value = "/whxdm/index")	
	public String whxdmIndex(Model model) {	
		model.addAttribute("totalPrice",productService.totalPrice());
		model.addAttribute("totalOrder",productService.totalOrder());
		return path_whAdmin + "index";
	}
}
