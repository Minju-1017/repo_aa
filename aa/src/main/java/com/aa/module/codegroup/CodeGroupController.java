package com.aa.module.codegroup;

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

import com.aa.common.AAUtil;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(value="/hoxdm/codegroup/")
public class CodeGroupController {
	
	private String path = "hoxdm/codegroup/";
	
	@Autowired
	CodeGroupService service;
	
	/**
	 * 전체 데이터 읽어오기 - 페이징 기능 들어감
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "CodeGroupHOXdmList")
	public String codeGroupHOXdmList(Model model, @ModelAttribute("vo") CodeGroupVo vo,
			HttpSession httpSession) throws Exception {
		// addAttribute 하기 전에 미리 실행되야함
		vo.setParamsPaging(service.selectOneCount(vo));
		
		if (vo.getTotalRows() > 0) {
			model.addAttribute("codeGroupList", service.selectList(vo));
		}
		
		return path + "CodeGroupHOXdmList";
	}
	
	/**
	 * 데이터 추가/수정 폼
	 * @return
	 */
	@RequestMapping(value = "CodeGroupHOXdmForm")
	public String codeGroupHOXdmForm(@ModelAttribute("vo") CodeGroupVo vo,
			Model model, CodeGroupDto codeGroupDto) throws Exception {
		if (vo.getCgSeq().equals("0") || vo.getCgSeq().equals("")) {
			// insert mode
		} else {
			// update mode
			model.addAttribute("codeGroupItem", service.selectOne(codeGroupDto));
		}
		
		return path + "CodeGroupHOXdmForm";
	}
	
	/**
	 * 입력한 데이터 추가하기
	 * @return redirect: 데이터 저장 후 돌아갈 주소(List)
	 */
	@RequestMapping(value = "CodeGroupHOXdmInst")
	public String codeGroupHOXdmInst(CodeGroupDto codeGroupDto) {
		service.insert(codeGroupDto);
		
		return "redirect:CodeGroupHOXdmList";
	}
	
	/**
	 * 입력한 데이터 수정하기
	 * @return redirect: 데이터 저장 후 돌아갈 주소(List)
	 */
	@RequestMapping(value = "CodeGroupHOXdmUpdt")
	public String codeGroupHOXdmUpdt(CodeGroupDto codeGroupDto) {
		service.update(codeGroupDto);	

		return "redirect:CodeGroupHOXdmList";
	}
	
	/**
	 * 데이터 삭제하기
	 * @return redirect: 데이터 삭제 후 돌아갈 주소(List)
	 */
	@RequestMapping(value = "CodeGroupHOXdmDele")
	public String codeGroupHOXdmDele(CodeGroupDto codeGroupDto) {
		service.delete(codeGroupDto);	

		return "redirect:CodeGroupHOXdmList";
	}
	
	/**
	 * 데이터 삭제 옵션 세팅 - update 이용
	 * @return redirect: 데이터 저장 후 돌아갈 주소(List)
	 */
	@RequestMapping(value = "CodeGroupHOXdmUele")
	public String codeGroupHOXdmUele(CodeGroupDto codeGroupDto) {
		service.uelete(codeGroupDto);	

		return "redirect:CodeGroupHOXdmList";
	}
	
	/**
	 * Ajax를 통한 여러건 데이터 삭제
	 * @param seqList
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "CodeGroupListHOXdmDeleProc")
	public Map<String, Object> codeGroupListHOXdmDeleProc(
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
	@RequestMapping(value = "CodeGroupListHOXdmUeleProc")
	public Map<String, Object> codeGroupListHOXdmUeleProc(
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
