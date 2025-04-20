package com.samael.springboot.backend.peliculas.models.dao;

import java.util.Date;

import org.springframework.data.repository.CrudRepository;

import com.samael.springboot.backend.peliculas.models.entidades.Tarjeta;

public interface ITarjetaDAO extends CrudRepository<Tarjeta, Integer>{

	public Tarjeta findByNumeroTarjetaAndFechaCaducidadAndNumeroSeguridad(String numeroTarjeta, Date fechaCaducidad,
			String numeroSeguridad);

}
