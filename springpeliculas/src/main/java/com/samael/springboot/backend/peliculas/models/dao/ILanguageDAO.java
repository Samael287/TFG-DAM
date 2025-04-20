package com.samael.springboot.backend.peliculas.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.samael.springboot.backend.peliculas.models.entidades.Language;

public interface ILanguageDAO extends CrudRepository<Language, Integer>{

}
