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

import com.samael.springboot.backend.peliculas.models.dto.GenresDTO;
import com.samael.springboot.backend.peliculas.models.entidades.Genres;
import com.samael.springboot.backend.peliculas.models.services.IGenresService;

@CrossOrigin({"*"})
@RestController
@RequestMapping("/api")
public class GenresRestController {

	@Autowired
	private IGenresService genresService;
	
	@GetMapping("/genres")
	public List<GenresDTO> todosLosGenres(){
		
		List<Genres> genres = genresService.findAll();
		List<GenresDTO> genresDto = new ArrayList<>();
		
		genres.forEach(genre -> {
			
			Set<String> peliculas = genre.getPeliculas().stream()
					.map(pelicula -> pelicula.getTitulo())
					.collect(Collectors.toSet());
			
			GenresDTO genreDto = new GenresDTO();
			
			genreDto.setId(genre.getId());
			genreDto.setGenre(genre.getGenre());
			genreDto.setPeliculas(peliculas);
			
			genresDto.add(genreDto);
		});
		
		return genresDto;
	}
	
	@GetMapping("/genres/{id}")
	public ResponseEntity<?> unGenre(@PathVariable Integer id){
		
		Genres genre = null;
		Map<String, Object> respuesta = new HashMap<String, Object>();
		GenresDTO genreDto = new GenresDTO();

		try {
			genre = genresService.findById(id);
		}catch(Exception e) {
			respuesta.put("mensaje", "Error al realizar la consulta sobre la base de datos");
			respuesta.put("error", e.getMessage().concat(": ").concat(e.getStackTrace().toString()));
			return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(genre == null) {
			respuesta.put("mensaje", "El identificador buscado no existe");
			return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.NOT_FOUND);
		}
		
		genreDto.setId(genre.getId());
		genreDto.setGenre(genre.getGenre());
		
		Set<String> peliculas = genre.getPeliculas().stream()
				.map(pelicula -> pelicula.getTitulo())
				.collect(Collectors.toSet());
		genreDto.setPeliculas(peliculas);
		
		return new ResponseEntity<GenresDTO>(genreDto,  HttpStatus.OK);
	}
	
	@PostMapping("/genres")
	public ResponseEntity<?> create(@RequestBody Genres genre, BindingResult result){
		
		Genres newGenre = null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			
			newGenre = genresService.save(genre);
			
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El genre ha sido creado con éxito");
		response.put("genre", newGenre);		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/genres/{id}")
	public ResponseEntity<?> update(@RequestBody Genres genre, BindingResult result, @PathVariable Integer id){
		
		Genres genreActual = genresService.findById(id);
		Genres genreUpdated = null;
		
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(genreActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el genre ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			genreActual.setGenre(genre.getGenre());
			
			genreUpdated = genresService.save(genreActual);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar el genre en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El genre ha sido actualizado con éxito.");
		response.put("genre", genreUpdated);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/genres/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id){
		
		Genres genreABorrar = genresService.findById(id);
		Map<String, Object> response = new HashMap<>();
		
		if(genreABorrar == null) {
			response.put("mensaje", "Error: no se pudo borrar, el genre ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
			
		try {
			genresService.delete(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al eliminar el genre en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El genre ha sido eliminado con éxito.");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);		
	}
}
