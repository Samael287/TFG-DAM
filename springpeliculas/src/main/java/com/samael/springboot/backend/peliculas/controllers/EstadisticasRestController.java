package com.samael.springboot.backend.peliculas.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samael.springboot.backend.peliculas.models.dao.IEstadisticaVentaDAO;
import com.samael.springboot.backend.peliculas.models.entidades.EstadisticaVenta;
import com.samael.springboot.backend.peliculas.models.services.ICompraService;

@CrossOrigin({"*"})
@RestController
@RequestMapping("/api")
public class EstadisticasRestController {

	@Autowired
	private ICompraService compraService;

	@Autowired
	private IEstadisticaVentaDAO estadisticasDao;

	// 📊 Generar estadísticas diarias
	@PostMapping("/estadisticas/diarias")
	public String generarEstadisticaDia() {
		guardarEstadisticas("DIARIO");
		return "✅ Estadísticas diarias generadas";
	}

	// 📊 Generar estadísticas semanales
	@PostMapping("/estadisticas/semanales")
	public String generarEstadisticaSemana() {
		guardarEstadisticas("SEMANAL");
		return "✅ Estadísticas semanales generadas";
	}

	// 📊 Generar estadísticas mensuales
	@PostMapping("/estadisticas/mensuales")
	public String generarEstadisticaMes() {
		guardarEstadisticas("MENSUAL");
		return "✅ Estadísticas mensuales generadas";
	}

	// 🧠 Método común para guardar estadísticas
	private void guardarEstadisticas(String tipo) {
		EstadisticaVenta estad = new EstadisticaVenta();
		estad.setFecha(LocalDate.now());
		estad.setVentasDelDia(compraService.obtenerComprasDelDia().size());
		estad.setVentasSemana(compraService.obtenerComprasSemanal().size());
		estad.setVentasMes(compraService.obtenerComprasMensual().size());
		estad.setIngresosTotales(compraService.obtenerIngresosTotales());
		estad.setPromedioDiario(compraService.obtenerPromedioVentasDelMes());
		estad.setTipo(tipo);

		estadisticasDao.save(estad);
		System.out.println("📊 Estadística guardada manualmente - Tipo: " + tipo + ", Fecha: " + LocalDate.now());
	}
}
