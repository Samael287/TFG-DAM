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

import com.samael.springboot.backend.peliculas.models.dto.CategoriesDTO;
import com.samael.springboot.backend.peliculas.models.entidades.Categories;
import com.samael.springboot.backend.peliculas.models.services.ICategoriesService;

@CrossOrigin({"*"})
@RestController
@RequestMapping("/api")
public class CategoriesRestController {

	@Autowired
	private ICategoriesService categoriesService;
	
	@GetMapping("/categories")
	public List<CategoriesDTO> todasLasCategories(){
		
		List<Categories> categories = categoriesService.findAll();
		List<CategoriesDTO> categoriesDto = new ArrayList<>();
		
		categories.forEach(category -> {
			
			Set<String> peliculas = category.getPeliculas().stream()
					.map(pelicula -> pelicula.getTitulo())
					.collect(Collectors.toSet());
			
			CategoriesDTO categoryDto = new CategoriesDTO();
			
			categoryDto.setId(category.getId());
			categoryDto.setCategory(category.getCategory());
			categoryDto.setPeliculas(peliculas);
			
			categoriesDto.add(categoryDto);
		});
		
		return categoriesDto;
	}
	
	@GetMapping("/categories/{id}")
	public ResponseEntity<?> unaCategory(@PathVariable Integer id){
		
		Categories category = null;
		Map<String, Object> respuesta = new HashMap<String, Object>();
		CategoriesDTO categoryDto = new CategoriesDTO();
		
		try {
			category = categoriesService.findById(id);
		}catch(Exception e) {
			respuesta.put("mensaje", "Error al realizar la consulta sobre la base de datos");
			respuesta.put("error", e.getMessage().concat(": ").concat(e.getStackTrace().toString()));
			return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(category == null) {
			respuesta.put("mensaje", "El identificador buscado no existe");
			return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.NOT_FOUND);
		}
		
		categoryDto.setId(category.getId());
		categoryDto.setCategory(category.getCategory());
		
		Set<String> peliculas = category.getPeliculas().stream()
				.map(pelicula -> pelicula.getTitulo())
				.collect(Collectors.toSet());
		
		categoryDto.setPeliculas(peliculas);
		
		return new ResponseEntity<CategoriesDTO>(categoryDto, HttpStatus.OK);
	}
	
	@PostMapping("/categories")
	public ResponseEntity<?> create(@RequestBody Categories category, BindingResult result){
		
		Categories newCategory = null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		try {

			newCategory = categoriesService.save(category);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La category ha sido creado con éxito.");
		response.put("category", newCategory);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/categories/{id}")
	public ResponseEntity<?> update(@RequestBody Categories category, BindingResult result, @PathVariable Integer id){
		
		Categories categoryActual = categoriesService.findById(id);
		Categories categoryUpdated = null;
		
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (categoryActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el actor ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
			
		try {
			
			categoryActual.setCategory(category.getCategory());
			/*Set<Peliculas> peliculasActualizadas = actor.getPeliculas().stream()
					.map(pelicula -> peliculasService.findById(pelicula.getId()))
	                .filter(Objects::nonNull) // ⚠️ Evitar películas no existentes
					.collect(Collectors.toSet());*/
			
			categoryUpdated = categoriesService.save(categoryActual); 
			
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar el actor en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		CategoriesDTO categoriesdto = new CategoriesDTO();
		
		categoriesdto.setId(categoryUpdated.getId());
		categoriesdto.setCategory(categoryUpdated.getCategory());
		
		Set<String> peliculas = categoryUpdated.getPeliculas().stream()
				.map(pelicula -> pelicula.getTitulo())
				.collect(Collectors.toSet());
		categoriesdto.setPeliculas(peliculas);
		
		response.put("mensaje", "La category ha sido actualizada con éxito.");
		response.put("category", categoryUpdated);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/categories/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id){
		
		Categories categoryABorrar = categoriesService.findById(id);
		Map<String, Object> response = new HashMap<>();

		if(categoryABorrar == null) {
			response.put("mensaje", "Error: no se pudo borrar, la category ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
			
		try {
			categoriesService.delete(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al eliminar la category en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La category ha sido eliminado con éxito.");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);		
	}
}
