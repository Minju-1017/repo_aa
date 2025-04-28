package com.aa.module.code;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aa.Constants;
import com.aa.module.codegroup.CodeGroupService;
import com.aa.module.member.MemberDto;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(value="/hoxdm/code/")
public class CodeController {
	
	private String path = "hoxdm/code/";
	
	@Autowired
	CodeService service;
	
	@Autowired
	CodeGroupService codeGroupService;
	
	/**
	 * 전체 데이터 읽어오기 - 페이징 기능 들어감
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "CodeHOXdmList")
	public String codeHOXdmList(Model model, @ModelAttribute("vo") CodeVo vo,
			HttpSession httpSession) throws Exception {
		// addAttribute 하기 전에 미리 실행되야함
		vo.setParamsPaging(service.selectOneCount(vo));
		
		if (vo.getTotalRows() > 0) {
			model.addAttribute("codeList", service.selectList(vo));
		}
		
		return path + "CodeHOXdmList";
	}
	
	/**
	 * 데이터 추가/수정 폼
	 * @return
	 */
	@RequestMapping(value = "CodeHOXdmForm")
	public String codeHOXdmForm(@ModelAttribute("vo") CodeVo vo, Model model, CodeDto codeDto) 
			throws Exception {
		model.addAttribute("codeGroupList", codeGroupService.selectListWithoutPaging());
		
		if (vo.getcSeq().equals("0") || vo.getcSeq().equals("")) {
			// insert mode
		} else {
			// update mode
			model.addAttribute("codeItem", service.selectOne(codeDto));
		}
		
		return path + "CodeHOXdmForm";
	}
	
	/**
	 * 입력한 데이터 추가하기
	 * @return redirect: 데이터 저장 후 돌아갈 주소(List)
	 */
	@RequestMapping(value = "CodeHOXdmInst")
	public String codeHOXdmInst(CodeDto codeDto) {
		service.insert(codeDto);
		
		return "redirect:CodeHOXdmList";
	}
	
	/**
	 * 입력한 데이터 수정하기
	 * @return redirect: 데이터 저장 후 돌아갈 주소(List)
	 */
	@RequestMapping(value = "CodeHOXdmUpdt")
	public String codeHOXdmUpdt(CodeDto codeDto) {
		service.update(codeDto);	

		return "redirect:CodeHOXdmList";
	}
	
	/**
	 * 데이터 삭제하기
	 * @return redirect: 데이터 삭제 후 돌아갈 주소(List)
	 */
	@RequestMapping(value = "CodeHOXdmDele")
	public String codeHOXdmDele(CodeDto codeDto) {
		System.out.println(codeDto.getcSeq());
		service.delete(codeDto);	

		return "redirect:CodeHOXdmList";
	}
	
	/**
	 * 데이터 삭제하기
	 * @return redirect: 데이터 삭제 후 돌아갈 주소(List)
	 */
	@RequestMapping(value = "CodeHOXdmUele")
	public String codeHOXdmUele(CodeDto codeDto) {
		System.out.println(codeDto.getcSeq());
		service.uelete(codeDto);	

		return "redirect:CodeHOXdmList";
	}
	
	/**
	 * Ajax를 통한 여러건 데이터 삭제
	 * @param seqList
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "CodeListHOXdmDeleProc")
	public Map<String, Object> codeListHOXdmDeleProc(
			@RequestParam(value="chbox") List<String> seqList) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		if (seqList == null || (seqList != null && seqList.size() == 0)) {
			returnMap.put("rt", "fail");
		} else {
			int successCnt = service.listDelete(seqList);
			
			if (successCnt > 0) {
				returnMap.put("rt", "success");
			} else {
				returnMap.put("rt", "fail");
			}
		}

		return returnMap;
	}
	
	/**
	 * Ajax를 통한 여러건 데이터 삭제 옵션 세팅 - update 이용
	 * @param seqList
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "CodeListHOXdmUeleProc")
	public Map<String, Object> codeListHOXdmUeleProc(
			@RequestParam(value="chbox") List<String> seqList) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		if (seqList == null || (seqList != null && seqList.size() == 0)) {
			returnMap.put("rt", "fail");
		} else {
			int successCnt = service.listUelete(seqList);
			
			if (successCnt > 0) {
				returnMap.put("rt", "success");
			} else {
				returnMap.put("rt", "fail");
			}
		}

		return returnMap;
	}

}
