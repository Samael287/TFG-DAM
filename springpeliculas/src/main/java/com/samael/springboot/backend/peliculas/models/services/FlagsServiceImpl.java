package com.samael.springboot.backend.peliculas.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.samael.springboot.backend.peliculas.models.dao.IFlagsDAO;
import com.samael.springboot.backend.peliculas.models.entidades.Flags;

@Service
public class FlagsServiceImpl implements IFlagsService{

	@Autowired
	private IFlagsDAO flagsDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Flags> findAll() {
		return (List<Flags>) flagsDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Flags findById(Integer id) {
		return flagsDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Flags save(Flags flag) {
		return flagsDao.save(flag);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		flagsDao.deleteById(id);
	}
}
