package com.samael.springboot.backend.peliculas.auth;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.samael.springboot.backend.peliculas.models.entidades.Tarjeta;
import com.samael.springboot.backend.peliculas.models.entidades.UserRole;
import com.samael.springboot.backend.peliculas.models.entidades.Usuarios;
import com.samael.springboot.backend.peliculas.models.services.IUsuarioService;
import com.samael.springboot.backend.peliculas.validation.ValidacionTarjeta;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final IUsuarioService usuariosService; // ⚠️ Esto es final para asegurar inyección

	// 🔹 Constructor necesario para que Spring inyecte el servicio
	public AuthController(IUsuarioService usuariosService) {
		this.usuariosService = usuariosService;
	}

	/*
	 * @PostMapping("/login") public ResponseEntity<?> login(@RequestBody Login
	 * login) throws NoSuchAlgorithmException { Usuarios user =
	 * usuariosService.login(login); if(user !=null) return
	 * ResponseEntity.ok().body(new RespuestaToken(getToken(user))); else return
	 * ResponseEntity.status(HttpStatus.UNAUTHORIZED).
	 * body("usuario y/o contraseña incorrectos"); }
	 */

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Login login) throws NoSuchAlgorithmException {
		Usuarios user = usuariosService.login(login);

		if (user != null) {
			// 🔹 Crear una respuesta con el token, rol y datos del usuario
			Map<String, Object> response = new HashMap<>();
			response.put("accessToken", getToken(user));
			response.put("role", user.getRol().name()); // ✅ Enviar el rol
			response.put("usuario", user); // ✅ Enviar el usuario completo

			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario y/o contraseña incorrectos");
		}
	}

	@PostMapping("/registro")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> registro(@Valid @RequestBody Usuarios userDto, BindingResult result) {
		Map<String, Object> response = new HashMap<>();
		
		 // 🔹 Primero validamos los campos del usuario (excepto la tarjeta)
	    if (result.hasErrors()) {
	        List<String> errors = result.getFieldErrors().stream()
	            .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
	            .toList();
	        response.put("errors", errors);
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    }

		try {
			// ✅ Validar tarjeta si viene incluida
			if (userDto.getTarjeta() != null) {
				Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
				Set<ConstraintViolation<Tarjeta>> violations = validator.validate(userDto.getTarjeta(),
						ValidacionTarjeta.class);

				if (!violations.isEmpty()) {
					List<String> erroresTarjeta = violations.stream()
							.map(err -> "Campo '" + err.getPropertyPath() + "': " + err.getMessage()).toList();
					response.put("errors", erroresTarjeta);
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
				}

				// ✅ Validación personalizada: fecha de caducidad
				Date hoy = new Date();
				if (userDto.getTarjeta().getFechaCaducidad() != null
						&& userDto.getTarjeta().getFechaCaducidad().before(hoy)) {
					response.put("errors", List.of("La fecha de caducidad no puede ser anterior a hoy"));
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
				}

				// ❗ Si quieres validar también número de seguridad o fondos aquí, puedes
				// hacerlo igual.
			}

			// Crear un usuario a partir del DTO
			Usuarios nuevoUsuario = new Usuarios(userDto);

			// ✅ Asignar un rol por defecto si no se especifica
			if (nuevoUsuario.getRol() == null) {
				nuevoUsuario.setRol(UserRole.CLIENTE); // Asigna CLIENTE si no se envía rol
			} else {
				nuevoUsuario.setRol(UserRole.valueOf(nuevoUsuario.getRol().toString().toUpperCase()));
			}

			boolean resultado = usuariosService.insert(nuevoUsuario);

			if (resultado) {
				return ResponseEntity.status(HttpStatus.CREATED).build();
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // Mejor 400 que 401
			}
		} catch (IllegalArgumentException e) {
			response.put("mensaje", "El rol enviado no es válido. Usa 'ADMIN' o 'CLIENTE'.");
			response.put("error", e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

		} catch (Exception e) {
			response.put("mensaje", "Error interno al registrar el usuario.");
			response.put("error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	private String getToken(Usuarios user) {
		Algorithm algorithm = Algorithm.HMAC256("token101");
		String token = JWT.create().withIssuer("samaeltebar").withClaim("id", user.getId())
				.withClaim("rol", user.getRol().name()).withIssuedAt(new Date(System.currentTimeMillis()))
				.withExpiresAt(new Date(System.currentTimeMillis() + (24 * 60 * 60 * 1000))) // Caduca en un día
				.sign(algorithm);

		return token;
	}
}
