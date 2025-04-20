package com.samael.springboot.backend.peliculas.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.samael.springboot.backend.peliculas.models.dao.ILanguageDAO;
import com.samael.springboot.backend.peliculas.models.entidades.Language;

@Service
public class LanguageServiceImpl implements ILanguageService{

	@Autowired
	private ILanguageDAO languageDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Language> findAll() {
		return (List<Language>) languageDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Language findById(Integer id) {
		return languageDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Language save(Language language) {
		return languageDao.save(language);
	}

	@Override
	public void delete(Integer id) {
		languageDao.deleteById(id);
	}
}
