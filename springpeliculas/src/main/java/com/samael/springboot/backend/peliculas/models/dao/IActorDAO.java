package com.samael.springboot.backend.peliculas.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.samael.springboot.backend.peliculas.models.entidades.Actor;

public interface IActorDAO extends CrudRepository<Actor, Integer>{

}
