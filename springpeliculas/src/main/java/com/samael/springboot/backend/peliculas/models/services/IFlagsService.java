package com.samael.springboot.backend.peliculas.models.services;

import java.util.List;

import com.samael.springboot.backend.peliculas.models.entidades.Flags;

public interface IFlagsService {

	public List<Flags> findAll();
	
	public Flags findById(Integer id);
	
	public Flags save(Flags flag);
	
	public void delete(Integer id);
}
