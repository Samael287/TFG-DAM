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

import com.samael.springboot.backend.peliculas.models.dto.ActorDTO;
import com.samael.springboot.backend.peliculas.models.entidades.Actor;
import com.samael.springboot.backend.peliculas.models.services.IActorService;

@CrossOrigin({"*"})
@RestController
@RequestMapping("/api")
public class ActoresRestController {
	
	@Autowired
	private IActorService actoresService;
	
	@GetMapping("/actores")
	public List<ActorDTO> todosLosActores(){
		
		List<Actor> actores = actoresService.findAll();
		List<ActorDTO> actoresDto = new ArrayList<>();
		
		actores.forEach(actor -> {
			
			Set<String> peliculas = actor.getPeliculas().stream()
					.map(pelicula -> pelicula.getTitulo())
					.collect(Collectors.toSet());
			
			ActorDTO actorDto = new ActorDTO();
			
			actorDto.setId(actor.getId());
			actorDto.setName(actor.getName());
			actorDto.setPeliculas(peliculas);
			
			actoresDto.add(actorDto);
		});
		
		return actoresDto;
	}
	
	@GetMapping("/actores/{id}")
	public ResponseEntity<?> unActor(@PathVariable Integer id){
		
		Actor actor = null;
		Map<String, Object> respuesta = new HashMap<String, Object>();
		ActorDTO actorDto = new ActorDTO();
		
		try {
			actor = actoresService.findById(id);
		}catch(Exception e) {
			respuesta.put("mensaje", "Error al realizar la consulta sobre la base de datos");
			respuesta.put("error", e.getMessage().concat(": ").concat(e.getStackTrace().toString()));
			return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(actor == null) {
			respuesta.put("mensaje", "El identificador buscado no existe");
			return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.NOT_FOUND);
		}
		
		actorDto.setId(actor.getId());
		actorDto.setName(actor.getName());
		
		Set<String> peliculas = actor.getPeliculas().stream()
				.map(pelicula -> pelicula.getTitulo())
				.collect(Collectors.toSet());
		
		actorDto.setPeliculas(peliculas);
		
		return new ResponseEntity<ActorDTO>(actorDto, HttpStatus.OK);
	}
	
	@PostMapping("/actores")
	public ResponseEntity<?> create(@RequestBody Actor actor, BindingResult result){
		
		Actor newActor = null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		try {

			newActor = actoresService.save(actor);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El actor ha sido creado con éxito.");
		response.put("actor", newActor);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/actores/{id}")
	public ResponseEntity<?> update(@RequestBody Actor actor, BindingResult result, @PathVariable Integer id){
		
		Actor actorActual = actoresService.findById(id);
		Actor actorUpdated = null;
		
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (actorActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el actor ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
			
		try {
			
			actorActual.setName(actor.getName());
			/*Set<Peliculas> peliculasActualizadas = actor.getPeliculas().stream()
					.map(pelicula -> peliculasService.findById(pelicula.getId()))
	                .filter(Objects::nonNull) // ⚠️ Evitar películas no existentes
					.collect(Collectors.toSet());*/
			
			actorUpdated = actoresService.save(actorActual); 
			
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar el actor en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		ActorDTO actordto = new ActorDTO();
		
		actordto.setId(actorUpdated.getId());
		actordto.setName(actorUpdated.getName());
		
		Set<String> peliculas = actorUpdated.getPeliculas().stream()
				.map(pelicula -> pelicula.getTitulo())
				.collect(Collectors.toSet());
		actordto.setPeliculas(peliculas);
		
		response.put("mensaje", "El actor ha sido actualizado con éxito.");
		response.put("actor", actorUpdated);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/actores/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id){
		
		Actor actorABorrar = actoresService.findById(id);
		Map<String, Object> response = new HashMap<>();

		if(actorABorrar == null) {
			response.put("mensaje", "Error: no se pudo borrar, el actor ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
			
		try {
			actoresService.delete(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al eliminar el actor en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El actor ha sido eliminado con éxito.");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);		
	}
}
