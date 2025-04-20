package com.samael.springboot.backend.peliculas.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.samael.springboot.backend.peliculas.models.dao.ICarritoDAO;
import com.samael.springboot.backend.peliculas.models.entidades.Carrito;
import com.samael.springboot.backend.peliculas.models.entidades.Peliculas;
import com.samael.springboot.backend.peliculas.models.entidades.Usuarios;

@Service
public class CarritoServiceImpl implements ICarritoService{
	
	@Autowired
	ICarritoDAO carritoDao;

	@Override
	@Transactional(readOnly = true)
	public List<Carrito> findAll() {
		return (List<Carrito>) carritoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Carrito findById(Integer id) {
		return carritoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Carrito save(Carrito carrito) {
		return carritoDao.save(carrito);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		carritoDao.deleteById(id);
	}

	 // ✅ Obtener el carrito completo de un usuario
    @Override
    @Transactional(readOnly = true)
    public List<Carrito> obtenerCarritoPorUsuario(Integer usuarioId) {
        return carritoDao.findByUsuarioId(usuarioId);
    }

    // ✅ Agregar película al carrito (Si ya existe, lanza excepción)
    @Override
    @Transactional
    public Carrito agregarAlCarrito(Usuarios usuario, Peliculas pelicula) {
        Carrito carritoExistente = carritoDao.findByUsuarioIdAndPeliculaId(usuario.getId(), pelicula.getId());

        if (carritoExistente != null) {
        	throw new IllegalArgumentException("La película ya está en el carrito.");
        }
        
        // Si la película no está en el carrito, la añadimos
        Carrito nuevoCarrito = new Carrito(usuario, pelicula);
       
        return carritoDao.save(nuevoCarrito);
    }

    // ✅ Eliminar una película específica del carrito
    @Override
    @Transactional
    public void eliminarDelCarrito(Integer usuarioId, Integer peliculaId) {
        Carrito carrito = carritoDao.findByUsuarioIdAndPeliculaId(usuarioId, peliculaId);
        if (carrito != null) {
            carritoDao.delete(carrito);
        }
    }

    // ✅ Vaciar completamente el carrito de un usuario
    @Override
    @Transactional
    public void vaciarCarrito(Integer usuarioId) {
        carritoDao.deleteByUsuarioId(usuarioId);
    }

    @Override
    @Transactional(readOnly = true)
    public int obtenerCantidadTotal(Integer usuarioId) {
        return carritoDao.countByUsuarioId(usuarioId);
    }

	@Override
	@Transactional(readOnly = true)
	public Carrito findByUsuarioIdAndPeliculaId(Integer usuarioId, Integer peliculaId) {
	    return carritoDao.findByUsuarioIdAndPeliculaId(usuarioId, peliculaId);
	}
}
