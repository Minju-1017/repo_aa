package com.aa.module.animal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aa.module.member.MemberDto;

@Service
public class AnimalService {
	
	@Autowired
	AnimalDao dao;
	
	public List<AnimalDto> selectList(MemberDto memberDto) {
		return dao.selectList(memberDto);
	}
	
	public AnimalDto selectOne(AnimalDto animalDto) {
		return dao.selectOne(animalDto);
	} 
	
	public int insert(AnimalDto animalDto) {
		return dao.insert(animalDto);
	}
	
	public int update(AnimalDto animalDto) {
		return dao.update(animalDto);
	}
	
	public int uelete(AnimalDto animalDto) {
		return dao.uelete(animalDto);
	}
}
