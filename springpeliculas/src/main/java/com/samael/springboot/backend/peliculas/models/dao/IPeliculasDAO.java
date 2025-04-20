package com.samael.springboot.backend.peliculas.models.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.samael.springboot.backend.peliculas.models.entidades.Peliculas;

public interface IPeliculasDAO extends CrudRepository<Peliculas, Integer>{

	List<Peliculas> findAllById(Peliculas pelicula);

	List<Peliculas> findByGenresIdIn(List<Integer> genreIds);
}
