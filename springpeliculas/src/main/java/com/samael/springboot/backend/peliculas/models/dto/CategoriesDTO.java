package com.samael.springboot.backend.peliculas.models.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class CategoriesDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String category;
	private Set<String> peliculas = new HashSet<>();

	public CategoriesDTO() {
		super();
	}

	public CategoriesDTO(int id, String category, Set<String> peliculas) {
		super();
		this.id = id;
		this.category = category;
		this.peliculas = peliculas;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Set<String> getPeliculas() {
		return peliculas;
	}

	public void setPeliculas(Set<String> peliculas) {
		this.peliculas = peliculas;
	}
}
