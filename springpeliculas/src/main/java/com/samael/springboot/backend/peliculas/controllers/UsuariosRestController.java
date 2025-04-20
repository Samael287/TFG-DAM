package com.samael.springboot.backend.peliculas.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
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

import com.samael.springboot.backend.peliculas.models.dto.TarjetaDTO;
import com.samael.springboot.backend.peliculas.models.dto.UsuariosDTO;
import com.samael.springboot.backend.peliculas.models.entidades.Compra;
import com.samael.springboot.backend.peliculas.models.entidades.Tarjeta;
import com.samael.springboot.backend.peliculas.models.entidades.UserRole;
import com.samael.springboot.backend.peliculas.models.entidades.Usuarios;
import com.samael.springboot.backend.peliculas.models.services.ICompraService;
import com.samael.springboot.backend.peliculas.models.services.ITarjetaService;
import com.samael.springboot.backend.peliculas.models.services.IUsuarioService;
import com.samael.springboot.backend.peliculas.utils.SecurityUtils;
import com.samael.springboot.backend.peliculas.validation.ValidacionTarjeta;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

@CrossOrigin({"*"})
@RestController
@RequestMapping("/api")
public class UsuariosRestController {

	@Autowired
	private IUsuarioService usuariosService;
	
	@Autowired
	private ITarjetaService tarjetasService;
	
	@Autowired
	ICompraService compraService;
	
	@Autowired
	SecurityUtils securityUtils;
	
	@GetMapping("/usuarios")
	public List<UsuariosDTO> todosLosUsuarios(){
		
		List<Usuarios> usuarios = usuariosService.findAll();
		List<UsuariosDTO> usuariosDtoList = new ArrayList<>();
		
		for(Usuarios usuario : usuarios) {
			UsuariosDTO usuariosDTO = new UsuariosDTO();
			
			usuariosDTO.setId(usuario.getId());
			usuariosDTO.setNombre(usuario.getNombre()!= null ? 
					usuario.getNombre() : "Sin nombre asignado");
			usuariosDTO.setApellidos(usuario.getApellidos() != null ? 
					usuario.getApellidos() : "Sin apellidos asignados");
			usuariosDTO.setEmail(usuario.getEmail() != null ? 
					usuario.getEmail() : "Sin email asignado");
			usuariosDTO.setPassword(usuario.getPassword() != null ? 
					usuario.getPassword() : "Sin contraseña asignada");
			usuariosDTO.setRol(usuario.getRol());
		
			if(usuario.getTarjeta() != null) {
				TarjetaDTO tarjetaDTO = new TarjetaDTO();
				tarjetaDTO.setId(usuario.getTarjeta().getId());
				tarjetaDTO.setNumeroTarjeta(usuario.getTarjeta().getNumeroTarjeta());
				tarjetaDTO.setFechaCaducidad(usuario.getTarjeta().getFechaCaducidad().toString());
				tarjetaDTO.setNumeroSeguridad(usuario.getTarjeta().getNumeroSeguridad());
				tarjetaDTO.setFondosDisponibles(usuario.getTarjeta().getFondosDisponibles());
				tarjetaDTO.setUsuarioId(usuario.getTarjeta().getUsuario().getId());
				
				usuariosDTO.setTarjeta(tarjetaDTO);
			}
			
			usuariosDtoList.add(usuariosDTO);
		}
		
		return usuariosDtoList;
	}
	
	@GetMapping("/usuarios/{id}")
	public ResponseEntity<?> unUsuario(@PathVariable Integer id){
		Usuarios usuario = null;
		UsuariosDTO usuarioDto = new UsuariosDTO();
		Map<String, Object> respuesta = new HashMap<String, Object>();

	    try {
	        usuario = usuariosService.findById(id);
	    } catch (Exception e) { // Base de datos inaccesible
	        respuesta.put("mensaje", "Error al realizar la consulta sobre la base de datos");
	        respuesta.put("error", e.getMessage().concat(": ").concat(e.getStackTrace().toString()));
	        return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	    if (usuario == null) { // Hemos buscado un id que no existe
	        respuesta.put("mensaje", "El identificador buscado no existe");
	        return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.NOT_FOUND);
	    }
	    
	    usuarioDto.setId(usuario.getId());
	    usuarioDto.setNombre(usuario.getNombre());
	    usuarioDto.setApellidos(usuario.getApellidos());
	    usuarioDto.setEmail(usuario.getEmail());
	    usuarioDto.setPassword(usuario.getPassword());
	    usuarioDto.setRol(usuario.getRol());
	    
	    if(usuario.getTarjeta() != null) {
			TarjetaDTO tarjetaDTO = new TarjetaDTO();
			tarjetaDTO.setId(usuario.getTarjeta().getId());
			tarjetaDTO.setNumeroTarjeta(usuario.getTarjeta().getNumeroTarjeta());
			tarjetaDTO.setFechaCaducidad(usuario.getTarjeta().getFechaCaducidad().toString());
			tarjetaDTO.setNumeroSeguridad(usuario.getTarjeta().getNumeroSeguridad());
			tarjetaDTO.setFondosDisponibles(usuario.getTarjeta().getFondosDisponibles());
			tarjetaDTO.setUsuarioId(usuario.getTarjeta().getUsuario().getId());
			
			usuarioDto.setTarjeta(tarjetaDTO);
		}	
	    
	    return new ResponseEntity<UsuariosDTO>(usuarioDto, HttpStatus.OK);
    }
	
	@PostMapping("/usuarios")
	public ResponseEntity<?> create(@Valid @RequestBody Usuarios usuario, BindingResult result){
		
		Usuarios newUsuario = null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			
			 // 🔒 Validar tarjeta si viene incluida
	        if (usuario.getTarjeta() != null) {
	            Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	            Set<ConstraintViolation<Tarjeta>> violations = validator.validate(usuario.getTarjeta(), ValidacionTarjeta.class);

	            if (!violations.isEmpty()) {
	                List<String> erroresTarjeta = violations.stream()
	                        .map(err -> "Campo '" + err.getPropertyPath() + "': " + err.getMessage())
	                        .collect(Collectors.toList());
	                response.put("errors", erroresTarjeta);
	                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	            }
	            Tarjeta tarjeta = usuario.getTarjeta();
		        // ⚠️ Validación de fecha de caducidad
		        Date hoy = new Date();
		        if (tarjeta.getFechaCaducidad() != null && tarjeta.getFechaCaducidad().before(hoy)) {
		            response.put("errors", List.of("La fecha de caducidad no puede ser anterior a hoy"));
		            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		        }
				 // 🔹 Verificar si el usuario tiene una tarjeta antes de acceder a su ID
				Integer tarjetaId = (usuario.getTarjeta() != null && usuario.getTarjeta().getId() != null) ? usuario.getTarjeta().getId() : 0;

			    if (tarjetaId != 0) {
			        // Buscar la tarjeta en la base de datos
			        Tarjeta tarjetaExistente = tarjetasService.findById(tarjetaId);
			        if (tarjetaExistente != null) {
			            usuario.setTarjeta(tarjetaExistente);  // Asociar la tarjeta existente
			        } else {
			            response.put("mensaje", "Error: La tarjeta con ID " + tarjetaId + " no existe.");
			            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			        }
			    }
	        }
			
		    // ✅ Asignar un rol por defecto si no se especifica
	        if (usuario.getRol() == null) {
	            usuario.setRol(UserRole.CLIENTE); // Asigna CLIENTE si no se envía rol
	        } else {
	            usuario.setRol(UserRole.valueOf(usuario.getRol().toString().toUpperCase()));
	        }
	       
			newUsuario = usuariosService.save(usuario);
		}catch (IllegalArgumentException e) {
	        response.put("mensaje", "El valor del campo 'rol' no es válido. Debe ser 'ADMIN' o 'CLIENTE'.");
	        response.put("error", e.getMessage());
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El usuario ha sido creado con éxito.");
		response.put("usuario", newUsuario);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PostMapping("/usuarios/verificar-password")
	public ResponseEntity<?> verificarPassword(@RequestBody Map<String, String> payload) {
	    try {
	        System.out.println("📌 Recibida solicitud de verificación de contraseña.");
	        System.out.println("🔍 Payload recibido: " + payload);

	        Integer userId = Integer.parseInt(payload.get("id"));
	        String passwordIngresada = payload.get("password");

	        System.out.println("📌 Usuario ID recibido: " + userId);
	        System.out.println("🔍 Contraseña ingresada: " + passwordIngresada);

	        Usuarios usuario = usuariosService.findById(userId);
	        if (usuario == null) {
	            System.out.println("❌ Usuario no encontrado");
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
	        }

	        String passwordCodificada = securityUtils.encodePassword(passwordIngresada);
	        System.out.println("🗄️ Contraseña codificada ingresada: " + passwordCodificada);
	        System.out.println("🗄️ Contraseña almacenada en BD: " + usuario.getPassword());

	        if (passwordCodificada.equals(usuario.getPassword())) {
	            System.out.println("✅ Contraseña correcta");
	            return ResponseEntity.ok().body(Map.of("mensaje", "Contraseña correcta"));
	        } else {
	            System.out.println("❌ Contraseña incorrecta");
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta");
	        }

	    } catch (Exception e) {
	        System.out.println("❌ Error al verificar la contraseña: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al verificar la contraseña");
	    }
	}

	@PutMapping("/usuarios/{id}")
	public ResponseEntity<?> update(@RequestBody Usuarios usuario, BindingResult result, @PathVariable Integer id){
		
		Usuarios usuarioActual = usuariosService.findById(id);
		Usuarios usuarioUpdated = null;

		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (usuarioActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el usuario ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			
			 // 🔹 Validar nombre
	        if (usuario.getNombre() != null) {
	            String nombre = usuario.getNombre().trim();
	            if (nombre.length() < 3 || nombre.length() > 12) {
	                response.put("errors", List.of("El nombre debe tener entre 3 y 12 caracteres"));
	                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	            }
	        }

	        // 🔹 Validar apellidos
	        if (usuario.getApellidos() != null && usuario.getApellidos().trim().isEmpty()) {
	            response.put("errors", List.of("Los apellidos no pueden estar vacíos"));
	            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	        }

	        // 🔹 Validar email
	        if (usuario.getEmail() != null) {
	            String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
	            if (!usuario.getEmail().matches(emailRegex)) {
	                response.put("errors", List.of("El email no tiene un formato válido"));
	                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	            }
	        }

	        // 🔹 Validar contraseña (si se cambia)
	        if (usuario.getPassword() != null && !usuario.getPassword().trim().isEmpty()) {
	            String pwd = usuario.getPassword().trim();
	            if (pwd.length() < 4) {
	                response.put("errors", List.of("La contraseña debe tener al menos 4 caracteres"));
	                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	            }
	        }
			
	        // 🔹 Asegurar que el ID se mantiene en la actualización
	        usuarioActual.setId(id);
	        
	        // 🔹 Actualizar datos básicos del usuario
	        usuarioActual.setNombre(usuario.getNombre());
	        usuarioActual.setApellidos(usuario.getApellidos());
	        System.out.println("🔍 Nueva contraseña recibida en la API: " + usuario.getPassword());

	        System.out.println("🔍 Nueva contraseña recibida en la API: " + usuario.getPassword());

	        // Actualizar la contraseña solo si se envía una nueva (no vacía)
	        if (usuario.getPassword() != null && !usuario.getPassword().trim().isEmpty()) {
	            // Si el valor recibido ya está encriptado, se asume que no se modificó
	            if (securityUtils.isEncoded(usuario.getPassword())) {
	                System.out.println("⚠️ La contraseña ya está codificada, se mantiene la actual.");
	                // No se actualiza; se conserva usuarioActual.getPassword()
	            } else {
	                System.out.println("✅ Se ha enviado una nueva contraseña, se procede a encriptarla.");
	                try {
						usuarioActual.setPassword(securityUtils.encodePassword(usuario.getPassword()));
					} catch (NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	        } else {
	            System.out.println("⚠️ No se ha enviado una nueva contraseña, se mantiene la actual.");
	        }
	        usuarioActual.setRol(usuario.getRol());

	        // 🔹 Evitar que el email cambie si no se desea actualizar
	        if (!usuarioActual.getEmail().equals(usuario.getEmail())) {
	            usuarioActual.setEmail(usuario.getEmail());
	        }

	        // 🔹 ACTUALIZAR TARJETA: Si el usuario envía datos de tarjeta
	        if (usuario.getTarjeta() != null) {

	            Tarjeta nuevaTarjeta = usuario.getTarjeta();
	            Tarjeta tarjetaActual = usuarioActual.getTarjeta();

	            // ✅ Validación personalizada de la fecha
	            Date hoy = new Date();
	            if (nuevaTarjeta.getFechaCaducidad() != null &&
	                nuevaTarjeta.getFechaCaducidad().before(hoy)) {
	                response.put("errors", List.of("La fecha de caducidad no puede ser anterior a hoy"));
	                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	            }

	            // ⚠️ Solo buscar tarjeta existente si se ha enviado un número de tarjeta
	            Tarjeta tarjetaExistente = null;
	            boolean tieneNumeroTarjeta = nuevaTarjeta.getNumeroTarjeta() != null && !nuevaTarjeta.getNumeroTarjeta().isBlank();

	            if (tieneNumeroTarjeta) {
	                tarjetaExistente = tarjetasService.findByNumeroFechaSeguridad(
	                    nuevaTarjeta.getNumeroTarjeta(),
	                    nuevaTarjeta.getFechaCaducidad(),
	                    nuevaTarjeta.getNumeroSeguridad()
	                );
	            }

	            if (tarjetaExistente != null) {
	                if (tarjetaActual != null && tarjetaExistente.getId().equals(tarjetaActual.getId())) {
	                    tarjetaActual.setFechaCaducidad(nuevaTarjeta.getFechaCaducidad());
	                    tarjetaActual.setNumeroSeguridad(nuevaTarjeta.getNumeroSeguridad());
	                    tarjetaActual.setFondosDisponibles(nuevaTarjeta.getFondosDisponibles());
	                } else {
	                    if (tarjetaExistente.getUsuario() != null && !tarjetaExistente.getUsuario().getId().equals(usuarioActual.getId())) {
	                        response.put("mensaje", "Error: La tarjeta ingresada ya pertenece a otro usuario.");
	                        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
	                    }
	                    usuarioActual.setTarjeta(tarjetaExistente);
	                }
	            } else {
	                if (tarjetaActual == null) {
	                    // 🔍 Validar con grupo si se va a crear una nueva
	                    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	                    Set<ConstraintViolation<Tarjeta>> violations = validator.validate(nuevaTarjeta, ValidacionTarjeta.class);
	                    if (!violations.isEmpty()) {
	                        List<String> erroresTarjeta = violations.stream()
	                                .map(err -> "Campo '" + err.getPropertyPath() + "': " + err.getMessage())
	                                .collect(Collectors.toList());
	                        response.put("errors", erroresTarjeta);
	                        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	                    }

	                    Tarjeta tarjetaNueva = new Tarjeta();
	                    tarjetaNueva.setNumeroTarjeta(nuevaTarjeta.getNumeroTarjeta());
	                    tarjetaNueva.setFechaCaducidad(nuevaTarjeta.getFechaCaducidad());
	                    tarjetaNueva.setNumeroSeguridad(nuevaTarjeta.getNumeroSeguridad());
	                    tarjetaNueva.setFondosDisponibles(nuevaTarjeta.getFondosDisponibles());
	                    tarjetaNueva = tarjetasService.save(tarjetaNueva);
	                    usuarioActual.setTarjeta(tarjetaNueva);

	                } else if (tieneNumeroTarjeta && !tarjetaActual.getNumeroTarjeta().equals(nuevaTarjeta.getNumeroTarjeta())) {
	                    // 🔍 Validar con grupo si se cambia el número
	                    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	                    Set<ConstraintViolation<Tarjeta>> violations = validator.validate(nuevaTarjeta, ValidacionTarjeta.class);
	                    if (!violations.isEmpty()) {
	                        List<String> erroresTarjeta = violations.stream()
	                                .map(err -> "Campo '" + err.getPropertyPath() + "': " + err.getMessage())
	                                .collect(Collectors.toList());
	                        response.put("errors", erroresTarjeta);
	                        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	                    }

	                    tarjetaActual.setUsuario(null);
	                    tarjetaActual.setUsuarioAnteriorId(usuarioActual.getId());
	                    tarjetasService.save(tarjetaActual);

	                    Tarjeta tarjetaNueva = new Tarjeta();
	                    tarjetaNueva.setNumeroTarjeta(nuevaTarjeta.getNumeroTarjeta());
	                    tarjetaNueva.setFechaCaducidad(nuevaTarjeta.getFechaCaducidad());
	                    tarjetaNueva.setNumeroSeguridad(nuevaTarjeta.getNumeroSeguridad());
	                    tarjetaNueva.setFondosDisponibles(nuevaTarjeta.getFondosDisponibles());
	                    tarjetaNueva = tarjetasService.save(tarjetaNueva);
	                    usuarioActual.setTarjeta(tarjetaNueva);

	                } else {
	                    // ✅ Validaciones individuales si no se cambia el número de tarjeta
	                    if (nuevaTarjeta.getFechaCaducidad() == null) {
	                        response.put("errors", List.of("La fecha de caducidad no puede estar vacía"));
	                        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	                    }
	                    if (nuevaTarjeta.getNumeroSeguridad() == null || !nuevaTarjeta.getNumeroSeguridad().matches("\\d{3}")) {
	                        response.put("errors", List.of("El número de seguridad debe tener exactamente 3 dígitos"));
	                        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	                    }
	                    if (nuevaTarjeta.getFondosDisponibles() == null || nuevaTarjeta.getFondosDisponibles() < 0) {
	                        response.put("errors", List.of("Los fondos disponibles no pueden ser negativos"));
	                        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	                    }

	                    // ⏬ Actualización normal si todo está bien
	                    tarjetaActual.setFechaCaducidad(nuevaTarjeta.getFechaCaducidad());
	                    tarjetaActual.setNumeroSeguridad(nuevaTarjeta.getNumeroSeguridad());
	                    tarjetaActual.setFondosDisponibles(nuevaTarjeta.getFondosDisponibles());
	                }
	            }
	        }

	        // 🔹 Guardar la actualización (Hibernate hará `UPDATE` en lugar de `INSERT`)
	        usuarioUpdated = usuariosService.save(usuarioActual);

	    } catch (DataAccessException e) {
	        response.put("mensaje", "Error al actualizar el usuario en la base de datos");
	        response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	    response.put("mensaje", "El usuario ha sido actualizado con éxito.");
	    response.put("usuario", usuarioUpdated);
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/usuarios/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id){
		
		Usuarios usuarioABorrar = usuariosService.findById(id);
		
		Map<String, Object> response = new HashMap<>();

		if(usuarioABorrar == null) {
			response.put("mensaje", "Error: no se pudo borrar, el usuario ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
			
		try {
			//desasociar la tarjeta del usuario para que al eliminarlo se guarde la tarjeta en los registros.
			if(usuarioABorrar.getTarjeta() != null) {
				Tarjeta tarjeta = usuarioABorrar.getTarjeta();
				tarjeta.setUsuario(null);
				tarjeta.setUsuarioAnteriorId(usuarioABorrar.getId());
				tarjetasService.save(tarjeta);
			}
			
			 // ✅ 1. Buscar si el usuario tiene compras antes de hacer cambios en la base de datos
	        List<Compra> comprasDelUsuario = compraService.obtenerCompraPorUsuario(id);

	        if (!comprasDelUsuario.isEmpty()) { // Solo modificamos compras si hay alguna
	            for (Compra compra : comprasDelUsuario) {
	                compra.setUsuarioBorradoId(id); // Guardamos el ID del usuario eliminado
	                compra.setUsuario(null); // Desasociamos al usuario
	                compraService.save(compra); // Guardamos la compra con el ID del usuario borrado
	            }
	        }
			
			usuariosService.delete(id);
			
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al eliminar el usuario en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El usuario ha sido eliminado con éxito.");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);		
	}
}
