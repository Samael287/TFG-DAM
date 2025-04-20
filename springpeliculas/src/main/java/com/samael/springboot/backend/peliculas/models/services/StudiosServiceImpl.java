package com.samael.springboot.backend.peliculas.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.samael.springboot.backend.peliculas.models.dao.IStudiosDAO;
import com.samael.springboot.backend.peliculas.models.entidades.Studios;

@Service
public class StudiosServiceImpl implements IStudiosService{

	@Autowired
	private IStudiosDAO studiosDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Studios> findAll() {
		return (List<Studios>) studiosDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Studios findById(Integer id) {
		return studiosDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Studios save(Studios studio) {
		return studiosDao.save(studio);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		studiosDao.deleteById(id);
	}
}
