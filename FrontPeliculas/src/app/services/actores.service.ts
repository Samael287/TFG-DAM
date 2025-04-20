import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ActoresService {

  private apiUrl = 'http://localhost:8080/api/actores'; // URL del backend

  constructor(private http: HttpClient) { }

  getActores(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  getActorById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  createActor(actor: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, actor);
  }

  updateActor(id: number, actor: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, actor);
  }

  deleteActor(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }
}
