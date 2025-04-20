import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TarjetasService {

  private apiUrl = 'http://localhost:8080/api/tarjetas'; // URL del backend

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

  getTarjetas(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }
  
  getTarjeta(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  createTarjeta(tarjeta: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, tarjeta, this.getHeaders());
  }

  updateTarjeta(id: number, tarjeta: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, tarjeta, this.getHeaders());
  }

  deleteTarjeta(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }
}
