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

import com.samael.springboot.backend.peliculas.models.dto.LanguageDTO;
import com.samael.springboot.backend.peliculas.models.entidades.Language;
import com.samael.springboot.backend.peliculas.models.services.ILanguageService;

@CrossOrigin({"*"})
@RestController
@RequestMapping("/api")
public class LanguageRestController {

	@Autowired
	private ILanguageService languageService;
	
	@GetMapping("/languages")
	public List<LanguageDTO> todosLosLanguages(){
		
		List<Language> languages = languageService.findAll();
		List<LanguageDTO> languagesDto = new ArrayList<>();
		
		languages.forEach(language -> {
			
			Set<String> peliculas = language.getPeliculas().stream()
					.map(pelicula -> pelicula.getTitulo())
					.collect(Collectors.toSet());
			
			LanguageDTO languageDto = new LanguageDTO();
			
			languageDto.setId(language.getId());
			languageDto.setLanguage(language.getLanguage());
			languageDto.setPeliculas(peliculas);
			
			languagesDto.add(languageDto);
		});
		
		return languagesDto;
	}
	
	@GetMapping("/languages/{id}")
	public ResponseEntity<?> unLanguage(@PathVariable Integer id){
		
		Language language = null;
		Map<String, Object> respuesta = new HashMap<String, Object>();
		LanguageDTO languageDto = new LanguageDTO();
		
		try {
			language = languageService.findById(id);
		}catch(Exception e) {
			respuesta.put("mensaje", "Error al realizar la consulta sobre la base de datos");
			respuesta.put("error", e.getMessage().concat(": ").concat(e.getStackTrace().toString()));
			return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(language == null) {
			respuesta.put("mensaje", "El identificador buscado no existe");
			return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.NOT_FOUND);
		}
		
		languageDto.setId(language.getId());
		languageDto.setLanguage(language.getLanguage());
		
		Set<String> peliculas = language.getPeliculas().stream()
				.map(pelicula -> pelicula.getTitulo())
				.collect(Collectors.toSet());
		languageDto.setPeliculas(peliculas);
		
		return new ResponseEntity<LanguageDTO>(languageDto,  HttpStatus.OK);
	}
	
	@PostMapping("/languages")
	public ResponseEntity<?> create(@RequestBody Language language, BindingResult result){
		
		Language newLanguage = null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			
			newLanguage = languageService.save(language);
			
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El language ha sido creado con éxito");
		response.put("language", newLanguage);		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/languages/{id}")
	public ResponseEntity<?> update(@RequestBody Language language, BindingResult result, @PathVariable Integer id){
		
		Language languageActual = languageService.findById(id);
		Language languageUpdated = null;
		
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(languageActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el language ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			languageActual.setLanguage(language.getLanguage());
			
			languageUpdated = languageService.save(languageActual);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar el language en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El language ha sido actualizado con éxito.");
		response.put("language", languageUpdated);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/languages/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id){
		
		Language languageABorrar = languageService.findById(id);
		Map<String, Object> response = new HashMap<>();
		
		if(languageABorrar == null) {
			response.put("mensaje", "Error: no se pudo borrar, el language ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
			
		try {
			languageService.delete(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al eliminar el language en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El language ha sido eliminado con éxito.");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);		
	}
}
