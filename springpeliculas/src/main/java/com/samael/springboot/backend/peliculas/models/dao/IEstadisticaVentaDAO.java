package com.samael.springboot.backend.peliculas.models.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.samael.springboot.backend.peliculas.models.entidades.EstadisticaVenta;

public interface IEstadisticaVentaDAO extends CrudRepository<EstadisticaVenta, Integer> {
	
	List<EstadisticaVenta> findByTipo(String tipo);

	EstadisticaVenta findByFecha(LocalDate fecha);
}
