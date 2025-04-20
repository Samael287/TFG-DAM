package com.samael.springboot.backend.peliculas.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.samael.springboot.backend.peliculas.models.dao.IDirectorDAO;
import com.samael.springboot.backend.peliculas.models.entidades.Director;

@Service
public class DirectorServiceImpl implements IDirectorService{

	@Autowired
	private IDirectorDAO directorDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Director> findAll() {
		return (List<Director>) directorDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Director findById(Integer id) {
		return directorDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Director save(Director director) {
		return directorDao.save(director);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		directorDao.deleteById(id);
	}
}
