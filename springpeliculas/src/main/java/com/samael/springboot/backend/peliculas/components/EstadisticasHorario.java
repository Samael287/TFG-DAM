package com.samael.springboot.backend.peliculas.components;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.samael.springboot.backend.peliculas.models.dao.IEstadisticaVentaDAO;
import com.samael.springboot.backend.peliculas.models.entidades.EstadisticaVenta;
import com.samael.springboot.backend.peliculas.models.services.ICompraService;

@Component
public class EstadisticasHorario {

	@Autowired
	private ICompraService compraService;

	@Autowired
	private IEstadisticaVentaDAO estadisticasDao;

	// 🕛 Todos los días a las 00:00
	@Scheduled(cron = "0 0 0 * * ?")
	public void guardarEstadisticasDelDia() {
		guardarEstadisticas("DIARIO");
	}

	// 📆 Cada lunes a las 02:00 AM
	@Scheduled(cron = "0 2 0 ? * MON")
	public void guardarEstadisticasSemanales() {
		guardarEstadisticas("SEMANAL");
	}

	// 📈 Cada día 1 del mes a las 04:00 AM
	@Scheduled(cron = "0 4 0 1 * ?")
	public void guardarEstadisticasMensuales() {
		guardarEstadisticas("MENSUAL");
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
		System.out.println("📊 Estadística guardada - Tipo: " + tipo + ", Fecha: " + LocalDate.now());
	}
}
