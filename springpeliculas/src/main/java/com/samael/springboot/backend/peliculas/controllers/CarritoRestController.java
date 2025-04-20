package com.samael.springboot.backend.peliculas.controllers;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samael.springboot.backend.peliculas.models.dto.CarritoDTO;
import com.samael.springboot.backend.peliculas.models.dto.PeliculasDTO;
import com.samael.springboot.backend.peliculas.models.dto.UsuariosDTO;
import com.samael.springboot.backend.peliculas.models.entidades.Carrito;
import com.samael.springboot.backend.peliculas.models.entidades.Peliculas;
import com.samael.springboot.backend.peliculas.models.entidades.Usuarios;
import com.samael.springboot.backend.peliculas.models.services.ICarritoService;
import com.samael.springboot.backend.peliculas.models.services.IPeliculasService;
import com.samael.springboot.backend.peliculas.models.services.IUsuarioService;

@CrossOrigin({"*"})
@RestController
@RequestMapping("/api")
public class CarritoRestController {

	@Autowired
	private ICarritoService carritoService;

	@Autowired
	private IUsuarioService usuarioService;

	// Asegúrate de tener un servicio para películas, o bien usa el DAO directamente
	// si lo prefieres.
	@Autowired
	private IPeliculasService peliculasService;

	@GetMapping("/carritos")
	public List<CarritoDTO> todosLosItems() {

		List<Carrito> carritos = carritoService.findAll();
		List<CarritoDTO> carritoDtoList = new ArrayList<>();

		for (Carrito carrito : carritos) {
			CarritoDTO carritoDto = new CarritoDTO();

			carritoDto.setId(carrito.getId());
			if (carrito.getUsuario() != null) {
				UsuariosDTO usuarioDto = new UsuariosDTO();
				usuarioDto.setId(carrito.getUsuario().getId());
				usuarioDto.setNombre(carrito.getUsuario().getNombre());

				carritoDto.setUsuario(usuarioDto);
			}

			if (carrito.getPelicula() != null) {
				PeliculasDTO peliculaDto = new PeliculasDTO();
				peliculaDto.setId(carrito.getPelicula().getId());
				peliculaDto.setTitulo(carrito.getPelicula().getTitulo());
				peliculaDto.setPrecio(carrito.getPelicula().getPrecio());

				carritoDto.setPelicula(peliculaDto);
			}

			carritoDtoList.add(carritoDto);
		}
		return carritoDtoList;
	}

	@GetMapping("/carritos/{id}")
	public ResponseEntity<?> unItem(@PathVariable Integer id) {
		Carrito carrito = null;
		CarritoDTO carritoDto = new CarritoDTO();

		Map<String, Object> respuesta = new HashMap<String, Object>();

		try {
			carrito = carritoService.findById(id);
		} catch (Exception e) { // Base de datos inaccesible
			respuesta.put("mensaje", "Error al realizar la consulta sobre la base de datos");
			respuesta.put("error", e.getMessage().concat(": ").concat(e.getStackTrace().toString()));
			return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (carrito == null) { // Hemos buscado un id que no existe
			respuesta.put("mensaje", "El identificador buscado no existe");
			return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.NOT_FOUND);
		}

		carritoDto.setId(carrito.getId());

		if (carrito.getUsuario() != null) {
			UsuariosDTO usuarioDto = new UsuariosDTO();
			usuarioDto.setId(carrito.getUsuario().getId());
			usuarioDto.setNombre(carrito.getUsuario().getNombre());

			carritoDto.setUsuario(usuarioDto);
		}

		if (carrito.getPelicula() != null) {
			PeliculasDTO peliculaDto = new PeliculasDTO();
			peliculaDto.setId(carrito.getPelicula().getId());
			peliculaDto.setTitulo(carrito.getPelicula().getTitulo());
			peliculaDto.setPrecio(carrito.getPelicula().getPrecio());

			carritoDto.setPelicula(peliculaDto);
		}

		return new ResponseEntity<CarritoDTO>(carritoDto, HttpStatus.OK);
	}

	// Obtener el carrito completo de un usuario
	@GetMapping("/carritos/usuario/{usuarioId}")
	public ResponseEntity<?> obtenerCarritoPorUsuario(@PathVariable Integer usuarioId) {
		List<Carrito> carritoItems = carritoService.obtenerCarritoPorUsuario(usuarioId);
		return new ResponseEntity<>(carritoItems, HttpStatus.OK);
	}

	@PostMapping("/carritos")
	public ResponseEntity<?> agregarAlCarrito(@RequestBody Carrito carrito, BindingResult result) {
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		// Validamos que se haya enviado el ID del usuario y de la película
		if (carrito.getUsuario() == null || carrito.getPelicula() == null) {
			response.put("mensaje", "Debe enviar el id del usuario y de la película.");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		Usuarios usuario = usuarioService.findById(carrito.getUsuario().getId());
		Peliculas pelicula = peliculasService.findById(carrito.getPelicula().getId());

		if (usuario == null || pelicula == null) {
			response.put("mensaje", "Usuario o película no encontrados.");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		try {
			Carrito nuevoCarrito = carritoService.agregarAlCarrito(usuario, pelicula);
			response.put("mensaje", "La película ha sido añadida al carrito con éxito.");
			response.put("carrito", nuevoCarrito);
			// Si se necesitara devolver el total de items en el carrito:
			response.put("cantidadTotal", carritoService.obtenerCantidadTotal(usuario.getId()));
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			response.put("mensaje", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos.");
			response.put("error", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			response.put("mensaje", "Error al añadir al carrito.");
			response.put("error", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ✅ Obtener cantidad total de películas en el carrito de un usuario
	@GetMapping("/carritos/cantidad/{usuarioId}")
	public ResponseEntity<?> obtenerCantidadTotal(@PathVariable Integer usuarioId) {
		int cantidadTotal = carritoService.obtenerCantidadTotal(usuarioId);
		return ResponseEntity.ok().body(Map.of("cantidadTotal", cantidadTotal));
	}

	// Eliminar un item específico del carrito (por su ID)
	@DeleteMapping("/carritos/{id}")
	public ResponseEntity<?> eliminarDelCarrito(@PathVariable Integer id) {
		Carrito itemABorrar = carritoService.findById(id);
		Map<String, Object> response = new HashMap<>();

		if (itemABorrar == null) {
			response.put("mensaje", "Error: no se pudo borrar, el Item con ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {

			carritoService.delete(id);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el item en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El item ha sido eliminado con éxito.");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	// Vaciar el carrito completo de un usuario
	@DeleteMapping("/carritos/vaciar/{usuarioId}")
	public ResponseEntity<?> vaciarCarrito(@PathVariable Integer usuarioId) {
		carritoService.vaciarCarrito(usuarioId);
		return new ResponseEntity<>("Carrito vaciado.", HttpStatus.OK);
	}

	@DeleteMapping("/carritos/usuario/{usuarioId}/pelicula/{peliculaId}")
	public ResponseEntity<?> eliminarPeliculaDelCarrito(@PathVariable Integer usuarioId,
			@PathVariable Integer peliculaId) {
		Carrito item = carritoService.findByUsuarioIdAndPeliculaId(usuarioId, peliculaId);
		Map<String, Object> response = new HashMap<>();

		if (item == null) {
			response.put("mensaje", "Error: No se encontró el item en el carrito para el usuario " + usuarioId
					+ " y la película " + peliculaId);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		try {
			carritoService.delete(item.getId());
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el item del carrito.");
			response.put("error", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "La película ha sido eliminada del carrito con éxito.");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
