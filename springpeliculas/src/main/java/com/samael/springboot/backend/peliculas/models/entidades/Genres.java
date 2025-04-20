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
@Table(name = "genres")
public class Genres implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String genre;
	private Set<Peliculas> peliculas = new HashSet<Peliculas>(0);

	public Genres() {
		super();
	}

	public Genres(int id, String genre, Set<Peliculas> peliculas) {
		super();
		this.id = id;
		this.genre = genre;
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

	@Column(name = "genre", nullable = false)
	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "pelicula_genre", joinColumns = {
			@JoinColumn(name = "genre_id", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "pelicula_id", nullable = false) })
	@JsonIgnoreProperties("genres")
	public Set<Peliculas> getPeliculas() {
		return peliculas;
	}

	public void setPeliculas(Set<Peliculas> peliculas) {
		this.peliculas = peliculas;
	}
}
