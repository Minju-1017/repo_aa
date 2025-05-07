package com.aa.module.miss;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface MissDao {
	
	public List<MissDto> uaList(MissDto dto);
	public MissDto uaOne(MissDto dto);
	public int mbInsert(MissDto dto);
	public List<MissDto> mbList(MissVo vo);
	public int selectCount(MissVo vo);
	public MissDto mbOne(MissDto dto);
	public List<MissDto> reviewList(MissVo vo);
	public int reviewCount(MissVo vo);
	public int reviewInsert(MissDto dto);
	public int mbUpdate(MissDto dto);
	public int mbUelete(MissDto dto);
}
