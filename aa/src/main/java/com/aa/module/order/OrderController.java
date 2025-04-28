package com.aa.module.order;

import java.util.ArrayList;
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

@Controller
public class OrderController {
	
	private String path = "hoxdm/order/";
	
	@Autowired //인터페이스
	OrderService service;
	
	//유저
	@RequestMapping(value = "/hoxdm/order/UserOrderHOXdmList")
	public String UserOrderHOXdmList(Model model,@ModelAttribute("vo") OrderVo vo) {
		
		vo.setParamsPaging(service.selectOneCount(vo));
		
		if (vo.getTotalRows() > 0) {
			model.addAttribute("orderList", service.selectList(vo));
		}
		
		return path + "UserOrderHOXdmList"; 
	}
	
	@RequestMapping(value = "/hoxdm/order/UserOrderHOXdmForm")
	public String UserOrderHOXdmForm(@ModelAttribute("vo") OrderVo vo,
			Model model, OrderDto orderDto) throws Exception {
		
		if (vo.getoSeq().equals("0") || vo.getoSeq().equals("")) {
			// insert mode
		} else {
			List<OrderDto> dtoList = service.selectOne(orderDto); // 주문한 상품이 여러개일 수 있으므로, List로 읽는다
			OrderDto dto = new OrderDto();
			dto = dtoList.get(0);
			model.addAttribute("orderItem", dto);
			model.addAttribute("orderList", dtoList);
		}
		return path + "UserOrderHOXdmForm";
	}
	
	@RequestMapping(value = "/hoxdm/order/UserOrderProductHOXdmForm")
	public String UserOrderProductHOXdmForm(Model model) {
		model.addAttribute("productList",service.productList());
		return "hoxdm/order/UserOrderProductHOXdmForm";
	}
	
	@RequestMapping(value = "/hoxdm/order/UserOrderProductHOXdmInst")
	public String UserOrderProductHOXdmInst(OrderDto orderDto) {
		service.ordersInsert(orderDto);
		service.ordersProductInsert(orderDto);
		return "redirect:/hoxdm/order/UserOrderHOXdmList";
	}
	
	@ResponseBody
	@RequestMapping(value = "/hoxdm/order/UserOrderHOXdmUpdt")
	public Map<String, Object> UserOrderHOXdmUpdt(OrderDto orderDto, 
			@RequestParam(value="opSeqList") List<String> opSeqList,
			@RequestParam(value="opStateCdList") List<String> opStateCdList) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		if (orderDto == null || opSeqList == null || opStateCdList == null) {
			returnMap.put("rt", "fail");
		} else {
			int successCnt = service.update(orderDto);
			
			if (successCnt > 0) {
				if (opSeqList.size() > 0 && opStateCdList.size() > 0) {
					int opSuccessCnt = 0;
					
					for (int i = 0; i < opSeqList.size(); i++) {
						OrderDto dto = new OrderDto();
						
						dto.setOpSeq(opSeqList.get(i));
						dto.setOpStateCd(opStateCdList.get(i));
						
						opSuccessCnt = opSuccessCnt + service.updateOPList(dto);
					}
					
					if (opSuccessCnt > 0) {
						returnMap.put("rt", "success");	
					} else {
						returnMap.put("rt", "fail");
					}
				} else {
					returnMap.put("rt", "success");	// 수정할 데이터 없으므로 바로 성공
				}
			} else {
				returnMap.put("rt", "fail");
			}
		}

		return returnMap;
	}
	
	//거래처
	@RequestMapping(value = "/hoxdm/order/ClientOrderHOXdmList")
	public String ClientOrderHOXdmList(Model model,@ModelAttribute("vo") OrderVo vo) {
		
		vo.setParamsPaging(service.selectTwoCount(vo));
		
		if (vo.getTotalRows() > 0) {
			model.addAttribute("orderList", service.selectTwo(vo));
		}
		
		return path + "ClientOrderHOXdmList"; 
		}
	

	@RequestMapping(value = "/hoxdm/order/ClientOrderHOXdmInst")
	public String ClientOrderHOXdmInst(OrderDto orderDto) {
		
		service.insert(orderDto);
		return path + "ClientOrderHOXdmInst";
	}
	
	@RequestMapping(value = "/hoxdm/order/WarehouseOrderHOXdmList")
	public String WraehouseOrderHOXdmList(Model model,@ModelAttribute("vo") OrderVo vo) {
		
		vo.setParamsPaging(service.selectThreeCount(vo));
		
		if (vo.getTotalRows() > 0) {
			model.addAttribute("orderList", service.selectThree(vo));
		}
		
		return path + "WarehouseOrderHOXdmList"; 
	}
	
	//입고관리
	@RequestMapping(value = "/whxdm/whorder/InOrderWHXdmList")
	public String InOrderWHXdmList(Model model,@ModelAttribute("vo") OrderVo vo) {
		
		vo.setParamsPaging(service.selectFourCount(vo));
		
		if (vo.getTotalRows() > 0) {
			model.addAttribute("orderList", service.selectFour(vo));
		}
		
		return "whxdm/whorder/InOrderWHXdmList"; 
	}
	
	//입고관리 폼
	@RequestMapping(value = "/whxdm/whorder/InOrderWHXdmForm")
	public String InOrderWHXdmForm(@ModelAttribute("vo") OrderVo vo,
			Model model, OrderDto orderDto) throws Exception {
		
		if (vo.getoSeq().equals("0") || vo.getoSeq().equals("")) {
			// insert mode
		} else {
			List<OrderDto> dtoList = service.selectOne(orderDto); // 주문한 상품이 여러개일 수 있으므로, List로 읽는다
			OrderDto dto = new OrderDto();
			dto = dtoList.get(0);
			model.addAttribute("orderItem", dto);
			model.addAttribute("orderList", dtoList);
		}
		return "whxdm/whorder/InOrderWHXdmForm";
	}
	
	//입고업데이트
	@ResponseBody
	@RequestMapping(value = "/whxdm/whorder/InOrderWHXdmUpdt")
	public Map<String, Object> InOrderWHXdmUpdt(OrderDto orderDto, 
			@RequestParam(value="opSeqList") List<String> opSeqList,
			@RequestParam(value="opStateCdList") List<String> opStateCdList,
			@RequestParam(value="pSeqList") List<String> pSeqList,
			@RequestParam(value="stockList") List<String> stockList
			) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		if (orderDto == null || opSeqList == null || opStateCdList == null) {
			returnMap.put("rt", "fail");
		} else {
			int successCnt = service.update(orderDto);
			
			if (successCnt > 0) {
				if (opSeqList.size() > 0 && opStateCdList.size() > 0) {
					int opSuccessCnt = 0;
					service.updateState(orderDto);
					for (int i = 0; i < opSeqList.size(); i++) {
						OrderDto dto = new OrderDto();
						
						dto.setOpSeq(opSeqList.get(i));
						dto.setOpStateCd(opStateCdList.get(i));
						
						opSuccessCnt = opSuccessCnt + service.updateOPList(dto);
					}
					for(int i=0; i< pSeqList.size(); i++) {
						OrderDto dto = new OrderDto();
						
						dto.setpSeq(pSeqList.get(i));
						dto.setpStock(stockList.get(i));
						service.updateStock(dto);
					}
					
					if (opSuccessCnt > 0) {
						returnMap.put("rt", "success");	
					} else {
						returnMap.put("rt", "fail");
					}
				} else {
					returnMap.put("rt", "success");	// 수정할 데이터 없으므로 바로 성공
				}
			} else {
				returnMap.put("rt", "fail");
			}
		}

		return returnMap;
	}
	
	//출고
	@RequestMapping(value = "/whxdm/whorder/OutOrderWHXdmList")
	public String OutOrderWHXdmList(Model model,@ModelAttribute("vo") OrderVo vo) {
		
		vo.setParamsPaging(service.selectFiveCount(vo));
		
		if (vo.getTotalRows() > 0) {
			model.addAttribute("orderList", service.selectFive(vo));
		}
		
		return "whxdm/whorder/OutOrderWHXdmList"; 
	}
	
	//출고 폼
	@RequestMapping(value = "/whxdm/whorder/OutOrderWHXdmForm")
	public String OutOrderWHXdmForm(@ModelAttribute("vo") OrderVo vo,
			Model model, OrderDto orderDto) throws Exception {
		
		if (vo.getoSeq().equals("0") || vo.getoSeq().equals("")) {
			// insert mode
		} else {
			List<OrderDto> dtoList = service.selectOne(orderDto); // 주문한 상품이 여러개일 수 있으므로, List로 읽는다
			OrderDto dto = new OrderDto();
			dto = dtoList.get(0);
			model.addAttribute("orderItem", dto);
			model.addAttribute("orderList", dtoList);
		}
		return "whxdm/whorder/OutOrderWHXdmForm";
	}
	
	//출고 업데이트
	@ResponseBody
	@RequestMapping(value = "/whxdm/whorder/OutOrderWHXdmUpdt")
	public Map<String, Object> OutOrderWHXdmUpdt(OrderDto orderDto, 
			@RequestParam(value="opSeqList") List<String> opSeqList,
			@RequestParam(value="opStateCdList") List<String> opStateCdList
			
			) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		if (orderDto == null || opSeqList == null || opStateCdList == null) {
			returnMap.put("rt", "fail");
		} else {
			int deliveryNoCnt = service.updateCheckDeliveryNo(orderDto);
			
			if (deliveryNoCnt > 0) {
				returnMap.put("rt", "fail_deliveryNo");
			} else {
				int successCnt = service.update(orderDto);
				
				if (successCnt > 0) {
					if (opSeqList.size() > 0 && opStateCdList.size() > 0) {
						int opSuccessCnt = 0;
						
						for (int i = 0; i < opSeqList.size(); i++) {
							OrderDto dto = new OrderDto();
							
							dto.setOpSeq(opSeqList.get(i));
							dto.setOpStateCd(opStateCdList.get(i));
							
							opSuccessCnt = opSuccessCnt + service.updateOPList(dto);
						}
						
						
						if (opSuccessCnt > 0) {
							returnMap.put("rt", "success");	
						} else {
							returnMap.put("rt", "fail");
						}
					} else {
						returnMap.put("rt", "success");	// 수정할 데이터 없으므로 바로 성공
					}
				} else {
					returnMap.put("rt", "fail");
				}
			}
		}

		return returnMap;
	}
	
	@RequestMapping(value = "/whxdm/whorder/DeliveryOrderWHXdmList")
	public String DeliveryOrderWHXdmList(Model model,@ModelAttribute("vo") OrderVo vo) {
		
		vo.setParamsPaging(service.selectSixCount(vo));
		
		if (vo.getTotalRows() > 0) {
			model.addAttribute("orderList", service.selectSix(vo));
		}
		
		return "whxdm/whorder/DeliveryOrderWHXdmList"; 
	}
	
	@RequestMapping(value = "/hoxdm/order/DeliOrderHOXdmList")
	public String DeliOrderHOXdmList(Model model,@ModelAttribute("vo") OrderVo vo) {
		
		vo.setParamsPaging(service.selectSixCount(vo));
		
		if (vo.getTotalRows() > 0) {
			model.addAttribute("orderList", service.selectSix(vo));
		}
		
		return "hoxdm/order/DeliOrderHOXdmList"; 
	}
	
	/**
	 * Ajax를 통한 여러건 데이터 삭제
	 * @param seqList
	 * @return
	 * @throws Exception
	 */
	@ResponseBody			
	@RequestMapping(value = "/hoxdm/order/UserOrderHOXdmDeleProc")
	public Map<String, Object> userOrderHOXdmDeleProc(
			@RequestParam(value="chbox") List<String> seqList) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		if (seqList == null || (seqList != null && seqList.size() == 0)) {
			returnMap.put("rt", "fail");
		} else {
			List<Boolean> successProductDelete = new ArrayList<Boolean>();
			
			for (String seq : seqList) {
				// 주문한 상품이 여러개일 수 있으므로, List로 읽는다
				OrderDto dto = new OrderDto();
				dto.setoSeq(seq);
				List<OrderDto> dtoList = service.selectOne(dto);
				
				// 상품 리스트 전부 삭제했는지 체크
				int successProductListCnt = service.listProductDelete(dtoList);
				
				if (dtoList.size() == successProductListCnt) {
					successProductDelete.add(true);
				} else {
					successProductDelete.add(false);
				}
			}
			
			// 삭제하려는 주문의 상품들이 모두 삭제 되었는지 체크
			int successProductCnt = 0;
			
			for (Boolean success : successProductDelete) {
				if (success) successProductCnt++;
			}

			// 상품 리스트 전부 삭제 했으면, 주문 리스트 삭제
			if (successProductCnt == seqList.size()) {
				int successCnt = service.listDelete(seqList);
				
				if (successCnt > 0) {
					returnMap.put("rt", "success");
				} else {
					returnMap.put("rt", "fail");
				}
			} else {
				returnMap.put("rt", "fail");
			}
		}

		return returnMap;
	}
}
