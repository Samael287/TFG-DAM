package com.samael.springboot.backend.peliculas.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.samael.springboot.backend.peliculas.models.dto.CompraDTO;
import com.samael.springboot.backend.peliculas.models.dto.PeliculasDTO;
import com.samael.springboot.backend.peliculas.models.dto.TarjetaDTO;
import com.samael.springboot.backend.peliculas.models.dto.UsuariosDTO;
import com.samael.springboot.backend.peliculas.models.entidades.Compra;
import com.samael.springboot.backend.peliculas.models.entidades.Peliculas;
import com.samael.springboot.backend.peliculas.models.entidades.Tarjeta;
import com.samael.springboot.backend.peliculas.models.entidades.Usuarios;
import com.samael.springboot.backend.peliculas.models.services.ICarritoService;
import com.samael.springboot.backend.peliculas.models.services.ICompraService;
import com.samael.springboot.backend.peliculas.models.services.IPeliculasService;
import com.samael.springboot.backend.peliculas.models.services.ITarjetaService;
import com.samael.springboot.backend.peliculas.models.services.IUsuarioService;

@CrossOrigin({ "*" })
@RestController
@RequestMapping("/api")
public class CompraRestController {

	@Autowired
	ICompraService compraService;

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private IPeliculasService peliculasService;

	@Autowired
	private ITarjetaService tarjetaService;

	@Autowired
	private ICarritoService carritoService;

	@GetMapping("/compras")
	List<CompraDTO> todasLasCompras() {

		List<Compra> compras = compraService.findAll();
		List<CompraDTO> compraDtoList = new ArrayList<>();

		for (Compra compra : compras) {
			CompraDTO compraDto = new CompraDTO();

			compraDto.setId(compra.getId());

			if (compra.getUsuario() != null) {
				UsuariosDTO usuarioDto = new UsuariosDTO();
				usuarioDto.setId(compra.getUsuario().getId());
				usuarioDto.setNombre(compra.getUsuario().getNombre());

				compraDto.setUsuario(usuarioDto);
			}

			if (compra.getPelicula() != null) {
				PeliculasDTO peliculaDto = new PeliculasDTO();
				peliculaDto.setId(compra.getPelicula().getId());
				peliculaDto.setTitulo(compra.getPelicula().getTitulo());
				peliculaDto.setPrecio(compra.getPelicula().getPrecio());

				compraDto.setPelicula(peliculaDto);
			}

			if (compra.getTarjeta() != null) {
				TarjetaDTO tarjetaDto = new TarjetaDTO();
				tarjetaDto.setId(compra.getTarjeta().getId());
				tarjetaDto.setNumeroTarjeta(compra.getTarjeta().getNumeroTarjeta());
				tarjetaDto.setUsuarioAnteriorId(compra.getTarjeta().getUsuarioAnteriorId());

				compraDto.setTarjeta(tarjetaDto);
			}

			compraDto.setFechaCompra(compra.getFechaCompra());
			compraDto.setUsuarioBorradoId(compra.getUsuarioBorradoId());
			compraDto.setPeliculaBorradaId(compra.getPeliculaBorradaId());
			compraDto.setTarjetaBorradaId(compra.getTarjetaBorradaId());
			compraDto.setPrecio(compra.getPelicula().getPrecio());
			compraDto.setTransaccionId(compra.getTransaccionId());

			compraDtoList.add(compraDto);
		}
		return compraDtoList;
	}

	@GetMapping("/compras/{id}")
	ResponseEntity<?> unaCompra(@PathVariable Integer id) {
		Compra compra = null;
		CompraDTO compraDto = new CompraDTO();

		Map<String, Object> respuesta = new HashMap<String, Object>();

		try {
			compra = compraService.findById(id);
		} catch (Exception e) { // Base de datos inaccesible
			respuesta.put("mensaje", "Error al realizar la consulta sobre la base de datos");
			respuesta.put("error", e.getMessage().concat(": ").concat(e.getStackTrace().toString()));
			return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (compra == null) { // Hemos buscado un id que no existe
			respuesta.put("mensaje", "El identificador buscado no existe");
			return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.NOT_FOUND);
		}

		compraDto.setId(compra.getId());

		if (compra.getUsuario() != null) {
			UsuariosDTO usuarioDto = new UsuariosDTO();
			usuarioDto.setId(compra.getUsuario().getId());
			usuarioDto.setNombre(compra.getUsuario().getNombre());

			compraDto.setUsuario(usuarioDto);
		}

		if (compra.getPelicula() != null) {
			PeliculasDTO peliculaDto = new PeliculasDTO();
			peliculaDto.setId(compra.getPelicula().getId());
			peliculaDto.setTitulo(compra.getPelicula().getTitulo());
			peliculaDto.setPrecio(compra.getPelicula().getPrecio());

			compraDto.setPelicula(peliculaDto);
		}

		if (compra.getTarjeta() != null) {
			TarjetaDTO tarjetaDto = new TarjetaDTO();
			tarjetaDto.setId(compra.getTarjeta().getId());
			tarjetaDto.setNumeroTarjeta(compra.getTarjeta().getNumeroTarjeta());
			tarjetaDto.setUsuarioAnteriorId(compra.getTarjeta().getUsuarioAnteriorId());

			compraDto.setTarjeta(tarjetaDto);
		}

		compraDto.setFechaCompra(compra.getFechaCompra());
		compraDto.setUsuarioBorradoId(compra.getUsuarioBorradoId());
		compraDto.setPeliculaBorradaId(compra.getPeliculaBorradaId());
		compraDto.setTarjetaBorradaId(compra.getTarjetaBorradaId());
		compraDto.setPrecio(compra.getPelicula().getPrecio());
		compraDto.setTransaccionId(compra.getTransaccionId());

		return new ResponseEntity<CompraDTO>(compraDto, HttpStatus.OK);
	}

	@GetMapping("/compras/usuario/{usuarioId}")
	public ResponseEntity<?> compraDeUnUsuario(@PathVariable Integer usuarioId) {
		List<Compra> compraItems = compraService.obtenerCompraPorUsuario(usuarioId);
		return new ResponseEntity<>(compraItems, HttpStatus.OK);
	}

	@PostMapping("/compras")
	public ResponseEntity<?> realizarCompra(@RequestBody Compra compra, BindingResult result) {
		Map<String, Object> response = new HashMap<>();

		// Validar errores de binding
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		// Validar que se envíen el id del usuario, el id de la tarjeta y al menos una
		// película (mediante peliculaIds)
		if (compra.getUsuario() == null || compra.getTarjeta() == null || compra.getPeliculaIds() == null
				|| compra.getPeliculaIds().isEmpty()) {
			response.put("mensaje", "Debe enviar el id del usuario, el id de la tarjeta y al menos un id de película.");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		// Obtener el usuario y la tarjeta de la base de datos
		Usuarios usuario = usuarioService.findById(compra.getUsuario().getId());
		Tarjeta tarjeta = tarjetaService.findById(compra.getTarjeta().getId());

		if (usuario == null || tarjeta == null) {
			response.put("mensaje", "Usuario o tarjeta no encontrados.");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		// Recuperar la lista de películas a comprar usando la lista de IDs recibida
		List<Peliculas> peliculas = peliculasService.findAllById(compra.getPeliculaIds());
		if (peliculas.isEmpty()) {
			response.put("mensaje", "No se encontraron las películas seleccionadas.");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		// Calcular el total de la compra sumando el precio de cada película
		double totalCompra = peliculas.stream().mapToDouble(Peliculas::getPrecio).sum();
		if (tarjeta.getFondosDisponibles() < totalCompra) {
			response.put("mensaje", "Saldo insuficiente para realizar la compra.");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		// Obtener el último número de transacción y calcular el nuevo
		Integer ultimoTransaccionId = compraService.obtenerUltimaTransaccionIdPorUsuario(usuario.getId());
		int nuevoTransaccionId = (ultimoTransaccionId == null || ultimoTransaccionId == 0) ? 1
				: ultimoTransaccionId + 1;

		List<Compra> comprasRealizadas = new ArrayList<>();

		try {
			// Por cada película, verificar que no exista ya una compra para ese usuario y
			// crear un registro nuevo
			for (Peliculas p : peliculas) {
				Compra compraExistente = compraService.findByUsuarioIdAndPeliculaId(usuario.getId(), p.getId());
				if (compraExistente != null) {
					throw new IllegalArgumentException("La película '" + p.getTitulo() + "' ya está comprada.");
				}

				Compra nuevaCompra = new Compra();
				nuevaCompra.setUsuario(usuario);
				nuevaCompra.setPelicula(p);
				nuevaCompra.setTarjeta(tarjeta);
				nuevaCompra.setPrecio(p.getPrecio());
				nuevaCompra.setFechaCompra(LocalDateTime.now());
				nuevaCompra.setTransaccionId(nuevoTransaccionId); // Asigna el mismo transacción_id a todas

				Compra compraGuardada = compraService.save(nuevaCompra);
				comprasRealizadas.add(compraGuardada);

				// Restar el precio de cada película del saldo disponible de la tarjeta
				double nuevosFondos = Math.round((tarjeta.getFondosDisponibles() - p.getPrecio()) * 100) / 100.0;
				tarjeta.setFondosDisponibles(nuevosFondos);
			}

			// Actualizar la tarjeta con el nuevo saldo
			tarjetaService.save(tarjeta);

			// **Vaciar el carrito del usuario** para que no queden ítems comprados
			carritoService.vaciarCarrito(usuario.getId());

			response.put("mensaje", "La compra se realizó con éxito.");
			response.put("compras", comprasRealizadas);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			response.put("mensaje", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos.");
			response.put("error", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			response.put("mensaje", "Error al realizar la compra.");
			response.put("error", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/compras/estadisticas")
	public ResponseEntity<?> obtenerEstadisticas() {
		Map<String, Object> estadisticas = new HashMap<>();
		estadisticas.put("ventasHoy", compraService.obtenerComprasDelDia().size());
		estadisticas.put("ventasSemana", compraService.obtenerComprasSemanal().size());
		estadisticas.put("ventasMes", compraService.obtenerComprasMensual().size());
		estadisticas.put("ingresosTotales", compraService.obtenerIngresosTotales());
		estadisticas.put("promedioDiario", compraService.obtenerPromedioVentasDelMes());

		return ResponseEntity.ok(estadisticas);
	}
	
	@GetMapping("/compras/estadisticas2")
	public ResponseEntity<?> obtenerEstadisticas(@RequestParam(defaultValue = "DIARIO") String tipo) {
	    Map<String, Object> estadisticas = new HashMap<>();

	    switch (tipo.toUpperCase()) {
	        case "SEMANAL" -> {
	            estadisticas.put("ventasSemana", compraService.obtenerComprasSemanal().size());
	        }
	        case "MENSUAL" -> {
	            estadisticas.put("ventasMes", compraService.obtenerComprasMensual().size());
	        }
	        default -> {
	            estadisticas.put("ventasHoy", compraService.obtenerComprasDelDia().size());
	        }
	    }

	    estadisticas.put("ingresosTotales", compraService.obtenerIngresosTotales());
	    estadisticas.put("promedioDiario", compraService.obtenerPromedioVentasDelMes());

	    return ResponseEntity.ok(estadisticas);
	}
}
