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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samael.springboot.backend.peliculas.models.dto.TarjetaDTO;
import com.samael.springboot.backend.peliculas.models.entidades.Compra;
import com.samael.springboot.backend.peliculas.models.entidades.Tarjeta;
import com.samael.springboot.backend.peliculas.models.entidades.Usuarios;
import com.samael.springboot.backend.peliculas.models.services.ICompraService;
import com.samael.springboot.backend.peliculas.models.services.ITarjetaService;
import com.samael.springboot.backend.peliculas.models.services.IUsuarioService;

@CrossOrigin({"*"})
@RestController
@RequestMapping("/api")
public class TarjetaRestController {
	
	@Autowired
	private ITarjetaService tarjetasService;
	
	@Autowired
	private IUsuarioService usuariosService;
	
	@Autowired
	ICompraService compraService;
	
	@GetMapping("/tarjetas")
	public List<TarjetaDTO> todasLasTarjetas(){
		
		List<Tarjeta> tarjetas = tarjetasService.findAll();
		List<TarjetaDTO> tarjetasDto = new ArrayList<>();
		
		tarjetas.forEach(tarjeta -> {
			
			String fechaCaducidad = tarjeta.getFechaCaducidad() != null ?
					tarjeta.getFechaCaducidad().toString() : "Fecha no asignada";
			
			TarjetaDTO tarjetaDto = new TarjetaDTO();
			
			tarjetaDto.setId(tarjeta.getId());
			tarjetaDto.setNumeroTarjeta(tarjeta.getNumeroTarjeta());
			tarjetaDto.setFechaCaducidad(fechaCaducidad);
			tarjetaDto.setNumeroSeguridad(tarjeta.getNumeroSeguridad());
			tarjetaDto.setFondosDisponibles(tarjeta.getFondosDisponibles());
			tarjetaDto.setUsuarioAnteriorId(tarjeta.getUsuarioAnteriorId());
			if(tarjeta.getUsuario() != null)
			{
				tarjetaDto.setUsuarioId(tarjeta.getUsuario().getId());
			} else {
				tarjetaDto.setUsuarioId(null);
			}
			
			tarjetasDto.add(tarjetaDto);
		});
		
		return tarjetasDto;
	}
	
	@GetMapping("/tarjetas/{id}")
	ResponseEntity<?> unaTarjeta(@PathVariable Integer id){
		
		Tarjeta tarjeta = tarjetasService.findById(id);
		TarjetaDTO tarjetaDto = new TarjetaDTO();
		
		Map<String, Object> respuesta = new HashMap<String, Object>();

	    try {
	    	tarjeta = tarjetasService.findById(id);
	    } catch (Exception e) { // Base de datos inaccesible
	        respuesta.put("mensaje", "Error al realizar la consulta sobre la base de datos");
	        respuesta.put("error", e.getMessage().concat(": ").concat(e.getStackTrace().toString()));
	        return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	    if (tarjeta == null) { // Hemos buscado un id que no existe
	        respuesta.put("mensaje", "El identificador buscado no existe");
	        return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.NOT_FOUND);
	    }
	    
	    tarjetaDto.setId(tarjeta.getId());
	    tarjetaDto.setNumeroTarjeta(tarjeta.getNumeroTarjeta());
	    tarjetaDto.setFechaCaducidad(tarjeta.getFechaCaducidad().toString());
	    tarjetaDto.setNumeroSeguridad(tarjeta.getNumeroSeguridad());
	    tarjetaDto.setFondosDisponibles(tarjeta.getFondosDisponibles());
		tarjetaDto.setUsuarioAnteriorId(tarjeta.getUsuarioAnteriorId());
		if(tarjeta.getUsuario() != null)
		{
			tarjetaDto.setUsuarioId(tarjeta.getUsuario().getId());
		} else {
			tarjetaDto.setUsuarioId(null);
		}
	    
	    return new ResponseEntity<TarjetaDTO>(tarjetaDto, HttpStatus.OK);
	}
	
	@PostMapping("/tarjetas")
	public ResponseEntity<?> create(@RequestBody Tarjeta tarjeta, BindingResult result){
		
		Tarjeta newTarjeta = null;
		
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			
			newTarjeta = tarjetasService.save(tarjeta);
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La tarjeta ha sido creada con éxito.");
		response.put("tarjeta", newTarjeta);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/tarjetas/{id}")
	public ResponseEntity<?> update(@RequestBody Tarjeta tarjeta, BindingResult result, @PathVariable Integer id){
		
		Tarjeta tarjetaActual = tarjetasService.findById(id);		
		Tarjeta tarjetaUpdated = null;
		
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (tarjetaActual == null) {
			response.put("mensaje", "Error: no se pudo editar, la tarjeta ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			
			 // 🔹 Si el número de tarjeta es diferente, creamos una nueva en lugar de actualizar
	        if (!tarjetaActual.getNumeroTarjeta().equals(tarjeta.getNumeroTarjeta())) {

	            // 🔹 Guardamos el ID del usuario en la tarjeta anterior antes de desasociarla
	            if (tarjetaActual.getUsuario() != null) {
	                tarjetaActual.setUsuarioAnteriorId(tarjetaActual.getUsuario().getId());
	            }

	            tarjetasService.save(tarjetaActual); // 🔹 Guardamos la tarjeta anterior con el usuario anterior

	            // 🔹 Creamos la nueva tarjeta con los datos actualizados
	            Tarjeta nuevaTarjeta = new Tarjeta();
	            nuevaTarjeta.setNumeroTarjeta(tarjeta.getNumeroTarjeta());
	            nuevaTarjeta.setFechaCaducidad(tarjeta.getFechaCaducidad());
	            nuevaTarjeta.setNumeroSeguridad(tarjeta.getNumeroSeguridad());
	            nuevaTarjeta.setFondosDisponibles(tarjeta.getFondosDisponibles());

	            // 🔹 Asociamos la nueva tarjeta al mismo usuario si lo tiene
	            if (tarjetaActual.getUsuario() != null) {
	                nuevaTarjeta.setUsuario(tarjetaActual.getUsuario());
	            }

	            // 🔹 Guardamos la nueva tarjeta en la base de datos
	            tarjetaUpdated = tarjetasService.save(nuevaTarjeta);

	        } else {
	            // 🔹 Si el número de tarjeta NO cambió, solo actualizamos los datos
	            tarjetaActual.setFechaCaducidad(tarjeta.getFechaCaducidad());
	            tarjetaActual.setNumeroSeguridad(tarjeta.getNumeroSeguridad());
	            tarjetaActual.setFondosDisponibles(tarjeta.getFondosDisponibles());

	            tarjetaUpdated = tarjetasService.save(tarjetaActual);
	       }
	        
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar la tarjeta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "La tarjeta ha sido actualizada con éxito.");
		response.put("tarjeta", tarjetaUpdated);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/tarjetas/{id}") //NO BORRA REALMENTE, solo desasocia la tarjeta eliminada del usuario!!!
	public ResponseEntity<?> delete(@PathVariable Integer id){
		
		Tarjeta tarjetaABorrar = tarjetasService.findById(id);
		
		Map<String, Object> response = new HashMap<>();

		if(tarjetaABorrar == null) {
			response.put("mensaje", "Error: no se pudo borrar, la tarjeta ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
			
		try {
			 // ✅ 1. Obtener el usuario actual que tiene la tarjeta
	        Usuarios usuarioAsociado = tarjetaABorrar.getUsuario();

	        if (usuarioAsociado != null) {
	            // ✅ 2. Guardar el ID del usuario en `usuarioAnteriorId`
	            tarjetaABorrar.setUsuarioAnteriorId(usuarioAsociado.getId());

	            // ✅ 3. Desasociar la tarjeta del usuario
	            usuarioAsociado.setTarjeta(null);
	            usuariosService.save(usuarioAsociado);  // 🔹 Guardamos el usuario sin tarjeta

	            // ✅ 4. Desasociar el usuario de la tarjeta
	            tarjetaABorrar.setUsuario(null);
	            
	            // ✅ 5. Verificar compras realizadas con esta tarjeta
	            List<Compra> comprasConTarjeta = compraService.obtenerCompraPorTarjeta(id);

	            if (!comprasConTarjeta.isEmpty()) { // Solo modificamos compras si hay alguna
	                for (Compra compra : comprasConTarjeta) {
	                    compra.setTarjetaBorradaId(id); // Guardamos el ID de la tarjeta eliminada
	                    compra.setTarjeta(null); // Desasociamos la tarjeta
	                    compraService.save(compra); // Guardamos la compra con el ID de la tarjeta borrada
	                }
	            }
	            
	            tarjetasService.save(tarjetaABorrar); // 🔹 Guardamos la tarjeta sin usuario

	            response.put("mensaje", "La tarjeta ID " + id + " ha sido desasociada del usuario con éxito.");
	            return new ResponseEntity<>(response, HttpStatus.OK);
	        } else {
	            response.put("mensaje", "La tarjeta ID " + id + " ya está desasociada.");
	            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	        }

		}catch(DataAccessException e) {
			response.put("mensaje", "Error al desasociar la tarjeta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
}
