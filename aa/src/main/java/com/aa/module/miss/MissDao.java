package com.aa.module.miss;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface MissDao {
	
	public List<MissDto> uaList(MissDto dto);
	public MissDto uaOne(MissDto dto);
	public int mbInsert(MissDto dto);
}
