package com.aa.module.order;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface OrderDao {
	
	public List<OrderDto> selectList(OrderVo vo);
	public int selectOneCount(OrderVo vo);
	public int selectTwoCount(OrderVo vo);
	public int selectThreeCount(OrderVo vo);
	public int selectFourCount(OrderVo vo);
	public int selectFiveCount(OrderVo vo);
	public int selectSixCount(OrderVo vo);
	public List<OrderDto> selectOne(OrderDto orderDto);
	public List<OrderDto> selectTwo(OrderVo vo);
	public List<OrderDto> selectThree(OrderVo vo);
	public List<OrderDto> selectFour(OrderVo vo);
	public List<OrderDto> selectFive(OrderVo vo);
	public List<OrderDto> selectSix(OrderVo vo);
	public int update(OrderDto orderDto);
	public int updateCheckDeliveryNo(OrderDto orderDto);
	public int updateOPList(OrderDto orderDto);
	public int insert(OrderDto orderDto);
	public List<OrderDto> productList();
	public int ordersInsert(OrderDto orderDto);
	public int ordersProductInsert(OrderDto orderDto);
	public int updateStock(OrderDto orderDto);
	public int updateState(OrderDto orderDto);
	public int listDelete(List<String> seqList);
	public int listProductDelete(List<OrderDto> seqList);
}
