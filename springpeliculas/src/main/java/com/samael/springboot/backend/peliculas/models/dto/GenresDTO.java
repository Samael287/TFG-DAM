package com.samael.springboot.backend.peliculas.models.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class GenresDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String genre;
	private Set<String> peliculas = new HashSet<>();

	public GenresDTO() {
		super();
	}

	public GenresDTO(int id, String genre, Set<String> peliculas) {
		super();
		this.id = id;
		this.genre = genre;
		this.peliculas = peliculas;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public Set<String> getPeliculas() {
		return peliculas;
	}

	public void setPeliculas(Set<String> peliculas) {
		this.peliculas = peliculas;
	}
}
