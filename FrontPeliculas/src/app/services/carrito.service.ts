import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CarritoService {

  private apiUrl = 'http://localhost:8080/api/carritos';

  constructor(private http: HttpClient) { }
  
  private getHeaders() {
    const token = localStorage.getItem('token');
    if (token && token !== 'null' && token.trim() !== '') {
      return {
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`
        })
      };
    }
    return {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
  }
  
  // Obtener todos los elementos del carrito de un usuario
  getCarritoPorUsuario(usuarioId: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/usuario/${usuarioId}`, this.getHeaders());
  }

  // Agregar una película al carrito de un usuario
  agregarAlCarrito(usuarioId: number, peliculaId: number): Observable<any> {
    return this.http.post(`${this.apiUrl}`, { usuario: { id: usuarioId }, pelicula: { id: peliculaId } });
  }

  // Eliminar una película específica del carrito
  eliminarPeliculaDelCarrito(usuarioId: number, peliculaId: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/usuario/${usuarioId}/pelicula/${peliculaId}`);
  }

  // Vaciar el carrito de un usuario
  vaciarCarrito(usuarioId: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/vaciar/${usuarioId}`);
  }

  // Obtener la cantidad total de películas en el carrito de un usuario
  getCantidadTotal(usuarioId: number): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/cantidad/${usuarioId}`);
  }
}
