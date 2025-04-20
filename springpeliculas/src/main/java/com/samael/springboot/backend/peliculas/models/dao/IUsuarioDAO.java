package com.samael.springboot.backend.peliculas.models.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.samael.springboot.backend.peliculas.models.entidades.Usuarios;

public interface IUsuarioDAO extends CrudRepository<Usuarios, Integer>{

    Optional<Usuarios> findByEmailAndPassword(String email, String password);

}
