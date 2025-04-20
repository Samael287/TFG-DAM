import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/auth';

  private currentUserSubject = new BehaviorSubject<any>(JSON.parse(localStorage.getItem('user') || '{}'));
  public currentUser$ = this.currentUserSubject.asObservable();

  constructor(private http: HttpClient, private router: Router) {}

  /*login(credentials: { email: string; password: string }): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, credentials);
  }*/

    login(credentials: { email: string; password: string }): Observable<any> {
      return this.http.post<{ accessToken: string; role: string; usuario: any }>(`${this.apiUrl}/login`, credentials).pipe(
        tap(response => {
          this.saveToken(response.accessToken, response.role);
          console.log("📌 Respuesta del backend en login:", response);

          // ✅ Guardar el nombre del usuario en localStorage
          if (response.usuario && response.usuario.nombre) {
            localStorage.setItem('user', JSON.stringify(response.usuario));
            localStorage.setItem('username', response.usuario.nombre);
            localStorage.setItem('userId', response.usuario.id);
            console.log("✅ Usuario autenticado con ID:", response.usuario.id);

          } else {
            console.warn("⚠️ No se recibió el nombre del usuario en la respuesta.");
          }
        })
      );
    }
  

  register(user: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/registro`, user);
  }

  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    localStorage.removeItem('username');
    this.router.navigate(['/']);
  }

  saveToken(token: string, role: string): void {
    localStorage.setItem('token', token);
    localStorage.setItem('role', role);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  getRole(): string | null {
    return localStorage.getItem('role');
  }

  isAuthenticated(): boolean {
    return !!this.getToken();
  }

  isAdmin(): boolean {
    return this.getRole() === 'ADMIN';
  }

  getUsername(): string {
    return localStorage.getItem('username') || ''; // ✅ Obtiene el nombre del usuario
  }

   getUserId(): number | null {
    const userId = localStorage.getItem('userId');
    return userId ? Number(userId) : null;
  }

  public getUser(): any {
    const userData = localStorage.getItem('user');
    return userData ? JSON.parse(userData) : null;
  }

  setUser(user: any): void {
    localStorage.setItem('user', JSON.stringify(user));
    this.currentUserSubject.next(user);
  }
}