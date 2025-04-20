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

import com.samael.springboot.backend.peliculas.models.dto.FlagsDTO;
import com.samael.springboot.backend.peliculas.models.entidades.Flags;
import com.samael.springboot.backend.peliculas.models.services.IFlagsService;

@CrossOrigin({"*"})
@RestController
@RequestMapping("/api")
public class FlagsRestController {

	@Autowired
	private IFlagsService flagsService;
	
	@GetMapping("/flags")
	public List<FlagsDTO> todasLasFlags(){
		
		List<Flags> flags = flagsService.findAll();
		List<FlagsDTO> flagsDto = new ArrayList<>();
		
		flags.forEach(flag -> {
			
			Set<String> peliculas = flag.getPeliculas().stream()
					.map(pelicula -> pelicula.getTitulo())
					.collect(Collectors.toSet());
			
			FlagsDTO flagDto = new FlagsDTO();
			
			flagDto.setId(flag.getId());
			flagDto.setFlags(flag.getFlags());
			flagDto.setPeliculas(peliculas);
			
			flagsDto.add(flagDto);
		});

		return flagsDto;		
	}
	
	@GetMapping("/flags/{id}")
	public ResponseEntity<?> unaFlag(@PathVariable Integer id){
		
		Flags flag = null;
		Map<String, Object> respuesta = new HashMap<String, Object>();
		FlagsDTO flagDto = new FlagsDTO();
		
		try {
			flag = flagsService.findById(id);
		}catch(Exception e) {
			respuesta.put("mensaje", "Error al realizar la consulta sobre la base de datos");
			respuesta.put("error", e.getMessage().concat(": ").concat(e.getStackTrace().toString()));
			return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(flag == null) {
			respuesta.put("mensaje", "El identificador buscado no existe");
			return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.NOT_FOUND);
		}
		
		flagDto.setId(flag.getId());
		flagDto.setFlags(flag.getFlags());
		
		Set<String> peliculas = flag.getPeliculas().stream()
				.map(pelicula -> pelicula.getTitulo())
				.collect(Collectors.toSet());
		flagDto.setPeliculas(peliculas);
		
		return new ResponseEntity<FlagsDTO>(flagDto,  HttpStatus.OK);
	}
	
	@PostMapping("/flags")
	public ResponseEntity<?> create(@RequestBody Flags flag, BindingResult result){
		
		Flags newFlag = null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			
			newFlag = flagsService.save(flag);
			
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La flag ha sido creado con éxito");
		response.put("flag", newFlag);		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/flags/{id}")
	public ResponseEntity<?> update(@RequestBody Flags flag, BindingResult result, @PathVariable Integer id){
		
		Flags flagActual = flagsService.findById(id);
		Flags flagUpdated = null;
		
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(flagActual == null) {
			response.put("mensaje", "Error: no se pudo editar, la flag ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			flagActual.setFlags(flag.getFlags());
			
			flagUpdated = flagsService.save(flagActual);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar la flag en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La flag ha sido actualizado con éxito.");
		response.put("flag", flagUpdated);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/flags/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id){
		
		Flags flagABorrar = flagsService.findById(id);
		Map<String, Object> response = new HashMap<>();
		
		if(flagABorrar == null) {
			response.put("mensaje", "Error: no se pudo borrar, la flag ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
			
		try {
			flagsService.delete(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al eliminar la flag en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La flag ha sido eliminada con éxito.");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);		
	}
}
