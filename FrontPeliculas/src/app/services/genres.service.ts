import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GenresService {

  private apiUrl = 'http://localhost:8080/api/genres'; // URL del backend

  constructor(private http: HttpClient) { }

  getGenres(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  createGenre(genre: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, genre);
  }

  updateGenre(id: number, genre: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, genre);
  }

  deleteGenre(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }
}
