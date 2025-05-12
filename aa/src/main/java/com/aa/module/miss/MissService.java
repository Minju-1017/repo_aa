package com.aa.module.miss;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MissService {
	
	@Autowired
	MissDao dao;
	
	public List<MissDto> uaList(MissDto dto){
		return dao.uaList(dto);
	}
	public MissDto uaOne(MissDto dto) {
		return dao.uaOne(dto);
	}
	public int mbInsert(MissDto dto) {
		return dao.mbInsert(dto);
	}
	public List<MissDto> mbList(MissVo vo){
		return dao.mbList(vo);
	}
	public int selectCount(MissVo vo) {
		return dao.selectCount(vo);
	}
	public MissDto mbOne(MissDto dto) {
		return dao.mbOne(dto);
	}
	public List<MissDto> reviewList(MissVo vo){
		return dao.reviewList(vo);
	}
	public int reviewCount(MissVo vo) {
		return dao.reviewCount(vo);
	}
	public int reviewInsert(MissDto dto) {
		return dao.reviewInsert(dto);
	}
	public int mbUpdate(MissDto dto) {
		return dao.mbUpdate(dto);
	}
	public int mbUelete(MissDto dto) {
		return dao.mbUelete(dto);
	}
	
}
