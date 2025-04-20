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
@Table(name = "language")
public class Language implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String language;
	private Set<Peliculas> peliculas = new HashSet<Peliculas>(0);

	public Language() {
		super();
	}

	public Language(int id, String language, Set<Peliculas> peliculas) {
		super();
		this.id = id;
		this.language = language;
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

	@Column(name = "language", nullable = false)
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "pelicula_language", joinColumns = {
			@JoinColumn(name = "language_id", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "pelicula_id", nullable = false) })
	@JsonIgnoreProperties("languages")
	public Set<Peliculas> getPeliculas() {
		return peliculas;
	}

	public void setPeliculas(Set<Peliculas> peliculas) {
		this.peliculas = peliculas;
	}
}
