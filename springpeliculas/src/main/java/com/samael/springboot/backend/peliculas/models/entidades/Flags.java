package com.samael.springboot.backend.peliculas.models.entidades;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "flags")
public class Flags implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String flags;
	private Set<Peliculas> peliculas = new HashSet<Peliculas>(0);

	public Flags() {
		super();
	}

	public Flags(int id, String flags, Set<Peliculas> peliculas) {
		super();
		this.id = id;
		this.flags = flags;
		this.peliculas = peliculas;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "flag", nullable = false)
	public String getFlags() {
		return flags;
	}

	public void setFlags(String flags) {
		this.flags = flags;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "pelicula_flags", joinColumns = {
			@JoinColumn(name = "flag_id", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "pelicula_id", nullable = false) })
	@JsonIgnoreProperties("flagses")
	public Set<Peliculas> getPeliculas() {
		return peliculas;
	}

	public void setPeliculas(Set<Peliculas> peliculas) {
		this.peliculas = peliculas;
	}
}
