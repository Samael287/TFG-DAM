package com.samael.springboot.backend.peliculas.models.entidades;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "usuarios")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Usuarios implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	
	@NotEmpty(message = "no puede estar vacío")
	@Size(min=3, max=12, message = "el tamaño tiene que estar entre 3 y 12")
	private String nombre;
	
	@NotEmpty(message = "no puede estar vacío")
	private String apellidos;
	
	@NotEmpty(message = "no puede estar vacío")
	@Email(message = "no es una dirección de correo bien formada")
	private String email;
	
	@NotEmpty(message = "no puede estar vacío")
	@Size(min=4, message = "el tamaño mínimo es de 4 caracteres.")
	private String password;
	
	private UserRole rol;
	private Tarjeta tarjeta;

	public Usuarios() {
		super();
	}

	public Usuarios(Integer id, String nombre, String apellidos, String email, String password, UserRole rol,
			Tarjeta tarjeta) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.password = password;
		this.rol = rol;
		this.tarjeta = tarjeta;
	}
	
	public Usuarios(Usuarios dto) {
        this.nombre = dto.getNombre();
        this.apellidos = dto.getApellidos();
        this.email = dto.getEmail();
        this.password = dto.getPassword();
        this.rol = dto.getRol();
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

	@Column(name = "nombre", nullable = false)
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "apellidos", nullable = false)
	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	@Column(name = "email", nullable = false, unique = true)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "password", nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
	public UserRole getRol() {
		return rol;
	}

	public void setRol(UserRole rol) {
		this.rol = rol;
	}

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH}) //detach para que no se elimine la tarjeta asociada, porque con ALL se elimina aunque desasocies.
	    @JoinColumn(name = "tarjeta_id", unique = true, nullable = true, referencedColumnName = "id", 
	                foreignKey = @ForeignKey(name = "fk_tarjeta"))
    @JsonIgnoreProperties("usuarios")
	public Tarjeta getTarjeta() {
		return tarjeta;
	}

	public void setTarjeta(Tarjeta tarjeta) {
		this.tarjeta = tarjeta;
	}
}
