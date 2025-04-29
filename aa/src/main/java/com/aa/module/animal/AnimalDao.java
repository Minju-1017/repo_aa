package com.aa.module.animal;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface AnimalDao {
	
	
	public List<AnimalDto> selectList(AnimalDto animalDto);  
	

}
