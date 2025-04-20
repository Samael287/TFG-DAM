import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  user = {
    nombre: '',
    apellidos: '',
    email: '',
    password: '',
    rol: 'CLIENTE' // Por defecto será CLIENTE
  };

  constructor(private authService: AuthService, private router: Router) {}

  register(): void {
  if (!this.user.nombre.trim()) {
    alert('El nombre es obligatorio.');
    return;
  }
  if (!this.user.apellidos.trim()) {
    alert('Los apellidos son obligatorios.');
    return;
  }
  if (!this.user.email.trim()) {
    alert('El correo electrónico es obligatorio.');
    return;
  }
  if (!this.validateEmail(this.user.email)) {
    alert('El correo electrónico no es válido.');
    return;
  }
  if (!this.user.password.trim()) {
    alert('La contraseña es obligatoria.');
    return;
  }
  if (this.user.password.length < 4) {
    alert('La contraseña debe tener al menos 6 caracteres.');
    return;
  }

  this.authService.register(this.user).subscribe(
    () => {
      alert('Registro exitoso. Ahora puedes iniciar sesión.');
      this.router.navigate(['/login']);
    },
    (error) => {
      alert('Error al registrar el usuario.');
    }
  );
}

// Función simple para validar el email
validateEmail(email: string): boolean {
  const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return re.test(email);
}
  goToLogin(): void {
    this.router.navigate(['/login']);
  }
}
