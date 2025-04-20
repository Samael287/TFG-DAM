package com.samael.springboot.backend.peliculas.models.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.samael.springboot.backend.peliculas.models.dao.ITarjetaDAO;
import com.samael.springboot.backend.peliculas.models.entidades.Tarjeta;

@Service
public class TarjetaServiceImpl implements ITarjetaService{

	@Autowired
	private ITarjetaDAO tarjetaDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Tarjeta> findAll() {
		return (List<Tarjeta>) tarjetaDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Tarjeta findById(Integer id) {
		return tarjetaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Tarjeta save(Tarjeta tarjeta) {
		return tarjetaDao.save(tarjeta);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		tarjetaDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Tarjeta findByNumeroFechaSeguridad(String numeroTarjeta, Date fechaCaducidad, String numeroSeguridad) {
		return tarjetaDao.findByNumeroTarjetaAndFechaCaducidadAndNumeroSeguridad(numeroTarjeta, fechaCaducidad, numeroSeguridad);
	}
}
