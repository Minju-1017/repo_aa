
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
	
	@RequestMapping(value = "/productWHXdmList")
	public String productWHXdmList(@ModelAttribute("vo")ProductVo vo, Model model,HttpSession httpSession,MemberDto memberDto) {
		vo.setParamsPaging(productService.selectCount(vo));
		memberDto.setuSeq(String.valueOf(httpSession.getAttribute("sessSeqWHXdm")));
		model.addAttribute("list",productService.productList(vo));
		return "whxdm/product/productWHXdmList";
	}
	@RequestMapping(value = "/productHOXdmList")
	public String productHOXdmList(@ModelAttribute("vo")ProductVo vo, Model model,HttpSession httpSession,MemberDto memberDto) {
		vo.setParamsPaging(productService.selectCount(vo));
		memberDto.setuSeq(String.valueOf(httpSession.getAttribute("sessSeqHOXdm")));
		model.addAttribute("list",productService.productList(vo));
		return "hoxdm/product/productHOXdmList";
	}
	@RequestMapping(value = "/productHOXdmForm")
	public String productHOXdmForm(ProductDto productDto, Model model,HttpSession httpSession,MemberDto memberDto) {
		memberDto.setuSeq(String.valueOf(httpSession.getAttribute("sessSeqHOXdm")));
		if(productDto.getpSeq().equals("0")  || productDto.getpSeq().equals("")) {
			model.addAttribute("vList",productService.venderList(productDto));
			model.addAttribute("codeList",productService.codeList(productDto));
		}else {
			model.addAttribute("codeList",productService.codeList(productDto));
			model.addAttribute("item",productService.productOne(productDto));
			model.addAttribute("vList",productService.venderList(productDto));
		}
		
		return "hoxdm/product/productHOXdmForm";
	}
	@RequestMapping(value = "/productWHXdmForm")
	public String productWHXdmForm(ProductDto productDto, Model model,HttpSession httpSession,MemberDto memberDto) {
		memberDto.setuSeq(String.valueOf(httpSession.getAttribute("sessSeqWHXdm")));
		
			model.addAttribute("item",productService.productOne(productDto));
			model.addAttribute("vList",productService.venderList(productDto));
			model.addAttribute("codeList",productService.codeList(productDto));

		return "whxdm/product/productWHXdmForm";
	}
	@RequestMapping(value = "/productHOXdmInst")
	public String productHOXdmInst(ProductDto productDto) {
		productService.productInsert(productDto);
		return "redirect:/productHOXdmList";
	}
	@RequestMapping(value = "/productHOXdmUpdt")
	public String productHOXdmUpdt(ProductDto productDto) {
		productService.productUpdate(productDto);
		return "redirect:/productHOXdmList";
	}
	@RequestMapping(value = "/productHOXdmUele")
	public String productHOXdmUele(ProductDto productDto) {
		productService.productUelete(productDto);
		return "redirect:/productHOXdmList";
	}
	@RequestMapping(value = "/productWHXdmUpdt")
	public String productWHXdmUpdt(ProductDto productDto) {
		productService.productUpdate(productDto);
		return "redirect:/productWHXdmList";
	}
}
