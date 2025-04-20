package com.samael.springboot.backend.peliculas.models.services;

import java.util.List;

import com.samael.springboot.backend.peliculas.models.entidades.Actor;

public interface IActorService {

	public List<Actor> findAll();
	
	public Actor findById(Integer id);
	
	public Actor save(Actor actor);
	
	public void delete(Integer id);
}
