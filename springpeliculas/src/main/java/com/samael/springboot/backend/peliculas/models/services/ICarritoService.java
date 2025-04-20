package com.samael.springboot.backend.peliculas.models.services;

import java.util.List;

import com.samael.springboot.backend.peliculas.models.entidades.Carrito;
import com.samael.springboot.backend.peliculas.models.entidades.Peliculas;
import com.samael.springboot.backend.peliculas.models.entidades.Usuarios;

public interface ICarritoService {
	
	public List<Carrito> findAll();
	
	public Carrito findById(Integer id);
	
	public Carrito save(Carrito carrito);
	
	public void delete(Integer id);
	
	public List<Carrito> obtenerCarritoPorUsuario(Integer usuarioId);
    
    public Carrito agregarAlCarrito(Usuarios usuario, Peliculas pelicula);

    public void eliminarDelCarrito(Integer usuarioId, Integer peliculaId);
    
    public void vaciarCarrito(Integer usuarioId);
    
    public int obtenerCantidadTotal(Integer usuarioId);

	public Carrito findByUsuarioIdAndPeliculaId(Integer usuarioId, Integer peliculaId);

}
