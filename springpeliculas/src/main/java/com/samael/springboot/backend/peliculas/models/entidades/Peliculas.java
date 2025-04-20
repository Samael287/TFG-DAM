package com.samael.springboot.backend.peliculas.models.entidades;

import java.util.Date;
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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "peliculas")
public class Peliculas implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String titulo;
	private String descripcion;
	private Date fecha_estreno;
	private int duracion;
	private double precio;
	private String portada;
	private Studios studio;
	private Categories category;
	private Set<Actor> actores = new HashSet<Actor>(0);
	private Set<Director> directores = new HashSet<Director>(0);
	private Set<Flags> flagses = new HashSet<Flags>(0);
	private Set<Genres> genres = new HashSet<Genres>(0);
	private Set<Language> languages = new HashSet<Language>(0);

	public Peliculas() {
		super();
	}

	public Peliculas(int id, String titulo, String descripcion, Date fecha_estreno, int duracion, double precio,
			String portada, Studios studio, Categories category, Set<Actor> actores, Set<Director> directores,
			Set<Flags> flagses, Set<Genres> genres, Set<Language> languages) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.fecha_estreno = fecha_estreno;
		this.duracion = duracion;
		this.precio = precio;
		this.portada = portada;
		this.studio = studio;
		this.category = category;
		this.actores = actores;
		this.directores = directores;
		this.flagses = flagses;
		this.genres = genres;
		this.languages = languages;
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

	@Column(name = "titulo", nullable = false)
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@Column(name = "descripcion", nullable = false)
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "fecha_estreno", nullable = false)
    @Temporal(TemporalType.DATE)
	public Date getFecha_estreno() {
		return fecha_estreno;
	}

	public void setFecha_estreno(Date fecha_estreno) {
		this.fecha_estreno = fecha_estreno;
	}

	@Column(name = "duracion", nullable = false)
	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	@Column(name = "precio", nullable = false)
	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	@Column(name = "portada", nullable = false)
	public String getPortada() {
		return portada;
	}

	public void setPortada(String portada) {
		this.portada = portada;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "studio_id")	
	@JsonIgnoreProperties("peliculas")
	public Studios getStudio() {
		return studio;
	}

	public void setStudio(Studios studio) {
		this.studio = studio;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	@JsonIgnoreProperties("peliculas")
	public Categories getCategory() {
		return category;
	}

	public void setCategory(Categories category) {
		this.category = category;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "pelicula_actor", joinColumns = {
			@JoinColumn(name = "pelicula_id", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "actor_id", nullable = false) })
	@JsonIgnoreProperties("peliculas")
	public Set<Actor> getActores() {
		return actores;
	}

	public void setActores(Set<Actor> actores) {
		this.actores = actores;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "pelicula_director", joinColumns = {
			@JoinColumn(name = "pelicula_id", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "director_id", nullable = false) })
	@JsonIgnoreProperties("peliculas")
	public Set<Director> getDirectores() {
		return directores;
	}

	public void setDirectores(Set<Director> directores) {
		this.directores = directores;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "pelicula_flags", joinColumns = {
			@JoinColumn(name = "pelicula_id", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "flag_id", nullable = false) })
	@JsonIgnoreProperties("peliculas")
	public Set<Flags> getFlagses() {
		return flagses;
	}

	public void setFlagses(Set<Flags> flagses) {
		this.flagses = flagses;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "pelicula_genre", joinColumns = {
			@JoinColumn(name = "pelicula_id", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "genre_id", nullable = false) })
	@JsonIgnoreProperties("peliculas")
	public Set<Genres> getGenres() {
		return genres;
	}

	public void setGenres(Set<Genres> genres) {
		this.genres = genres;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "pelicula_language", joinColumns = {
			@JoinColumn(name = "pelicula_id", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "language_id", nullable = false) })
	@JsonIgnoreProperties("peliculas")
	public Set<Language> getLanguages() {
		return languages;
	}

	public void setLanguages(Set<Language> languages) {
		this.languages = languages;
	}
}
