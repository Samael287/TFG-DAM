package com.samael.springboot.backend.peliculas.models.services;

import java.util.List;

import com.samael.springboot.backend.peliculas.models.entidades.Categories;

public interface ICategoriesService {
	
	public List<Categories> findAll();
	
	public Categories findById(Integer id);
	
	public Categories save(Categories category);
	
	public void delete(Integer id);
}
