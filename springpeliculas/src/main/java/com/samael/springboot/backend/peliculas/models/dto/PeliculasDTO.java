package com.samael.springboot.backend.peliculas.models.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class PeliculasDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String titulo;
	private String descripcion;
	private String fecha_estreno;
	private int duracion;
	private double precio;
	private String portada;
	private String studio;
	private String category;
	private Set<String> actores = new HashSet<>();
	private Set<String> directores = new HashSet<>();
	private Set<String> flagses = new HashSet<>();
	private Set<String> genres = new HashSet<>();
	private Set<String> languages = new HashSet<>();

	public PeliculasDTO() {
		super();
	}

	public PeliculasDTO(int id, String titulo, String descripcion, String fecha_estreno, int duracion, double precio,
			String portada, String studio, String category, Set<String> actores, Set<String> directores,
			Set<String> flagses, Set<String> genres, Set<String> languages) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.fecha_estreno = fecha_estreno;
		this.duracion = duracion;
		this.precio = precio;
		this.portada = portada;
		this.studio = studio;
		this.category = category;
		this.actores = actores;
		this.directores = directores;
		this.flagses = flagses;
		this.genres = genres;
		this.languages = languages;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getFecha_estreno() {
		return fecha_estreno;
	}

	public void setFecha_estreno(String fecha_estreno) {
		this.fecha_estreno = fecha_estreno;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String getPortada() {
		return portada;
	}

	public void setPortada(String portada) {
		this.portada = portada;
	}

	public String getStudio() {
		return studio;
	}

	public void setStudio(String studio) {
		this.studio = studio;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Set<String> getActores() {
		return actores;
	}

	public void setActores(Set<String> actores) {
		this.actores = actores;
	}

	public Set<String> getDirectores() {
		return directores;
	}

	public void setDirectores(Set<String> directores) {
		this.directores = directores;
	}

	public Set<String> getFlagses() {
		return flagses;
	}

	public void setFlagses(Set<String> flagses) {
		this.flagses = flagses;
	}

	public Set<String> getGenres() {
		return genres;
	}

	public void setGenres(Set<String> genres) {
		this.genres = genres;
	}

	public Set<String> getLanguages() {
		return languages;
	}

	public void setLanguages(Set<String> languages) {
		this.languages = languages;
	}
}
