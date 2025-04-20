package com.samael.springboot.backend.peliculas.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthorizationFilter extends OncePerRequestFilter {
    private final String HEADER = "Authorization";
	private final String PREFIX = "Bearer ";
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) 
			throws ServletException, IOException {
		try {
			String token = getJWTToken(request);
			System.out.println("🔍 Token recibido en backend: " + token);

			if (token != null) {
				Algorithm algorithm = Algorithm.HMAC256("token101");
				JWTVerifier verifier = JWT.require(algorithm)
					.withIssuer("samaeltebar")
					.build(); //Reusable verifier instance
				DecodedJWT jwt = verifier.verify(token);
				setUpSpringAuthentication(jwt);
			}
			chain.doFilter(request, response);
		} catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
		}
	}	

	/**
	 * Metodo para autenticarnos dentro del flujo de Spring
	 * 
	 * @param claims
	 */
	/*private void setUpSpringAuthentication(DecodedJWT jwt) {
		Authentication auth = new UsernamePasswordAuthenticationToken(jwt.getSubject(), jwt.getClaim("id"), null);
		SecurityContextHolder.getContext().setAuthentication(auth);
	}*/
	private void setUpSpringAuthentication(DecodedJWT jwt) {
	    String rol = jwt.getClaim("rol").asString(); // 🔥 Obtener el rol del token
	    Integer userId = jwt.getClaim("id").asInt(); // 🔥 Obtener el ID del usuario

	    List<GrantedAuthority> authorities = new ArrayList<>();
	    if (rol != null) {
	        authorities.add(new SimpleGrantedAuthority("ROLE_" + rol.toUpperCase())); // 🔥 Agregar "ROLE_"
	    }

	    // 🔥 Agregar este log para verificar si el rol se extrae correctamente
	    System.out.println("🔍 Verificación de Token: ID=" + userId + ", Rol=ROLE_" + rol.toUpperCase());

	    // Si no hay roles, no se configura autenticación
	    if (authorities.isEmpty()) {
	        System.out.println("⚠️ No se asignaron roles al usuario.");
	        return;
	    }

	    Authentication auth = new UsernamePasswordAuthenticationToken(userId, null, authorities);
	    SecurityContextHolder.getContext().setAuthentication(auth);

	    System.out.println("✅ Usuario autenticado con éxito: " + SecurityContextHolder.getContext().getAuthentication());
	}

	private String getJWTToken(HttpServletRequest request) {
		String authenticationHeader = request.getHeader(HEADER);
		if (authenticationHeader == null || !authenticationHeader.startsWith(PREFIX))
			return null;
		return authenticationHeader.replace(PREFIX, "");
	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
	    String path = request.getRequestURI();
	    return path.startsWith("/auth/login") || path.startsWith("/auth/registro") || path.startsWith("/api/carritos/")
	    		|| path.startsWith("/api/compras")
	    		|| path.startsWith("/api/estadisticas/")
	    		|| path.startsWith("/api/estadisticas2/")
	    		|| path.startsWith("/api/tendencias/")
	    		|| (request.getMethod().equals("POST") && path.equals("/api/usuarios"))
	    		|| (request.getMethod().equals("POST") && path.startsWith("/api/usuarios/"))
	    		|| (request.getMethod().equals("PUT") && path.startsWith("/api/usuarios/"))
	    		|| (request.getMethod().equals("DELETE") && path.startsWith("/api/usuarios/"));
	}
}