package com.samael.springboot.backend.peliculas.models.dto;

import java.io.Serializable;

import com.samael.springboot.backend.peliculas.models.entidades.UserRole;

public class UsuariosDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String nombre;
	private String apellidos;
	private String email;
	private String password;
	private UserRole rol;
	private TarjetaDTO tarjeta;

	public UsuariosDTO() {
		super();
	}

	public UsuariosDTO(Integer id, String nombre, String apellidos, String email, String password, UserRole rol,
			TarjetaDTO tarjeta) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.password = password;
		this.rol = rol;
		this.tarjeta = tarjeta;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserRole getRol() {
		return rol;
	}

	public void setRol(UserRole rol) {
		this.rol = rol;
	}

	public TarjetaDTO getTarjeta() {
		return tarjeta;
	}

	public void setTarjeta(TarjetaDTO tarjeta) {
		this.tarjeta = tarjeta;
	}
}
