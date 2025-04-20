package com.samael.springboot.backend.peliculas.models.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class FlagsDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String flags;
	private Set<String> peliculas = new HashSet<>();

	public FlagsDTO() {
		super();
	}

	public FlagsDTO(int id, String flags, Set<String> peliculas) {
		super();
		this.id = id;
		this.flags = flags;
		this.peliculas = peliculas;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFlags() {
		return flags;
	}

	public void setFlags(String flags) {
		this.flags = flags;
	}

	public Set<String> getPeliculas() {
		return peliculas;
	}

	public void setPeliculas(Set<String> peliculas) {
		this.peliculas = peliculas;
	}
}
