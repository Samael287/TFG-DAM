package com.samael.springboot.backend.peliculas.models.entidades;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "carrito", uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "pelicula_id"}))
public class Carrito implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private Usuarios usuario;
	private Peliculas pelicula;

	public Carrito() {
		super();
	}

	public Carrito(int id, Usuarios usuario, Peliculas pelicula) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.pelicula = pelicula;
	}

	public Carrito(Usuarios usuario, Peliculas pelicula) {
		this.usuario = usuario;
		this.pelicula = pelicula;
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

	@ManyToOne(fetch = FetchType.LAZY)  
    @JoinColumn(name = "usuario_id", nullable = false,  referencedColumnName = "id",
						foreignKey = @ForeignKey(name = "fk_carrito_usuario"))
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Para evitar problemas en JSON
	public Usuarios getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pelicula_id", nullable = false,  referencedColumnName = "id",
    					foreignKey = @ForeignKey(name = "fk_carrito_pelicula"))
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	public Peliculas getPelicula() {
		return pelicula;
	}

	public void setPelicula(Peliculas pelicula) {
		this.pelicula = pelicula;
	}
}
