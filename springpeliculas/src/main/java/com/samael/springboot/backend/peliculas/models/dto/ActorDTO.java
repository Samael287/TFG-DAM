package com.samael.springboot.backend.peliculas.models.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class ActorDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private Set<String> peliculas = new HashSet<>();

	public ActorDTO() {
		super();
	}

	public ActorDTO(int id, String name, Set<String> peliculas) {
		super();
		this.id = id;
		this.name = name;
		this.peliculas = peliculas;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<String> getPeliculas() {
		return peliculas;
	}

	public void setPeliculas(Set<String> peliculas) {
		this.peliculas = peliculas;
	}
}
