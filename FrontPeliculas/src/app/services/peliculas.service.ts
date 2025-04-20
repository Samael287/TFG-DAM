import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PeliculasService {

  private apiUrl = 'http://localhost:8080/api/peliculas'; // URL del backend

  constructor(private http: HttpClient) {}

  getPeliculas(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  createPelicula(pelicula: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, pelicula);
  }

  updatePelicula(id: number, pelicula: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, pelicula);
  }

  deletePelicula(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }
}
