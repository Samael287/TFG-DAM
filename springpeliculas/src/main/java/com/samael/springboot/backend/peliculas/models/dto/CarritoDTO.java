package com.samael.springboot.backend.peliculas.models.dto;

import java.io.Serializable;

public class CarritoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private UsuariosDTO usuario;
	private PeliculasDTO pelicula;
	private int cantidad;

	public CarritoDTO() {
		super();
	}

	public CarritoDTO(int id, UsuariosDTO usuario, PeliculasDTO pelicula, int cantidad) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.pelicula = pelicula;
		this.cantidad = cantidad;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UsuariosDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuariosDTO usuario) {
		this.usuario = usuario;
	}

	public PeliculasDTO getPelicula() {
		return pelicula;
	}

	public void setPelicula(PeliculasDTO pelicula) {
		this.pelicula = pelicula;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
}
