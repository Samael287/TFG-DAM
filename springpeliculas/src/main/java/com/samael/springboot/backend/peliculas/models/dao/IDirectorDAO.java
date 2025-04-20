package com.samael.springboot.backend.peliculas.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.samael.springboot.backend.peliculas.models.entidades.Director;

public interface IDirectorDAO extends CrudRepository<Director, Integer>{

}
