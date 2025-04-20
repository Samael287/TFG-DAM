import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LanguagesService {

  private apiUrl = 'http://localhost:8080/api/languages'; // URL del backend

  constructor(private http: HttpClient) { }

  getLanguages(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  createLanguage(language: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, language);
  }

  updateLanguage(id: number, language: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, language);
  }

  deleteLanguage(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }
}
