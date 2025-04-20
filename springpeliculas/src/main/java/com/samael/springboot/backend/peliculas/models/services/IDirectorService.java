package com.samael.springboot.backend.peliculas.models.services;

import java.util.List;

import com.samael.springboot.backend.peliculas.models.entidades.Director;

public interface IDirectorService {
	
	public List<Director> findAll();
	
	public Director findById(Integer id);
	
	public Director save(Director director);
	
	public void delete(Integer id);
}
