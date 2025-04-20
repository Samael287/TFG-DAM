package com.samael.springboot.backend.peliculas.models.services;

import java.util.Date;
import java.util.List;

import com.samael.springboot.backend.peliculas.models.entidades.Tarjeta;

public interface ITarjetaService {
	
	public List<Tarjeta> findAll();
	
	public Tarjeta findById(Integer id);
	
	public Tarjeta save(Tarjeta tarjeta);
	
	public void delete(Integer id);

	public Tarjeta findByNumeroFechaSeguridad(String numeroTarjeta, Date fechaCaducidad, String numeroSeguridad);
}
