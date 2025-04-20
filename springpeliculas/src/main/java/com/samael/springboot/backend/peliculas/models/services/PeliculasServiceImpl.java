package com.samael.springboot.backend.peliculas.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.samael.springboot.backend.peliculas.models.dao.IPeliculasDAO;
import com.samael.springboot.backend.peliculas.models.entidades.Peliculas;

@Service
public class PeliculasServiceImpl implements IPeliculasService{

	@Autowired
	private IPeliculasDAO peliculasDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Peliculas> findAll() {
		return (List<Peliculas>) peliculasDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Peliculas findById(Integer id) {
		return peliculasDao.findById(id).orElse(null);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Peliculas> findAllById(List<Integer> peliculasId) {
		return (List<Peliculas>) peliculasDao.findAllById(peliculasId);
	}

	@Override
	@Transactional
	public Peliculas save(Peliculas pelicula) {
		return peliculasDao.save(pelicula);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		peliculasDao.deleteById(id);
	}

	@Override
	public List<Peliculas> findAllById(Peliculas pelicula) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Peliculas> findTopByGeneros(List<Integer> generoIds) {
	    return peliculasDao.findByGenresIdIn(generoIds);
	}

}