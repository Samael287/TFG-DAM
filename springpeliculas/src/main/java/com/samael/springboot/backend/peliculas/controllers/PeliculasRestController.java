package com.samael.springboot.backend.peliculas.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samael.springboot.backend.peliculas.models.dto.PeliculasDTO;
import com.samael.springboot.backend.peliculas.models.entidades.Compra;
import com.samael.springboot.backend.peliculas.models.entidades.Peliculas;
import com.samael.springboot.backend.peliculas.models.services.ICompraService;
import com.samael.springboot.backend.peliculas.models.services.IPeliculasService;
import com.samael.springboot.backend.peliculas.utils.ImageUtils;

@CrossOrigin({"*"})
@RestController
@RequestMapping("/api")
public class PeliculasRestController {

	@Autowired
	private IPeliculasService peliculasService;
	
	@Autowired
	ICompraService compraService;
	
	@Autowired
	private ImageUtils imageUtils;

	@GetMapping("/peliculas")
	public List<PeliculasDTO> todasLasPeliculas() {
		List<Peliculas> peliculas = peliculasService.findAll();
		List<PeliculasDTO> peliculasDTO = new ArrayList<>();

		peliculas.forEach(pelicula -> {

			String category = pelicula.getCategory() != null ? pelicula.getCategory().getCategory()
					: "No hay categoria asignada";

			String studio = pelicula.getStudio() != null ? pelicula.getStudio().getStudio() : "No hay estudio asignado";

			String fecha_estreno = pelicula.getFecha_estreno() != null ? pelicula.getFecha_estreno().toString()
					: "No hay fecha de estreno";

			Set<String> actores = pelicula.getActores().stream()
					.map(actor -> actor.getName())
					.collect(Collectors.toSet());

			Set<String> directores = pelicula.getDirectores().stream()
					.map(director -> director.getName())
					.collect(Collectors.toSet());

			Set<String> flags = pelicula.getFlagses().stream()
					.map(flag -> flag.getFlags())
					.collect(Collectors.toSet());

			Set<String> genres = pelicula.getGenres().stream()
					.map(genre -> genre.getGenre())
					.collect(Collectors.toSet());

			Set<String> languages = pelicula.getLanguages().stream()
					.map(language -> language.getLanguage())
					.collect(Collectors.toSet());

			PeliculasDTO peliculasdto = new PeliculasDTO();

			peliculasdto.setId(pelicula.getId());
			peliculasdto.setTitulo(pelicula.getTitulo());
			peliculasdto.setDescripcion(pelicula.getDescripcion());
			peliculasdto.setFecha_estreno(fecha_estreno);
			peliculasdto.setDuracion(pelicula.getDuracion());
			peliculasdto.setPrecio(pelicula.getPrecio());
			peliculasdto.setPortada(pelicula.getPortada());
			peliculasdto.setStudio(studio);
			peliculasdto.setCategory(category);
			peliculasdto.setActores(actores);
			peliculasdto.setDirectores(directores);
			peliculasdto.setFlagses(flags);
			peliculasdto.setGenres(genres);
			peliculasdto.setLanguages(languages);

			peliculasDTO.add(peliculasdto);

		});
		return peliculasDTO;
	}

	@GetMapping("/peliculas/{id}")
	public ResponseEntity<?> unaPelicula(@PathVariable Integer id) {

		Peliculas pelicula = null;
		Map<String, Object> respuesta = new HashMap<String, Object>();
		PeliculasDTO peliculasdto = new PeliculasDTO();

		try {
			pelicula = peliculasService.findById(id);
		} catch (Exception e) { // Base de datos inaccesible
			respuesta.put("mensaje", "Error al realizar la consulta sobre la base de datos");
			respuesta.put("error", e.getMessage().concat(": ").concat(e.getStackTrace().toString()));
			return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (pelicula == null) { // Hemos buscado un id que no existe
			respuesta.put("mensaje", "El identificador buscado no existe");
			return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.NOT_FOUND);
		}

		peliculasdto.setId(pelicula.getId());
		peliculasdto.setTitulo(pelicula.getTitulo());
		peliculasdto.setDescripcion(pelicula.getDescripcion());
		peliculasdto.setFecha_estreno(pelicula.getFecha_estreno().toString());
		peliculasdto.setDuracion(pelicula.getDuracion());
		peliculasdto.setPrecio(pelicula.getPrecio());
		peliculasdto.setPortada(pelicula.getPortada());
		peliculasdto.setStudio(pelicula.getStudio() != null ? pelicula.getStudio().getStudio() : null);
		peliculasdto.setCategory(pelicula.getCategory() != null ? pelicula.getCategory().getCategory() : null);

		Set<String> actores = pelicula.getActores().stream()
				.map(actor -> actor.getName())
				.collect(Collectors.toSet());
		peliculasdto.setActores(actores);

		Set<String> directores = pelicula.getDirectores().stream()
				.map(director -> director.getName())
				.collect(Collectors.toSet());
		peliculasdto.setDirectores(directores);

		Set<String> flags = pelicula.getFlagses().stream()
				.map(flag -> flag.getFlags())
				.collect(Collectors.toSet());
		peliculasdto.setFlagses(flags);

		Set<String> genres = pelicula.getGenres().stream()
				.map(genre -> genre.getGenre())
				.collect(Collectors.toSet());
		peliculasdto.setGenres(genres);

		Set<String> languages = pelicula.getLanguages().stream()
				.map(language -> language.getLanguage())
				.collect(Collectors.toSet());
		peliculasdto.setLanguages(languages);

		return new ResponseEntity<PeliculasDTO>(peliculasdto, HttpStatus.OK);
	}

	@PostMapping("/peliculas")
	public ResponseEntity<?> create(@RequestBody Peliculas pelicula, BindingResult result) {

		Peliculas newPelicula = null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (pelicula.getPortada() != null && !pelicula.getPortada().isEmpty()) {
	        String imagePath = imageUtils.saveImageBase64("peliculas", pelicula.getPortada());
	        pelicula.setPortada(imagePath); // Guardamos la URL en la base de datos
	    }
		
		try {

			newPelicula = peliculasService.save(pelicula);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La película ha sido creada con éxito.");
		response.put("pelicula", newPelicula);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@PutMapping("/peliculas/{id}")
	public ResponseEntity<?> update(@RequestBody Peliculas pelicula, BindingResult result, @PathVariable Integer id) {

		Peliculas peliculaActual = peliculasService.findById(id);
		Peliculas peliculaUpdated = null;

		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (peliculaActual == null) {
			response.put("mensaje", "Error: no se pudo editar, la película ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
		
			peliculaActual.setTitulo(pelicula.getTitulo());
			peliculaActual.setDescripcion(pelicula.getDescripcion());
			peliculaActual.setFecha_estreno(pelicula.getFecha_estreno());
			peliculaActual.setDuracion(pelicula.getDuracion());
			peliculaActual.setPrecio(pelicula.getPrecio());
			peliculaActual.setPortada(pelicula.getPortada());
			peliculaActual.setStudio(pelicula.getStudio());
			peliculaActual.setCategory(pelicula.getCategory());
			peliculaActual.setActores(pelicula.getActores());
			peliculaActual.setDirectores(pelicula.getDirectores());
			peliculaActual.setFlagses(pelicula.getFlagses());
			peliculaActual.setGenres(pelicula.getGenres());
			peliculaActual.setLanguages(pelicula.getLanguages());
			
			peliculaUpdated = peliculasService.save(peliculaActual);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar la película en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		PeliculasDTO peliculasdto = new PeliculasDTO();
		peliculasdto.setId(peliculaUpdated.getId());
		peliculasdto.setId(peliculaUpdated.getId());
		peliculasdto.setTitulo(peliculaUpdated.getTitulo());
		peliculasdto.setDescripcion(peliculaUpdated.getDescripcion());
		peliculasdto.setFecha_estreno(peliculaUpdated.getFecha_estreno().toString());
		peliculasdto.setDuracion(peliculaUpdated.getDuracion());
		peliculasdto.setPrecio(peliculaUpdated.getPrecio());
		peliculasdto.setPortada(peliculaUpdated.getPortada());
		peliculasdto.setStudio(peliculaUpdated.getStudio() != null ? peliculaUpdated.getStudio().getStudio() : null);
		peliculasdto.setCategory(peliculaUpdated.getCategory() != null ? peliculaUpdated.getCategory().getCategory() : null);

		Set<String> actores = peliculaUpdated.getActores().stream()
				.map(actor -> actor.getName())
				.collect(Collectors.toSet());
		peliculasdto.setActores(actores);

		Set<String> directores = peliculaUpdated.getDirectores().stream()
				.map(director -> director.getName())
				.collect(Collectors.toSet());
		peliculasdto.setDirectores(directores);

		Set<String> flags = peliculaUpdated.getFlagses().stream()
				.map(flag -> flag.getFlags())
				.collect(Collectors.toSet());
		peliculasdto.setFlagses(flags);

		Set<String> genres = peliculaUpdated.getGenres().stream()
				.map(genre -> genre.getGenre())
				.collect(Collectors.toSet());
		peliculasdto.setGenres(genres);

		Set<String> languages = peliculaUpdated.getLanguages().stream()
				.map(language -> language.getLanguage())
				.collect(Collectors.toSet());
		peliculasdto.setLanguages(languages);

		response.put("mensaje", "La pelicula ha sido actualizada con éxito.");
		response.put("pelicula", peliculaUpdated);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/peliculas/{id}") //¡¡¡¡¡¡¡IMPORTANTE MIRAR EL BORRADO SUAVE POR LOS PAGOS!!!!!!!
	public ResponseEntity<?> delete(@PathVariable Integer id){
		
		Peliculas peliculaABorrar = peliculasService.findById(id);
		
		Map<String, Object> response = new HashMap<>();

		if(peliculaABorrar == null) {
			response.put("mensaje", "Error: no se pudo borrar, la película ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
			
		try {
			
			// ✅ 1. Buscar si el usuario tiene compras antes de hacer cambios en la base de datos
	        List<Compra> comprasDepelicula = compraService.obtenerCompraPorPelicula(id);

	        if (!comprasDepelicula.isEmpty()) { // Solo modificamos compras si hay alguna
	            for (Compra compra : comprasDepelicula) {
	                compra.setPeliculaBorradaId(id); // Guardamos el ID del usuario eliminado
	                compra.setPelicula(null); // Desasociamos al usuario
	                compraService.save(compra); // Guardamos la compra con el ID del usuario borrado
	            }
	        }
			
			peliculasService.delete(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al eliminar la película en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La película ha sido eliminada con éxito.");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);		
	}
}
