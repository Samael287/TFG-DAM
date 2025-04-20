package com.samael.springboot.backend.peliculas.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.samael.springboot.backend.peliculas.models.entidades.Flags;

public interface IFlagsDAO extends CrudRepository<Flags, Integer>{

}
