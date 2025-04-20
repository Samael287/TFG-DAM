package com.samael.springboot.backend.peliculas.models.services;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.samael.springboot.backend.peliculas.auth.Login;
import com.samael.springboot.backend.peliculas.models.dao.IUsuarioDAO;
import com.samael.springboot.backend.peliculas.models.entidades.Usuarios;
import com.samael.springboot.backend.peliculas.utils.SecurityUtils;

@Service
public class UsuarioServiceImpl implements IUsuarioService{
	
	@Autowired
	private IUsuarioDAO usuariosDao;
	
	@Autowired
	SecurityUtils securityUtils;

	@Override
	@Transactional(readOnly = true)
	public List<Usuarios> findAll() {
		return (List<Usuarios>) usuariosDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Usuarios findById(Integer id) {
		return usuariosDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Usuarios save(Usuarios usuario) {
		try {
			System.out.println("📝 Recibida solicitud para guardar usuario:");
	        System.out.println("🔍 Contraseña antes de guardar: " + usuario.getPassword());

	        if (usuario.getPassword() != null && !usuario.getPassword().trim().isEmpty() &&
	            !securityUtils.isEncoded(usuario.getPassword())) {
	            System.out.println("✅ Encriptando contraseña antes de guardar.");
	            usuario.setPassword(securityUtils.encodePassword(usuario.getPassword()));
	        } else {
	            System.out.println("⚠️ No se encripta la contraseña porque ya estaba encriptada o es vacía.");
	        }
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return usuariosDao.save(usuario);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		usuariosDao.deleteById(id);
	}
	
	@Override
    @Transactional(readOnly=true)
    public Usuarios login(Login login){
    	try {
    		return usuariosDao.findByEmailAndPassword(login.getEmail(), securityUtils.encodePassword(login.getPassword())).orElse(null);
    	}catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
    }
	
	@Override
    @Transactional
    public boolean insert(Usuarios u) {
    	try {
			u.setPassword(securityUtils.encodePassword(u.getPassword()));
			usuariosDao.save(u);
			return true;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
    }
}
