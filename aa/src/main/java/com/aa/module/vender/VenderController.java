package com.aa.module.vender;

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

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(value="/hoxdm/vender/")
public class VenderController {
	
	private String path = "hoxdm/vender/";
	
	@Autowired
	VenderService service;
	
	/**
	 * 전체 데이터 읽어오기 - 페이징 기능 들어감
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "VenderHOXdmList")
	public String venderHOXdmList(Model model, @ModelAttribute("vo") VenderVo vo,
			HttpSession httpSession) throws Exception {

		// addAttribute 하기 전에 미리 실행되야함
		vo.setParamsPaging(service.selectOneCount(vo));
		
		if (vo.getTotalRows() > 0) {
			model.addAttribute("venderList", service.selectList(vo));
		}
		
		return path + "VenderHOXdmList";
	}
	
	/**
	 * 데이터 추가/수정 폼
	 * @return
	 */
	@RequestMapping(value = "VenderHOXdmForm")
	public String venderHOXdmForm(@ModelAttribute("vo") VenderVo vo,
			Model model, VenderDto venderDto) throws Exception {
		if (vo.getvSeq().equals("0") || vo.getvSeq().equals("")) {
			// insert mode
		} else {
			// update mode
			model.addAttribute("venderItem", service.selectOne(venderDto));
		}
		
		return path + "VenderHOXdmForm";
	}
	
	/**
	 * 입력한 데이터 추가하기
	 * @return redirect: 데이터 저장 후 돌아갈 주소(List)
	 */
	@RequestMapping(value = "VenderHOXdmInst")
	public String venderHOXdmInst(VenderDto venderDto) {
		service.insert(venderDto);
		
		return "redirect:VenderHOXdmList";
	}
	
	/**
	 * 입력한 데이터 수정하기
	 * @return redirect: 데이터 저장 후 돌아갈 주소(List)
	 */
	@RequestMapping(value = "VenderHOXdmUpdt")
	public String venderHOXdmUpdt(VenderDto venderDto) {
		service.update(venderDto);	

		return "redirect:VenderHOXdmList";
	}
	
	/**
	 * 데이터 삭제하기
	 * @return redirect: 데이터 삭제 후 돌아갈 주소(List)
	 */
	@RequestMapping(value = "VenderHOXdmDele")
	public String venderHOXdmDele(VenderDto venderDto) {
		service.delete(venderDto);	

		return "redirect:VenderHOXdmList";
	}
	
	/**
	 * 데이터 삭제 옵션 세팅 - update 이용
	 * @return redirect: 데이터 저장 후 돌아갈 주소(List)
	 */
	@RequestMapping(value = "VenderHOXdmUele")
	public String venderHOXdmUele(VenderDto venderDto) {
		service.uelete(venderDto);	

		return "redirect:VenderHOXdmList";
	}
	
	/**
	 * Ajax를 통한 여러건 데이터 삭제
	 * @param seqList
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "VenderListHOXdmDeleProc")
	public Map<String, Object> venderListHOXdmDeleProc(
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
	@RequestMapping(value = "VenderListHOXdmUeleProc")
	public Map<String, Object> venderListHOXdmUeleProc(
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
