package com.samael.springboot.backend.peliculas.models.dto;

import java.io.Serializable;

public class TarjetaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String numeroTarjeta;
	private String fechaCaducidad;
	private String numeroSeguridad;
	private Double fondosDisponibles;
	private Integer usuarioAnteriorId;
	private Integer usuarioId;

	public TarjetaDTO() {
		super();
	}

	public TarjetaDTO(Integer id, String numeroTarjeta, String fechaCaducidad, String numeroSeguridad,
			Double fondosDisponibles, Integer usuarioAnteriorId,Integer usuarioId) {
		super();
		this.id = id;
		this.numeroTarjeta = numeroTarjeta;
		this.fechaCaducidad = fechaCaducidad;
		this.numeroSeguridad = numeroSeguridad;
		this.fondosDisponibles = fondosDisponibles;
		this.usuarioAnteriorId = usuarioAnteriorId;
		this.usuarioId = usuarioId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumeroTarjeta() {
		return numeroTarjeta;
	}

	public void setNumeroTarjeta(String numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}

	public String getFechaCaducidad() {
		return fechaCaducidad;
	}

	public void setFechaCaducidad(String fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}

	public String getNumeroSeguridad() {
		return numeroSeguridad;
	}

	public void setNumeroSeguridad(String numeroSeguridad) {
		this.numeroSeguridad = numeroSeguridad;
	}

	public Double getFondosDisponibles() {
		return fondosDisponibles;
	}

	public void setFondosDisponibles(Double fondosDisponibles) {
		this.fondosDisponibles = fondosDisponibles;
	}
	
	public Integer getUsuarioAnteriorId() {
		return usuarioAnteriorId;
	}

	public void setUsuarioAnteriorId(Integer usuarioAnteriorId) {
		this.usuarioAnteriorId = usuarioAnteriorId;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}
}
