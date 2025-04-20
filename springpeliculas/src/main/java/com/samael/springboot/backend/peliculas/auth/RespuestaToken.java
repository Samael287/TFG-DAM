package com.samael.springboot.backend.peliculas.auth;

import java.io.Serializable;

public class RespuestaToken implements Serializable {

	private static final long serialVersionUID = 1L;
	private String accessToken;

	public RespuestaToken() {
		super();
	}

	public RespuestaToken(String accessToken) {
		super();
		this.accessToken = accessToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
}
