import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DirectoresService {

  private apiUrl = 'http://localhost:8080/api/directores'; // URL del backend

  constructor(private http: HttpClient) { }

  getDirectores(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }
  
  createDirector(director: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, director);
  }

  updateDirector(id: number, director: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, director);
  }

  deleteDirector(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }
}
