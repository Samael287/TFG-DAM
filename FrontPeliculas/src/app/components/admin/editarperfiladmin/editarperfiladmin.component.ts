import { Component } from '@angular/core';
import { UsuariosService } from '../../../services/usuarios.service';
import { AuthService } from '../../../services/auth.service';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-editarperfiladmin',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './editarperfiladmin.component.html',
  styleUrl: './editarperfiladmin.component.css'
})
export class EditarPerfilAdminComponent {
 usuario: any = {};         // Datos del usuario
  nuevaPassword: string = ''; // Nueva contraseña
  confirmarPassword: string = ''; // Confirmación de nueva contraseña
  tarjeta: any = {};         // Datos de la tarjeta (vacío si no hay)
  mostrarFormularioTarjeta: boolean = false; // Para mostrar el formulario de tarjeta (cuando el usuario no tiene tarjeta asociada)
  verificado: boolean = false; // Indica si la contraseña ha sido verificada
  passwordActual: string = ''; // Contraseña actual para verificar

  constructor(
    private usuariosService: UsuariosService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    console.log('🔄 Cargando datos del usuario...');
    this.cargarDatosUsuario();
  }

  cargarDatosUsuario(): void {
    const userId = localStorage.getItem('userId');
    console.log("📌 ID del usuario autenticado:", userId);

    if (!userId) {
      alert('Error: No se pudo obtener el usuario.');
      return;
    }

    this.usuariosService.getUsuarios().subscribe(
      (usuarios) => {
        console.log("📥 Usuarios obtenidos de la API:", usuarios);
        // Buscar el usuario con el ID correspondiente
        this.usuario = usuarios.find((u: any) => u.id == userId);
      },
      (error) => {
        console.error('❌ Error al obtener usuario:', error);
      }
    );
  }

  // Método para verificar la contraseña actual
  verificarPassword(): void {
    const userId = localStorage.getItem('userId');
    console.log("📌 Verificando contraseña para el usuario ID:", userId);
    console.log("🔍 Contraseña ingresada:", this.passwordActual);

    if (!userId) {
      alert("Error: No se encontró el ID del usuario.");
      return;
    }

    this.usuariosService.verifyPassword(parseInt(userId), this.passwordActual).subscribe(
      (response) => {
        console.log("✅ Contraseña verificada:", response);
        this.verificado = true;
        alert("Contraseña verificada correctamente.");
      },
      (error) => {
        console.error("❌ Error al verificar contraseña:", error);
        alert("La contraseña es incorrecta.");
      }
    );
  }

  // Método para formatear la fecha al formato "YYYY-MM-DD"
  formatDate(date: string): string {
    if (!date) return ''; 
    const d = new Date(date);
    return d.toISOString().split('T')[0];
  }

  actualizarPerfil(): void {
    if (!this.verificado) {
      alert('Debes verificar tu contraseña antes de actualizar los datos.');
      return;
    }
  
    if (this.nuevaPassword && this.nuevaPassword !== this.confirmarPassword) {
      alert('Las contraseñas no coinciden.');
      return;
    }
  
    // Crear objeto solo con los campos a actualizar
    let usuarioActualizado: any = {
      id: this.usuario.id,
      nombre: this.usuario.nombre,
      apellidos: this.usuario.apellidos,
      email: this.usuario.email,
      rol: this.usuario.rol.toString(),
      // Si se ingresa una nueva contraseña se usa, sino se mantiene la actual
      password: this.nuevaPassword.trim() !== '' ? this.nuevaPassword : this.usuario.password,
      tarjeta: null // <-- Añadilo
    };
  
    console.log('📤 Enviando datos actualizados a la API:', usuarioActualizado);
  
    this.usuariosService.updateUsuario(this.usuario.id, usuarioActualizado).subscribe(
      (response) => {
        console.log('✅ Perfil actualizado con éxito:', response);

        //Actualizar el usuario al instante
        this.authService.setUser(response.usuario);
        alert('Perfil actualizado con éxito.');
      },
      (error) => {
        console.error('❌ Error en la actualización:', error);
        alert('Error al actualizar el perfil.');
      }
    );
    this.router.navigate(['/admin/gestiontotal']);
  }
}
