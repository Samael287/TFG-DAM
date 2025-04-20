package com.samael.springboot.backend.peliculas.models.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.samael.springboot.backend.peliculas.models.dao.ICompraDAO;
import com.samael.springboot.backend.peliculas.models.entidades.Compra;
import com.samael.springboot.backend.peliculas.models.entidades.Peliculas;
import com.samael.springboot.backend.peliculas.models.entidades.Usuarios;

@Service
public class CompraServiceImpl implements ICompraService {

	@Autowired
	ICompraDAO compraDao;

	@Autowired
	IPeliculasService peliculasService;

	@Override
	@Transactional(readOnly = true)
	public List<Compra> findAll() {
		return (List<Compra>) compraDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Compra findById(Integer id) {
		return compraDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Compra save(Compra compra) {
		return compraDao.save(compra);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		compraDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Compra> obtenerCompraPorUsuario(Integer usuarioId) {
		return compraDao.findByUsuarioId(usuarioId);
	}

	@Override
	@Transactional(readOnly = true)
	public Compra findByUsuarioIdAndPeliculaId(Integer usuarioId, Integer peliculaId) {
		return compraDao.findByUsuarioIdAndPeliculaId(usuarioId, peliculaId);
	}

	@Override
	@Transactional
	public Compra agregarCompra(Usuarios usuario, Peliculas pelicula) {
		Compra compraExistente = compraDao.findByUsuarioIdAndPeliculaId(usuario.getId(), pelicula.getId());

		if (compraExistente != null) {
			throw new IllegalArgumentException("La película ya está comprada.");
		}

		Compra nuevaCompra = new Compra(usuario, pelicula);

		return compraDao.save(nuevaCompra);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Compra> obtenerCompraPorTarjeta(Integer tarjetaId) {
		return compraDao.findByTarjetaId(tarjetaId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Compra> obtenerCompraPorPelicula(Integer peliculaId) {
		return compraDao.findByPeliculaId(peliculaId);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer obtenerUltimaTransaccionIdPorUsuario(Integer usuarioId) {
		Compra lastCompra = compraDao.findTopByUsuarioIdOrderByTransaccionIdDesc(usuarioId);
		return (lastCompra != null) ? lastCompra.getTransaccionId() : 0;
	}

	// ESTADISTICAS

	@Override
	@Transactional(readOnly = true)
	public Double obtenerIngresosTotales() {
		List<Compra> todas = (List<Compra>) compraDao.findAll();
		return todas.stream().mapToDouble(c -> c.getPrecio() != 0 ? c.getPrecio() : 0).sum();
	}

	@Override
	@Transactional(readOnly = true)
	public Double obtenerPromedioVentasDelMes() {
		List<Compra> delMes = obtenerComprasMensual();
		LocalDateTime hoy = LocalDateTime.now();
		int dias = hoy.getDayOfMonth();
		double total = delMes.stream().mapToDouble(c -> c.getPrecio() != 0 ? c.getPrecio() : 0).sum();
		return dias > 0 ? total / dias : 0;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Compra> obtenerComprasDelDia() {
		LocalDateTime inicio = LocalDate.now().atStartOfDay();
		LocalDateTime fin = LocalDate.now().atTime(23, 59, 59);
		return filtrarPorFecha(inicio, fin);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Compra> obtenerComprasSemanal() {
		LocalDateTime hoy = LocalDateTime.now();
		LocalDateTime inicioSemana = hoy.minusDays(7).withHour(0).withMinute(0).withSecond(0);
		return filtrarPorFecha(inicioSemana, hoy);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Compra> obtenerComprasMensual() {
		LocalDateTime hoy = LocalDateTime.now();
		LocalDateTime inicioMes = hoy.withDayOfMonth(1);
		return filtrarPorFecha(inicioMes, hoy);
	}

	// 📦 Método común para filtrar por rango de fechas
	private List<Compra> filtrarPorFecha(LocalDateTime desde, LocalDateTime hasta) {
		List<Compra> todas = (List<Compra>) compraDao.findAll();
		return todas.stream().filter(c -> {
			LocalDateTime fecha = c.getFechaCompra();
			return fecha != null && !fecha.isBefore(desde) && !fecha.isAfter(hasta);
		}).collect(Collectors.toList());
	}

	// TENDENCIAS
	@Override
	@Transactional(readOnly = true)
	public List<Peliculas> obtenerPeliculasMasVendidas() {
		Iterable<Compra> compras = compraDao.findAll();

		Map<Peliculas, Long> conteo = StreamSupport.stream(compras.spliterator(), false)
				.collect(Collectors.groupingBy(Compra::getPelicula, Collectors.counting()));

		return conteo.entrySet().stream().sorted((a, b) -> Long.compare(b.getValue(), a.getValue()))
				.map(Map.Entry::getKey).limit(5).toList();
	}

	/*@Override
	@Transactional(readOnly = true)
	public Peliculas obtenerPeliculaMasVendidaMes() {
		LocalDateTime inicioMes = LocalDate.now().withDayOfMonth(1).atStartOfDay();
		List<Compra> comprasMes = compraDao.findByFechaCompraAfter(inicioMes);

		return comprasMes.stream().collect(Collectors.groupingBy(Compra::getPelicula, Collectors.counting())).entrySet()
				.stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse(null);
	}*/
	
	@Override
	@Transactional(readOnly = true)
	public Peliculas obtenerPeliculaMasVendidaMes() {
	    LocalDateTime inicioMes = LocalDate.now().withDayOfMonth(1).atStartOfDay();
	    List<Compra> comprasMes = compraDao.findByFechaCompraAfter(inicioMes);

	    return comprasMes.stream()
	        .collect(Collectors.groupingBy(Compra::getPelicula, Collectors.counting()))
	        .entrySet().stream()
	        .sorted((a, b) -> {
	            int cmp = Long.compare(b.getValue(), a.getValue());
	            if (cmp == 0) {
	                // Desempatar por precio (por ejemplo, mayor precio)
	                return Double.compare(b.getKey().getPrecio(), a.getKey().getPrecio());
	            }
	            return cmp;
	        })
	        .map(Map.Entry::getKey)
	        .findFirst()
	        .orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Peliculas> recomendarPorHistorial(Integer usuarioId) {
		List<Compra> compras = compraDao.findByUsuarioId(usuarioId);

		Map<Integer, Long> generosComprados = compras.stream()
				.flatMap(compra -> compra.getPelicula().getGenres().stream())
				.collect(Collectors.groupingBy(genre -> genre.getId(), Collectors.counting()));

		List<Integer> topGeneros = generosComprados.entrySet().stream()
				.sorted((a, b) -> Long.compare(b.getValue(), a.getValue())).map(Map.Entry::getKey).limit(3)
				.collect(Collectors.toList());

		return peliculasService.findTopByGeneros(topGeneros);
	}
}
