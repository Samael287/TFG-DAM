package com.samael.springboot.backend.peliculas.models.entidades;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.samael.springboot.backend.peliculas.validation.ValidacionTarjeta;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tarjeta")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Para evitar problemas en JSON
public class Tarjeta implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	
	@NotBlank(message = "El número de tarjeta no puede estar vacío", groups = ValidacionTarjeta.class)
	@Size(min = 16, max = 16, message = "El número de tarjeta debe tener 16 dígitos", groups = ValidacionTarjeta.class)
	@Pattern(regexp = "\\d{16}", message = "El número de tarjeta debe tener exactamente 16 dígitos", groups = ValidacionTarjeta.class)
	private String numeroTarjeta;

	@NotNull(message = "La fecha de caducidad es obligatoria", groups = ValidacionTarjeta.class)
	private Date fechaCaducidad;

	@NotBlank(message = "El número de seguridad es obligatorio", groups = ValidacionTarjeta.class)
	@Size(min = 3, max = 3, message = "El código de seguridad debe tener 3 dígitos", groups = ValidacionTarjeta.class)
	@Pattern(regexp = "\\d{3}", message = "El número de seguridad debe tener exactamente 3 dígitos", groups = ValidacionTarjeta.class)
	private String numeroSeguridad;

	@NotNull(message = "Los fondos son obligatorios", groups = ValidacionTarjeta.class)
	@Min(value = 0, message = "Los fondos no pueden ser negativos", groups = ValidacionTarjeta.class)
	private Double fondosDisponibles;
	
	private Integer usuarioAnteriorId;
	private Usuarios usuario;
	
	public Tarjeta() {
		super();
	}

	public Tarjeta(Integer id, String numeroTarjeta, Date fechaCaducidad, String numeroSeguridad,
			Double fondosDisponibles, Integer usuarioAnteriorId, Usuarios usuario) {
		super();
		this.id = id;
		this.numeroTarjeta = numeroTarjeta;
		this.fechaCaducidad = fechaCaducidad;
		this.numeroSeguridad = numeroSeguridad;
		this.fondosDisponibles = fondosDisponibles;
		this.usuarioAnteriorId = usuarioAnteriorId;
		this.usuario = usuario;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "numero_tarjeta", nullable = false)
	public String getNumeroTarjeta() {
		return numeroTarjeta;
	}

	public void setNumeroTarjeta(String numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}

	@Column(name = "fecha_caducidad", nullable = false)
	public Date getFechaCaducidad() {
		return fechaCaducidad;
	}

	public void setFechaCaducidad(Date fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}

	@Column(name = "numero_seguridad", nullable = false)
	public String getNumeroSeguridad() {
		return numeroSeguridad;
	}

	public void setNumeroSeguridad(String numeroSeguridad) {
		this.numeroSeguridad = numeroSeguridad;
	}

	@Column(name = "fondos_disponibles", nullable = false)
	public Double getFondosDisponibles() {
		return fondosDisponibles;
	}

	public void setFondosDisponibles(Double fondosDisponibles) {
		this.fondosDisponibles = fondosDisponibles;
	}
	
	@Column(name = "usuario_anterior_id", nullable = true)
	public Integer getUsuarioAnteriorId() {
		return usuarioAnteriorId;
	}

	public void setUsuarioAnteriorId(Integer usuarioAnteriorId) {
		this.usuarioAnteriorId = usuarioAnteriorId;
	}
	
    @OneToOne(mappedBy = "tarjeta", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("tarjeta")
	public Usuarios getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}
}
