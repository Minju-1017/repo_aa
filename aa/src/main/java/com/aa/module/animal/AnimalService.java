package com.aa.module.animal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnimalService {
	
	@Autowired
	AnimalDao dao;
	
	public List<AnimalDto> selectList(AnimalDto animalDto) {
		return dao.selectList(animalDto);
	}

}
