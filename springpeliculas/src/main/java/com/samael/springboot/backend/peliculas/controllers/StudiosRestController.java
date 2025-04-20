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

import com.samael.springboot.backend.peliculas.models.dto.StudiosDTO;
import com.samael.springboot.backend.peliculas.models.entidades.Studios;
import com.samael.springboot.backend.peliculas.models.services.IStudiosService;

@CrossOrigin({"*"})
@RestController
@RequestMapping("/api")
public class StudiosRestController {

	@Autowired
	private IStudiosService studiosService;
	
	@GetMapping("/studios")
	public List<StudiosDTO> todosLosStudios(){
		
		List<Studios> studios = studiosService.findAll();
		List<StudiosDTO> studiosDto = new ArrayList<>();
		
		studios.forEach(studio -> {
			
			Set<String> peliculas = studio.getPeliculas().stream()
					.map(pelicula -> pelicula.getTitulo())
					.collect(Collectors.toSet());
			
			StudiosDTO studioDto = new StudiosDTO();
			
			studioDto.setId(studio.getId());
			studioDto.setStudio(studio.getStudio());
			studioDto.setPeliculas(peliculas);
			
			studiosDto.add(studioDto);
		});
		
		return studiosDto;
	}
	
	@GetMapping("/studios/{id}")
	public ResponseEntity<?> unStudio(@PathVariable Integer id){
		
		Studios studio = null;
		Map<String, Object> respuesta = new HashMap<String, Object>();
		StudiosDTO studioDto = new StudiosDTO();
		
		try {
			studio = studiosService.findById(id);
		}catch(Exception e) {
			respuesta.put("mensaje", "Error al realizar la consulta sobre la base de datos");
			respuesta.put("error", e.getMessage().concat(": ").concat(e.getStackTrace().toString()));
			return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(studio == null) {
			respuesta.put("mensaje", "El identificador buscado no existe");
			return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.NOT_FOUND);
		}
		
		studioDto.setId(studio.getId());
		studioDto.setStudio(studio.getStudio());
		
		Set<String> peliculas = studio.getPeliculas().stream()
				.map(pelicula -> pelicula.getTitulo())
				.collect(Collectors.toSet());
		studioDto.setPeliculas(peliculas);
		
		return new ResponseEntity<StudiosDTO>(studioDto,  HttpStatus.OK);
	}
	
	@PostMapping("/studios")
	public ResponseEntity<?> create(@RequestBody Studios studio, BindingResult result){
		
		Studios newStudio = null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			
			newStudio = studiosService.save(studio);
			
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El studio ha sido creado con éxito");
		response.put("studio", newStudio);		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/studios/{id}")
	public ResponseEntity<?> update(@RequestBody Studios studio, BindingResult result, @PathVariable Integer id){
		
		Studios studioActual = studiosService.findById(id);
		Studios studioUpdated = null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(studioActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el studio ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			studioActual.setStudio(studio.getStudio());
			
			studioUpdated = studiosService.save(studioActual);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar el studio en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El studio ha sido actualizado con éxito.");
		response.put("studio", studioUpdated);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/studios/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id){
		
		Studios studioABorrar = studiosService.findById(id);
		Map<String, Object> response = new HashMap<>();
		
		if(studioABorrar == null) {
			response.put("mensaje", "Error: no se pudo borrar, el studio ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
			
		try {
			studiosService.delete(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al eliminar el studio en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El studio ha sido eliminado con éxito.");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);		
	}
}
