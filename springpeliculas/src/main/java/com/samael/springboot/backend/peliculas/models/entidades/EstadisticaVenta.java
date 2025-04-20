package com.samael.springboot.backend.peliculas.models.entidades;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "estadisticas_ventas") //Service hecho en ComprasServiceImpl.
public class EstadisticaVenta implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "fecha")
	private LocalDate fecha;

	@Column(name = "ventas_del_dia")
	private int ventasDelDia;

	@Column(name = "ventas_semana")
	private int ventasSemana;

	@Column(name = "ventas_mes")
	private int ventasMes;

	@Column(name = "ingresos_totales")
	private double ingresosTotales;

	@Column(name = "promedio_diario")
	private double promedioDiario;

	@Column(name = "tipo")
	private String tipo; // "DIARIO", "SEMANAL", etc.

	public EstadisticaVenta() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EstadisticaVenta(Long id, LocalDate fecha, int ventasDelDia, int ventasSemana, int ventasMes,
			double ingresosTotales, double promedioDiario, String tipo) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.ventasDelDia = ventasDelDia;
		this.ventasSemana = ventasSemana;
		this.ventasMes = ventasMes;
		this.ingresosTotales = ingresosTotales;
		this.promedioDiario = promedioDiario;
		this.tipo = tipo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public int getVentasDelDia() {
		return ventasDelDia;
	}

	public void setVentasDelDia(int ventasDelDia) {
		this.ventasDelDia = ventasDelDia;
	}

	public int getVentasSemana() {
		return ventasSemana;
	}

	public void setVentasSemana(int ventasSemana) {
		this.ventasSemana = ventasSemana;
	}

	public int getVentasMes() {
		return ventasMes;
	}

	public void setVentasMes(int ventasMes) {
		this.ventasMes = ventasMes;
	}

	public double getIngresosTotales() {
		return ingresosTotales;
	}

	public void setIngresosTotales(double ingresosTotales) {
		this.ingresosTotales = ingresosTotales;
	}

	public double getPromedioDiario() {
		return promedioDiario;
	}

	public void setPromedioDiario(double promedioDiario) {
		this.promedioDiario = promedioDiario;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
