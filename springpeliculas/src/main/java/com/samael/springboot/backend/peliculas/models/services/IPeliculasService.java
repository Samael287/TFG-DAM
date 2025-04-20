package com.samael.springboot.backend.peliculas.models.services;

import java.util.List;

import com.samael.springboot.backend.peliculas.models.entidades.Peliculas;

public interface IPeliculasService {

	public List<Peliculas> findAll();
	
	public Peliculas findById(Integer id);
	
	public Peliculas save(Peliculas pelicula);
	
	public void delete(Integer id);

	public List<Peliculas> findAllById(List<Integer> list);

	List<Peliculas> findAllById(Peliculas pelicula);
	
	List<Peliculas> findTopByGeneros(List<Integer> generoIds);

}
