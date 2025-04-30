package com.aa.module.animal;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.aa.module.member.MemberDto;

@Repository
public interface AnimalDao {
	
	
	public List<AnimalDto> selectList(MemberDto memberDto);  
	public AnimalDto selectOne(AnimalDto animalDto);  
	public int insert(AnimalDto animalDto);
}
