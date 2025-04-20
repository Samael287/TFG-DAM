import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FlagsService {

  private apiUrl = 'http://localhost:8080/api/flags'; // URL del backend

  constructor(private http: HttpClient) { }

  getFlags(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  createFlag(flag: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, flag);
  }

  updateFlag(id: number, flag: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, flag);
  }

  deleteFlag(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }
}
