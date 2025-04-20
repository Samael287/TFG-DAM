import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class StudiosService {

  private apiUrl = 'http://localhost:8080/api/studios'; // URL del backend

  constructor(private http: HttpClient) { }

  getStudios(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  createStudio(studio: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, studio);
  }

  updateStudio(id: number, studio: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, studio);
  }

  deleteStudio(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }
}
