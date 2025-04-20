package com.samael.springboot.backend.peliculas.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.samael.springboot.backend.peliculas.models.dao.IGenresDAO;
import com.samael.springboot.backend.peliculas.models.entidades.Genres;

@Service
public class GenresServiceImpl implements IGenresService{

	@Autowired
	private IGenresDAO genresDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Genres> findAll() {
		return (List<Genres>) genresDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Genres findById(Integer id) {
		return genresDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Genres save(Genres genre) {
		return genresDao.save(genre);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		genresDao.deleteById(id);
	}
}
