package com.samael.springboot.backend.peliculas.models.dto;

import java.io.Serializable;
import java.time.LocalDateTime;


public class CompraDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private UsuariosDTO usuario;
	private PeliculasDTO pelicula;
	private TarjetaDTO tarjeta;
	private Integer usuarioBorradoId;
	private Integer peliculaBorradaId;
	private Integer tarjetaBorradaId;
	private double precio;
	private LocalDateTime fechaCompra;
	private Integer transaccionId;

	public CompraDTO() {
		super();
	}

	public CompraDTO(int id, UsuariosDTO usuario, PeliculasDTO pelicula, TarjetaDTO tarjeta, Integer usuarioBorradoId,
			Integer peliculaBorradaId, Integer tarjetaBorradaId, double precio, LocalDateTime fechaCompra,
			Integer transaccionId) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.pelicula = pelicula;
		this.tarjeta = tarjeta;
		this.usuarioBorradoId = usuarioBorradoId;
		this.peliculaBorradaId = peliculaBorradaId;
		this.tarjetaBorradaId = tarjetaBorradaId;
		this.precio = precio;
		this.fechaCompra = fechaCompra;
		this.transaccionId = transaccionId;
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

	public PeliculasDTO getPeliculaId() {
		return pelicula;
	}

	public void setPelicula(PeliculasDTO pelicula) {
		this.pelicula = pelicula;
	}

	public TarjetaDTO getTarjeta() {
		return tarjeta;
	}

	public void setTarjeta(TarjetaDTO tarjeta) {
		this.tarjeta = tarjeta;
	}

	public Integer getUsuarioBorradoId() {
		return usuarioBorradoId;
	}

	public void setUsuarioBorradoId(Integer usuarioBorradoId) {
		this.usuarioBorradoId = usuarioBorradoId;
	}

	public Integer getPeliculaBorradaId() {
		return peliculaBorradaId;
	}

	public void setPeliculaBorradaId(Integer peliculaBorradaId) {
		this.peliculaBorradaId = peliculaBorradaId;
	}

	public Integer getTarjetaBorradaId() {
		return tarjetaBorradaId;
	}

	public void setTarjetaBorradaId(Integer tarjetaBorradaId) {
		this.tarjetaBorradaId = tarjetaBorradaId;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public LocalDateTime getFechaCompra() {
		return fechaCompra;
	}

	public void setFechaCompra(LocalDateTime fechaCompra) {
		this.fechaCompra = fechaCompra;
	}

	public Integer getTransaccionId() {
		return transaccionId;
	}

	public void setTransaccionId(Integer transaccionId) {
		this.transaccionId = transaccionId;
	}
}
