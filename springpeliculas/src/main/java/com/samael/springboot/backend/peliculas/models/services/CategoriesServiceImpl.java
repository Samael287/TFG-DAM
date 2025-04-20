package com.samael.springboot.backend.peliculas.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.samael.springboot.backend.peliculas.models.dao.ICategoriesDAO;
import com.samael.springboot.backend.peliculas.models.entidades.Categories;

@Service
public class CategoriesServiceImpl implements ICategoriesService{

	@Autowired
	private ICategoriesDAO categoriesDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Categories> findAll() {
		return (List<Categories>) categoriesDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Categories findById(Integer id) {
		return categoriesDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Categories save(Categories category) {
		return categoriesDao.save(category);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		categoriesDao.deleteById(id);
	}

}
