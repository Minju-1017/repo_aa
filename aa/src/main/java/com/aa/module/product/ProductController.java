
package com.aa.module.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aa.module.member.MemberDto;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProductController {

	@Autowired
	ProductService productService;
	
	
	@RequestMapping(value = "/productXdmList")
	public String productXdmList(@ModelAttribute("vo")ProductVo vo, Model model,HttpSession httpSession,MemberDto memberDto) {
		vo.setParamsPaging(productService.selectCount(vo));
		memberDto.setuSeq(String.valueOf(httpSession.getAttribute("sessSeqXdm")));
		model.addAttribute("list",productService.productList(vo));
		return "xdm/product/productXdmList";
	}
	@RequestMapping(value = "/productXdmForm")
	public String productXdmForm(ProductDto productDto, Model model,HttpSession httpSession,MemberDto memberDto) {
		memberDto.setuSeq(String.valueOf(httpSession.getAttribute("sessSeqXdm")));
		if(productDto.getpSeq().equals("0")  || productDto.getpSeq().equals("")) {
			model.addAttribute("vList",productService.venderList(productDto));
			model.addAttribute("codeList",productService.codeList(productDto));
		}else {
			model.addAttribute("codeList",productService.codeList(productDto));
			model.addAttribute("item",productService.productOne(productDto));
			model.addAttribute("vList",productService.venderList(productDto));
		}
		
		return "xdm/product/productXdmForm";
	}
	@RequestMapping(value = "/productXdmInst")
	public String productXdmInst(ProductDto productDto) {
		productService.productInsert(productDto);
		return "redirect:/productXdmList";
	}
	@RequestMapping(value = "/productXdmUpdt")
	public String productXdmUpdt(ProductDto productDto) {
		productService.productUpdate(productDto);
		return "redirect:/productXdmList";
	}
	@RequestMapping(value = "/productXdmUele")
	public String productXdmUele(ProductDto productDto) {
		productService.productUelete(productDto);
		return "redirect:/productXdmList";
	}
}
