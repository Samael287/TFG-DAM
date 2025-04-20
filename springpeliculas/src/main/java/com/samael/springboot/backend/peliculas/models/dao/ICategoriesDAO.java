package com.samael.springboot.backend.peliculas.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.samael.springboot.backend.peliculas.models.entidades.Categories;

public interface ICategoriesDAO extends CrudRepository<Categories, Integer>{

}
