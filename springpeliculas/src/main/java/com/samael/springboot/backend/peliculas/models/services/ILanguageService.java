package com.samael.springboot.backend.peliculas.models.services;

import java.util.List;

import com.samael.springboot.backend.peliculas.models.entidades.Language;

public interface ILanguageService {

	public List<Language> findAll();
	
	public Language findById(Integer id);
	
	public Language save(Language language);
	
	public void delete(Integer id);
}
