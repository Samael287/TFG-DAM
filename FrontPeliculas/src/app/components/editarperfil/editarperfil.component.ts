import { Component, OnInit } from '@angular/core';
import { UsuariosService } from '../../services/usuarios.service';
import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';
import { Route, Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-editarperfil',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './editarperfil.component.html',
  styleUrl: './editarperfil.component.css'
})
/*export class EditarPerfilComponent implements OnInit {
  usuario: any = {}; // Datos del usuario
  nuevaPassword: string = ''; // Nueva contraseña
  confirmarPassword: string = ''; // Confirmación de nueva contraseña
  tarjeta: any = {}; // Datos de la tarjeta
  mostrarTarjeta: boolean = false; // Mostrar los datos de la tarjeta solo si tiene una
  verificado: boolean = false; // Indica si la contraseña ha sido verificada
  passwordActual: string = ''; // Contraseña actual para verificar

  constructor(private usuariosService: UsuariosService, private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    console.log('🔄 Cargando datos del usuario...');
    this.cargarDatosUsuario();
  }

  cargarDatosUsuario(): void {
    const userId = localStorage.getItem('userId');  // ✅ Usamos el ID guardado en el login
    console.log("📌 ID del usuario autenticado desde localStorage:", userId);
  
    if (!userId) {
      alert('Error: No se pudo obtener el usuario.');
      return;
    }
  
    this.usuariosService.getUsuarios().subscribe(
      (usuarios) => {
        console.log("📥 Usuarios obtenidos de la API:", usuarios);
  
        // 🔥 Buscamos el usuario con el ID correcto
        this.usuario = usuarios.find((u: any) => u.id == userId);
  
        if (this.usuario.tarjeta) {
          this.tarjeta = { ...this.usuario.tarjeta };

          // ✅ Convertir la fecha al formato correcto si existe
          if (this.tarjeta.fechaCaducidad) {
            this.tarjeta.fechaCaducidad = this.formatDate(this.tarjeta.fechaCaducidad);
          }

          this.mostrarTarjeta = true;
        } else {
          console.warn("⚠️ No se encontró el usuario en la lista.");
        }
      },
      (error) => {
        console.error('❌ Error al obtener usuario:', error);
      }
    );
  }
  
  verificarPassword(): void {
    const userId = localStorage.getItem('userId');  // ✅ Obtenemos el ID desde el localStorage
    console.log("📌 Enviando solicitud de verificación para el usuario ID:", userId);
    console.log("🔍 Contraseña ingresada:", this.passwordActual);
  
    if (!userId) {
      alert("Error: No se encontró el ID del usuario.");
      return;
    }
  
    this.usuariosService.verifyPassword(parseInt(userId), this.passwordActual).subscribe(
      (response) => {
        console.log("✅ Respuesta de verificación:", response);
        this.verificado = true;
        alert("Contraseña verificada correctamente.");
      },
      (error) => {
        console.error("❌ Error al verificar contraseña:", error);
        alert("La contraseña es incorrecta.");
      }
    );
  }
  
  formatDate(date: string): string {
    if (!date) return ''; 
    const d = new Date(date);
    return d.toISOString().split('T')[0];  // Convierte a "YYYY-MM-DD"
  }

  actualizarPerfil(): void {
    if (!this.verificado) {
      console.warn('⚠️ Intento de actualización sin verificación de contraseña.');
      alert('Debes verificar tu contraseña antes de actualizar los datos.');
      return;
    }
  
    if (this.nuevaPassword && this.nuevaPassword !== this.confirmarPassword) {
      console.warn('❌ Error: Las contraseñas no coinciden.');
      alert('Las contraseñas no coinciden.');
      return;
    }
  
    // ✅ Creamos un objeto solo con los campos que se van a actualizar
    let usuarioActualizado: any = {
      id: this.usuario.id,
      nombre: this.usuario.nombre.trim() !== '' ? this.usuario.nombre : this.usuario.nombre,
      apellidos: this.usuario.apellidos.trim() !== '' ? this.usuario.apellidos : this.usuario.apellidos,
      email: this.usuario.email.trim() !== '' ? this.usuario.email : this.usuario.email, // 🔹 El email no debe cambiar
      rol: this.usuario.rol.toString(),
      // ✅ Si el usuario NO ingresó una nueva contraseña, se mantiene la actual
      password: this.nuevaPassword.trim() !== '' ? this.nuevaPassword : this.usuario.password
  
    };
  
    // ✅ Si el usuario tiene tarjeta, enviar solo los campos válidos
    if (this.mostrarTarjeta) {
      usuarioActualizado.tarjeta = {
        numeroTarjeta: this.tarjeta.numeroTarjeta?.trim() !== '' ? this.tarjeta.numeroTarjeta : this.usuario.tarjeta?.numeroTarjeta,
        fechaCaducidad: this.tarjeta.fechaCaducidad?.trim() !== '' ? this.tarjeta.fechaCaducidad : this.usuario.tarjeta?.fechaCaducidad,
        numeroSeguridad: this.tarjeta.numeroSeguridad?.trim() !== '' ? this.tarjeta.numeroSeguridad : this.usuario.tarjeta?.numeroSeguridad,
        fondosDisponibles: this.tarjeta.fondosDisponibles !== null && this.tarjeta.fondosDisponibles !== undefined
          ? this.tarjeta.fondosDisponibles
          : this.usuario.tarjeta?.fondosDisponibles
      };
    }
  
    console.log('📤 Enviando datos actualizados a la API:', usuarioActualizado);
  
    this.usuariosService.updateUsuario(this.usuario.id, usuarioActualizado).subscribe(
      (response) => {
        console.log('✅ Perfil actualizado con éxito:', response);
        alert('Perfil actualizado con éxito.');
      },
      (error) => {
        console.error('❌ Error en la actualización:', error);
        alert('Error al actualizar el perfil.');
      }
    );
    this.router.navigate(['/peliculas']);
  }  
}*/

export class EditarPerfilComponent implements OnInit {
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

        if (this.usuario) {
          // Si el usuario ya tiene tarjeta, asignamos sus datos
          if (this.usuario.tarjeta) {
            this.tarjeta = { ...this.usuario.tarjeta };
            // Si hay fecha, formatearla al formato "YYYY-MM-DD"
            if (this.tarjeta.fechaCaducidad) {
              this.tarjeta.fechaCaducidad = this.formatDate(this.tarjeta.fechaCaducidad);
            }
            // En este caso, se muestran los datos existentes sin necesidad de activar el formulario
            this.mostrarFormularioTarjeta = false;
          } else {
            // Si no tiene tarjeta, inicialmente no se muestran los campos
            this.mostrarFormularioTarjeta = false;
            this.tarjeta = {}; // Inicialmente vacío
          }
        } else {
          console.warn("⚠️ No se encontró el usuario con ese ID.");
        }
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

  // Método para activar el formulario de tarjeta (cuando el usuario no tenga tarjeta)
  activarFormularioTarjeta(): void {
    this.mostrarFormularioTarjeta = true;
    // Inicializa el objeto tarjeta para que el usuario pueda rellenarlo
    this.tarjeta = { numeroTarjeta: '', fechaCaducidad: '', numeroSeguridad: '', fondosDisponibles: 0 };
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
      password: this.nuevaPassword.trim() !== '' ? this.nuevaPassword : this.usuario.password
    };
  
    // Incluir la información de la tarjeta si el usuario ya tiene una o si activó el formulario para añadirla
    if (this.usuario.tarjeta || this.mostrarFormularioTarjeta) {
      usuarioActualizado.tarjeta = {
        numeroTarjeta: this.tarjeta.numeroTarjeta,
        fechaCaducidad: this.tarjeta.fechaCaducidad,
        numeroSeguridad: this.tarjeta.numeroSeguridad,
        fondosDisponibles: this.tarjeta.fondosDisponibles !== null && this.tarjeta.fondosDisponibles !== undefined
          ? this.tarjeta.fondosDisponibles
          : this.usuario.tarjeta?.fondosDisponibles
      };
    }
  
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
    this.router.navigate(['/peliculas']);
  }
}