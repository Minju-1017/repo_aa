package com.aa.module.order;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
	
	@Autowired
	OrderDao dao;
	
	public List<OrderDto> selectList(OrderVo vo) {
		return dao.selectList(vo);
	}

	public int selectOneCount(OrderVo vo) {
		return dao.selectOneCount(vo);
	}
	
	public List<OrderDto> selectOne(OrderDto orderDto) {
		return dao.selectOne(orderDto);
	}
	
	public int update(OrderDto orderDto) {
		return dao.update(orderDto);
	}
	
	public int updateCheckDeliveryNo(OrderDto orderDto) {
		return dao.updateCheckDeliveryNo(orderDto);
	}
	
	public int updateOPList(OrderDto orderDto) {
		return dao.updateOPList(orderDto);
	}
	
	public int insert(OrderDto orderDto) {
		return dao.insert(orderDto);
	}
	public List<OrderDto> productList(){
		return dao.productList();
	}
	public int ordersInsert(OrderDto orderDto) {
		return dao.ordersInsert(orderDto);
	}
	public int ordersProductInsert(OrderDto orderDto) {
		return dao.ordersProductInsert(orderDto);
	}
	public int updateStock(OrderDto orderDto) {
		return dao.updateStock(orderDto);
	}
	public int updateState(OrderDto orderDto) {
		return dao.updateState(orderDto);
	}
	
	public int listDelete(List<String> seqList) {
		return dao.listDelete(seqList);
	}
	
	public int listProductDelete(List<OrderDto> seqList) {
		return dao.listProductDelete(seqList);
	}
}
