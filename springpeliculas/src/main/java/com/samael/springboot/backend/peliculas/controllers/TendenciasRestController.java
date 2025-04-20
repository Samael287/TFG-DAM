package com.samael.springboot.backend.peliculas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.samael.springboot.backend.peliculas.models.entidades.Peliculas;
import com.samael.springboot.backend.peliculas.models.services.ICompraService;

@CrossOrigin({ "*" })
@RestController
@RequestMapping("/api")
public class TendenciasRestController {

	@Autowired
	private ICompraService compraService;

	// Endpoint para obtener las 5 películas más vendidas (globalmente)
	@GetMapping("/tendencias/global")
	public ResponseEntity<List<Peliculas>> getPeliculasMasVendidas() {
		List<Peliculas> peliculas = compraService.obtenerPeliculasMasVendidas();
		return new ResponseEntity<>(peliculas, HttpStatus.OK);
	}

	// Endpoint para obtener la película más vendida en el mes actual
	@GetMapping("/tendencias/mensual")
	public ResponseEntity<Peliculas> getPeliculaMasVendidaMes() {
		Peliculas pelicula = compraService.obtenerPeliculaMasVendidaMes();
		return new ResponseEntity<>(pelicula, HttpStatus.OK);
	}

	// Endpoint para recomendar películas según el historial de compras de un
	// usuario
	@GetMapping("/tendencias/recomendacion")
	public ResponseEntity<List<Peliculas>> getRecomendaciones(@RequestParam Integer usuarioId) {
		List<Peliculas> recomendaciones = compraService.recomendarPorHistorial(usuarioId);
		return new ResponseEntity<>(recomendaciones, HttpStatus.OK);
	}
}
