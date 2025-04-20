package com.samael.springboot.backend.peliculas.models.entidades;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "studios")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "peliculas"})  // Evita que se serialicen las películas dentro del estudio
public class Studios implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String studio;
	private Set<Peliculas> peliculas = new HashSet<Peliculas>(0);

	public Studios() {
		super();
	}

	public Studios(int id, String studio, Set<Peliculas> peliculas) {
		super();
		this.id = id;
		this.studio = studio;
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

	@Column(name = "studio", nullable = false)
	public String getStudio() {
		return studio;
	}

	public void setStudio(String studio) {
		this.studio = studio;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "studio", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("studio")  // Evita el ciclo cuando se serializa una película
	public Set<Peliculas> getPeliculas() {
		return peliculas;
	}

	public void setPeliculas(Set<Peliculas> peliculas) {
		this.peliculas = peliculas;
	}
}
