package com.samael.springboot.backend.peliculas.models.services;

import java.util.List;

import com.samael.springboot.backend.peliculas.models.entidades.Studios;

public interface IStudiosService {

	public List<Studios> findAll();
	
	public Studios findById(Integer id);
	
	public Studios save(Studios studio);
	
	public void delete(Integer id);
}
