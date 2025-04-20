package com.samael.springboot.backend.peliculas.models.services;

import java.util.List;

import com.samael.springboot.backend.peliculas.auth.Login;
import com.samael.springboot.backend.peliculas.models.entidades.Usuarios;

public interface IUsuarioService {

	public List<Usuarios> findAll();
	
	public Usuarios findById(Integer id);
	
	public Usuarios save(Usuarios usuario);
	
	public void delete(Integer id);

	public Usuarios login(Login login);

	boolean insert(Usuarios u);
}
