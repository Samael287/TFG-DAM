import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CompraService {

  private apiUrl = 'http://localhost:8080/api/compras';
  private apiUrlTendencias = 'http://localhost:8080/api/tendencias';
  
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

    getComprasPorUsuario(usuarioId: number): Observable<any> {
      return this.http.get(`${this.apiUrl}/usuario/${usuarioId}`, this.getHeaders());
    }

    realizarCompra(compra: any): Observable<any> {
      return this.http.post(`${this.apiUrl}`, compra, this.getHeaders());
    }

    getEstadisticas(): Observable<any> {
      return this.http.get(`${this.apiUrl}/estadisticas`, this.getHeaders());
    }
    
    getEstadisticasParam(tipo: string = 'DIARIO') {
      return this.http.get<any>(`http://localhost:8080/api/compras/estadisticas?tipo=${tipo}`);
    }

    getPeliculasMasVendidas(): Observable<any> {
      return this.http.get(`${this.apiUrlTendencias}/global`, this.getHeaders());
    }
    
    getPeliculaMasVendidaMes(): Observable<any> {
      return this.http.get(`${this.apiUrlTendencias}/mensual`, this.getHeaders());
    }
    
    getRecomendaciones(usuarioId: number): Observable<any> {
      return this.http.get(`${this.apiUrlTendencias}/recomendacion?usuarioId=${usuarioId}`, this.getHeaders());
    }
}
