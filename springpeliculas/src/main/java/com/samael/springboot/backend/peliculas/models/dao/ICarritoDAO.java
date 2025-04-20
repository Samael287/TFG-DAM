package com.samael.springboot.backend.peliculas.models.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.samael.springboot.backend.peliculas.models.entidades.Carrito;

public interface ICarritoDAO extends CrudRepository<Carrito, Integer>{
	
	   // Obtener todos los elementos en el carrito de un usuario
    List<Carrito> findByUsuarioId(Integer usuarioId);

    // Buscar si un usuario ya tiene una película en su carrito
    Carrito findByUsuarioIdAndPeliculaId(Integer usuarioId, Integer peliculaId);

    // Vaciar completamente el carrito de un usuario
    void deleteByUsuarioId(Integer usuarioId);

    int countByUsuarioId(Integer usuarioId);
}
