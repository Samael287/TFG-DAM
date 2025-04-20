package com.samael.springboot.backend.peliculas.models.entidades;

import java.time.LocalDateTime;
import java.util.List;

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
import jakarta.persistence.Transient;

@Entity
@Table(name = "compras")
public class Compra implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private Usuarios usuario;
	private Peliculas pelicula;
	private Tarjeta tarjeta;
	private Integer usuarioBorradoId;
	private Integer peliculaBorradaId;
	private Integer tarjetaBorradaId;
	private double precio;
	private LocalDateTime fechaCompra;
	private Integer transaccionId;
    private List<Integer> peliculaIds;


	public Compra() {
		super();
	}

	public Compra(int id, Usuarios usuario, Peliculas pelicula, Tarjeta tarjeta, int usuarioBorradoId,
			int peliculaBorradaId, int tarjetaBorradaId, double precio, LocalDateTime fechaCompra, int transaccionId) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.pelicula = pelicula;
		this.tarjeta = tarjeta;
		this.usuarioBorradoId = usuarioBorradoId;
		this.peliculaBorradaId = peliculaBorradaId;
		this.tarjetaBorradaId = tarjetaBorradaId;
		this.precio = precio;
		this.fechaCompra = fechaCompra;
		this.transaccionId = transaccionId;
	}

	public Compra(Usuarios usuario, Peliculas pelicula) {
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
    @JoinColumn(name = "usuario_id", nullable = true,  referencedColumnName = "id",
						foreignKey = @ForeignKey(name = "fk_compra_usuario"))
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Para evitar problemas en JSON
	public Usuarios getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}

	@ManyToOne(fetch = FetchType.LAZY)  
    @JoinColumn(name = "pelicula_id", nullable = true,  referencedColumnName = "id",
						foreignKey = @ForeignKey(name = "fk_compra_pelicula"))
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Para evitar problemas en JSON
	public Peliculas getPelicula() {
		return pelicula;
	}

	public void setPelicula(Peliculas pelicula) {
		this.pelicula = pelicula;
	}

	@ManyToOne(fetch = FetchType.LAZY)  
    @JoinColumn(name = "tarjeta_id", nullable = true,  referencedColumnName = "id",
						foreignKey = @ForeignKey(name = "fk_compra_tarjeta"))
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Para evitar problemas en JSON
	public Tarjeta getTarjeta() {
		return tarjeta;
	}

	public void setTarjeta(Tarjeta tarjeta) {
		this.tarjeta = tarjeta;
	}

    @Column(name = "id_usuario_borrado", nullable = true) // Se guarda el ID si el usuario es eliminado
	public Integer getUsuarioBorradoId() {
		return usuarioBorradoId;
	}

	public void setUsuarioBorradoId(Integer usuarioBorradoId) {
		this.usuarioBorradoId = usuarioBorradoId;
	}
    
	@Column(name = "id_pelicula_borrada", nullable = true) // Se guarda el ID si el usuario es eliminado
	public Integer getPeliculaBorradaId() {
		return peliculaBorradaId;
	}

	public void setPeliculaBorradaId(Integer peliculaBorradaId) {
		this.peliculaBorradaId = peliculaBorradaId;
	}

	@Column(name = "id_tarjeta_borrada", nullable = true) // Se guarda el ID si el usuario es eliminado
	public Integer getTarjetaBorradaId() {
		return tarjetaBorradaId;
	}

	public void setTarjetaBorradaId(Integer tarjetaBorradaId) {
		this.tarjetaBorradaId = tarjetaBorradaId;
	}

    @Column(name = "precio", nullable = false)
	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}
    
	@Column(name = "fecha_compra", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	public LocalDateTime getFechaCompra() {
		return fechaCompra;
	}

	public void setFechaCompra(LocalDateTime fechaCompra) {
		this.fechaCompra = fechaCompra;
	}

	public Integer getTransaccionId() {
		return transaccionId;
	}

    @Column(name = "transaccion_id", nullable = false)
	public void setTransaccionId(Integer transaccionId) {
		this.transaccionId = transaccionId;
	}
    
    @Transient
    public List<Integer> getPeliculaIds() {
        return peliculaIds;
    }

    public void setPeliculaIds(List<Integer> peliculaIds) {
        this.peliculaIds = peliculaIds;
    }
}
