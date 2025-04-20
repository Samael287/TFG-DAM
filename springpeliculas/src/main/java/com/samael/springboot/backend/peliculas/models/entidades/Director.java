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
@Table(name = "director")
public class Director implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private Set<Peliculas> peliculas = new HashSet<Peliculas>(0);

	public Director() {
		super();
	}

	public Director(int id, String name, Set<Peliculas> peliculas) {
		super();
		this.id = id;
		this.name = name;
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

	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "pelicula_director", joinColumns = {
			@JoinColumn(name = "director_id", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "pelicula_id", nullable = false) })
	@JsonIgnoreProperties("directores")
	public Set<Peliculas> getPeliculas() {
		return peliculas;
	}

	public void setPeliculas(Set<Peliculas> peliculas) {
		this.peliculas = peliculas;
	}
}
