package com.samael.springboot.backend.peliculas.models.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.samael.springboot.backend.peliculas.models.entidades.Compra;

public interface ICompraDAO extends CrudRepository<Compra, Integer>{
	
	 // Obtener todos los elementos en la compra de un usuario
	List<Compra> findByUsuarioId(Integer id);
	
	List<Compra> findByTarjetaId(Integer tarjetaId);
	
	List<Compra> findByPeliculaId(Integer peliculaId);
	 
	// Buscar si un usuario ya tiene una película en su carrito
	Compra findByUsuarioIdAndPeliculaId(Integer usuarioId, Integer peliculaId);
	
    Compra findTopByUsuarioIdOrderByTransaccionIdDesc(Integer usuarioId);
    
    List<Compra> findByFechaCompraAfter(LocalDateTime fecha);

    List<Compra> findByFechaCompraBetween(LocalDateTime inicio, LocalDateTime fin);
}
