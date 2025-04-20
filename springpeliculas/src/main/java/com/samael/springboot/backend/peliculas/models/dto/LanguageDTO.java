package com.samael.springboot.backend.peliculas.models.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class LanguageDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String language;
	private Set<String> peliculas = new HashSet<>();

	public LanguageDTO() {
		super();
	}

	public LanguageDTO(int id, String language, Set<String> peliculas) {
		super();
		this.id = id;
		this.language = language;
		this.peliculas = peliculas;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Set<String> getPeliculas() {
		return peliculas;
	}

	public void setPeliculas(Set<String> peliculas) {
		this.peliculas = peliculas;
	}
}
