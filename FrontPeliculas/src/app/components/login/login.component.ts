import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  email: string = '';
  password: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  login(): void {
    this.authService.login({ email: this.email, password: this.password }).subscribe(
      (response) => {
        this.authService.saveToken(response.accessToken, response.role);
        const username = this.authService.getUsername(); // ✅ Obtiene el nombre del usuario

        alert('Login exitoso');
        if (response.role === 'ADMIN') {
          this.router.navigate(['/admin/gestiontotal']);
          alert(`Acceso concedido a Cine En Casa, ${username}!`); // ✅ Muestra alerta

        } else {
          this.router.navigate(['/peliculas']);
          alert(`¡Bienvenido a Cine En Casa, ${username}!`); // ✅ Muestra alerta
        }
      },
      (error) => {
        alert('Usuario o contraseña incorrectos');
      }
    );
  }

  goToRegister(): void {
    this.router.navigate(['/registro']);
  }
}
