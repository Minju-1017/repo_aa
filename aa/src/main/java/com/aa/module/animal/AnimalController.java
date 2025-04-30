package com.aa.module.animal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aa.module.member.MemberDto;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(value={"/xdm/animal/", "/usr/animal/"})
public class AnimalController {
	
	private String path_user = "usr/animal/";	// User
	private String path_admin = "xdm/animal/";	// admin
	@Autowired
	AnimalService service;
	//애완동물리스트
	@RequestMapping(value = "AnimalInfoUsr")	
	public String animalInfoUsr(Model model, MemberDto memberDto,HttpSession httpSession)  {	
		memberDto.setuSeq(String.valueOf(httpSession.getAttribute("sessSeqUsr")));
		model.addAttribute("animalList", service.selectList(memberDto));
		
		return path_user + "AnimalInfoUsr";
	}
	
	@RequestMapping(value = "AnimalInfoUsrForm")	
	public String AnimalInfoUsrForm(Model model,AnimalDto animalDto)  {	
		
		model.addAttribute("item", service.selectOne(animalDto));
		return path_user + "AnimalInfoUsrForm";
	}
	
	@RequestMapping(value = "AnimalInfoUsrinsert")	
	public String AnimalInfoUsrinsert(AnimalDto animalDto)  {	
		
		service.insert(animalDto);
		return   "redirect:/usr/animal/AnimalInfoUsr";
	}
}
