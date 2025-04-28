package com.aa.module.vender;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VenderService {
	
	@Autowired
	VenderDao dao;
	
	public int selectOneCount(VenderVo vo) {
		return dao.selectOneCount(vo);
	}
	
	public List<VenderDto> selectList(VenderVo vo) {
		return dao.selectList(vo);
	}
	
	public List<VenderDto> selectListWithoutPaging() {
		return dao.selectListWithoutPaging();
	}
	
	public VenderDto selectOne(VenderDto venderDto) {
		return dao.selectOne(venderDto);
	}
	
	public int insert(VenderDto venderDto) {
		return dao.insert(venderDto);
	}
	
	public int update(VenderDto venderDto) {
		return dao.update(venderDto);
	}
	
	public int delete(VenderDto venderDto) {
		return dao.delete(venderDto);
	}
	
	public int uelete(VenderDto venderDto) {
		return dao.uelete(venderDto);
	}
	
	public int listDelete(List<String> seqList) {
		return dao.listDelete(seqList);
	}
	
	public int listUelete(List<String> seqList) {
		return dao.listUelete(seqList);
	}
}
