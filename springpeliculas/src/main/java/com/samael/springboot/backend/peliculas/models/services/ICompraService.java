package com.samael.springboot.backend.peliculas.models.services;

import java.util.List;

import com.samael.springboot.backend.peliculas.models.entidades.Compra;
import com.samael.springboot.backend.peliculas.models.entidades.Peliculas;
import com.samael.springboot.backend.peliculas.models.entidades.Usuarios;

public interface ICompraService {
	
	public List<Compra> findAll();
	
	public Compra findById(Integer id);
	
	public Compra save(Compra compra);
	
	public void delete(Integer id);
	
	public List<Compra> obtenerCompraPorUsuario(Integer usuarioId);
	
    public List<Compra> obtenerCompraPorTarjeta(Integer tarjetaId);
    
    public List<Compra> obtenerCompraPorPelicula(Integer peliculaId);
	
	public Compra findByUsuarioIdAndPeliculaId(Integer usuarioId, Integer peliculaId);
	
	public Compra agregarCompra(Usuarios usuario, Peliculas pelicula);

	public Integer obtenerUltimaTransaccionIdPorUsuario(Integer usuarioId);
	
	public Double obtenerIngresosTotales();
	
	public List<Compra> obtenerComprasDelDia();
	
	public List<Compra> obtenerComprasSemanal();
	
	public List<Compra> obtenerComprasMensual();
	
	public Double obtenerPromedioVentasDelMes();

	List<Peliculas> obtenerPeliculasMasVendidas();

	Peliculas obtenerPeliculaMasVendidaMes();

	List<Peliculas> recomendarPorHistorial(Integer usuarioId);
}
