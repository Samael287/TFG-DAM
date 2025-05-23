package com.samael.springboot.backend.peliculas.auth;

import java.io.Serializable;

public class Login implements Serializable {

	private static final long serialVersionUID = 1L;
	private String email;
	private String password;

	public Login() {
		super();
	}

	public Login(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
