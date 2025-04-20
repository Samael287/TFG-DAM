package com.samael.springboot.backend.peliculas.models.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class StudiosDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String studio;
	private Set<String> peliculas = new HashSet<>();

	public StudiosDTO() {
		super();
	}

	public StudiosDTO(int id, String studio, Set<String> peliculas) {
		super();
		this.id = id;
		this.studio = studio;
		this.peliculas = peliculas;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStudio() {
		return studio;
	}

	public void setStudio(String studio) {
		this.studio = studio;
	}

	public Set<String> getPeliculas() {
		return peliculas;
	}

	public void setPeliculas(Set<String> peliculas) {
		this.peliculas = peliculas;
	}
}
