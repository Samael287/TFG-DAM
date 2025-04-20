package com.samael.springboot.backend.peliculas.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.samael.springboot.backend.peliculas.models.dao.IActorDAO;
import com.samael.springboot.backend.peliculas.models.entidades.Actor;

@Service
public class ActorServiceImpl implements IActorService{

	@Autowired
	private IActorDAO actorDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Actor> findAll() {
		return (List<Actor>) actorDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Actor findById(Integer id) {
		return actorDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Actor save(Actor actor) {
		return actorDao.save(actor);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		actorDao.deleteById(id);
	}
}
