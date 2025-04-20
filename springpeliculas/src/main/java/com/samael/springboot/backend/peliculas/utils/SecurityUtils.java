package com.samael.springboot.backend.peliculas.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.springframework.stereotype.Component;

@Component("securityUtils")
public class SecurityUtils {
	public String encodePassword(String pass) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(pass.getBytes(StandardCharsets.UTF_8));
		System.out.println(hash);
		String encodedPass = Base64.getEncoder().encodeToString(hash);
		return encodedPass;
	}
	
	 public boolean isEncoded(String password) {
	        if (password == null) return false;
	        // Un hash SHA-256 codificado en Base64 produce normalmente 44 caracteres.
	        // La expresión regular permite de 0 a 2 caracteres '=' al final (padding).
	        return password.length() == 44 && password.matches("[A-Za-z0-9+/]+={0,2}");
    }
}
