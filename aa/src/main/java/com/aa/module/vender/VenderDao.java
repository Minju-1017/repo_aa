package com.aa.module.vender;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface VenderDao {

	public int selectOneCount(VenderVo vo);
	public List<VenderDto> selectList(VenderVo vo); 
	public List<VenderDto> selectListWithoutPaging(); 
	public VenderDto selectOne(VenderDto venderDto);
	public int insert(VenderDto venderDto);
	public int update(VenderDto venderDto);
	public int delete(VenderDto venderDto);
	public int uelete(VenderDto venderDto);
	public int listDelete(List<String> seqList);
	public int listUelete(List<String> seqList);
	
}
