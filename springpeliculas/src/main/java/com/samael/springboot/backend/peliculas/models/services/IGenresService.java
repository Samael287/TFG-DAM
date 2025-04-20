package com.samael.springboot.backend.peliculas.models.services;

import java.util.List;

import com.samael.springboot.backend.peliculas.models.entidades.Genres;

public interface IGenresService {

	public List<Genres> findAll();
	
	public Genres findById(Integer id);
	
	public Genres save(Genres genre);
	
	public void delete(Integer id);
}
