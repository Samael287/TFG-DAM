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

import com.samael.springboot.backend.peliculas.models.dto.DirectorDTO;
import com.samael.springboot.backend.peliculas.models.entidades.Director;
import com.samael.springboot.backend.peliculas.models.services.IDirectorService;

@CrossOrigin({"*"})
@RestController
@RequestMapping("/api")
public class DirectoresRestController {

	@Autowired
	IDirectorService directoresService;
	
	@GetMapping("/directores")
	public List<DirectorDTO> todosLosDirectores(){
		
		List<Director> directores = directoresService.findAll();
		List<DirectorDTO> directoresDto = new ArrayList<>();
		
		directores.forEach(director -> {
			
			Set<String> peliculas = director.getPeliculas().stream()
					.map(pelicula -> pelicula.getTitulo())
					.collect(Collectors.toSet());
			
			DirectorDTO directorDto = new DirectorDTO();
			
			directorDto.setId(director.getId());
			directorDto.setName(director.getName());
			directorDto.setPeliculas(peliculas);
			
			directoresDto.add(directorDto);
		});
		
		return directoresDto;
	}
	
	@GetMapping("/directores/{id}")
	public ResponseEntity<?> unDirector(@PathVariable Integer id){
		
		Director director = null;
		Map<String, Object> respuesta = new HashMap<String, Object>();
		DirectorDTO directorDto = new DirectorDTO();
		
		try {
			director = directoresService.findById(id);
		}catch(Exception e) {
			respuesta.put("mensaje", "Error al realizar la consulta sobre la base de datos");
			respuesta.put("error", e.getMessage().concat(": ").concat(e.getStackTrace().toString()));
			return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(director == null) {
			respuesta.put("mensaje", "El identificador buscado no existe");
			return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.NOT_FOUND);
		}
		
		directorDto.setId(director.getId());
		directorDto.setName(director.getName());
		
		Set<String> peliculas = director.getPeliculas().stream()
				.map(pelicula -> pelicula.getTitulo())
				.collect(Collectors.toSet());
		
		directorDto.setPeliculas(peliculas);
		
		return new ResponseEntity<DirectorDTO>(directorDto, HttpStatus.OK);
	}
	
	@PostMapping("/directores")
	public ResponseEntity<?> create(@RequestBody Director director, BindingResult result){
		
		Director newDirector = null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			
			newDirector = directoresService.save(director);
			
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El director ha sido creado con éxito");
		response.put("director", newDirector);		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/directores/{id}")
	public ResponseEntity<?> update(@RequestBody Director director, BindingResult result, @PathVariable Integer id){
		
		Director directorActual = directoresService.findById(id);
		Director directorUpdated = null;
		
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(directorActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el director ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			directorActual.setName(director.getName());
			
			directorUpdated = directoresService.save(directorActual);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar el director en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El director ha sido actualizado con éxito.");
		response.put("director", directorUpdated);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/directores/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id){
		
		Director directorABorrar = directoresService.findById(id);		
		Map<String, Object> response = new HashMap<>();
		
		if(directorABorrar == null) {
			response.put("mensaje", "Error: no se pudo borrar, el director ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
			
		try {
			directoresService.delete(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al eliminar el director en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El director ha sido eliminado con éxito.");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);		
	}
}
