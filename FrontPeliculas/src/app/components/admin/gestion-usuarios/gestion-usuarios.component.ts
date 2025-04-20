import { Component, OnInit } from '@angular/core';
import { UsuariosService } from '../../../services/usuarios.service';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { UserRole } from '../../../models/user-role.enum';

interface Tarjeta {
  id?: number;
  numeroTarjeta?: string;
  fechaCaducidad?: string; // Se envía como string en formato YYYY-MM-DD
  numeroSeguridad?: string;
  fondosDisponibles?: number;
}

interface Usuario {
  id?: number;
  nombre?: string;
  apellidos?: string;
  email?: string;
  password?: string;
  rol?: UserRole;
  tarjeta?: Tarjeta | null;
}
@Component({
  selector: 'app-gestion-usuarios',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './gestion-usuarios.component.html',
  styleUrl: './gestion-usuarios.component.css'
})
export class GestionUsuariosComponent implements OnInit {
  usuarios: Usuario[] = [];
  showModal: boolean = false;
  isEditing: boolean = false;
  selectedUsuario!: Usuario;

  constructor(private usuariosService: UsuariosService) {}

  ngOnInit(): void {
    this.getUsuarios();
  }

  getUsuarios(): void {
    this.usuariosService.getUsuarios().subscribe(data => {
      this.usuarios = data;
    });
  }

  openModal(usuario?: Usuario): void {
  this.showModal = true;
  this.isEditing = !!usuario;
  
  this.selectedUsuario = usuario
    ? { 
        ...usuario, 
        tarjeta: usuario.tarjeta 
          ? { 
              ...usuario.tarjeta, 
              // Forzar el formato "yyyy-MM-dd" usando la función formatDate
              fechaCaducidad: this.formatDate(usuario.tarjeta.fechaCaducidad)
            } 
          : null 
      }
    : {
        id: undefined,
        nombre: '',
        apellidos: '',
        email: '',
        password: '',
        rol: UserRole.CLIENTE,
        tarjeta: null
      };
}

  closeModal(): void {
    this.showModal = false;
  }

  saveUsuario(): void {
    if (this.selectedUsuario.tarjeta) {
      this.selectedUsuario.tarjeta.fechaCaducidad = this.formatDate(this.selectedUsuario.tarjeta.fechaCaducidad);
    }

    if (this.isEditing && this.selectedUsuario.id) {
      this.usuariosService.updateUsuario(this.selectedUsuario.id, this.selectedUsuario).subscribe(() => {
        console.log('✅ Usuario actualizado correctamente');
        this.getUsuarios();
        this.closeModal();
      },
        error => console.error('❌ Error al actualizar usuario:', error)
      );
    } else {
      this.usuariosService.createUsuario(this.selectedUsuario).subscribe(() => {
        console.log('✅ Usuario creado correctamente');
        this.getUsuarios();
        this.closeModal();
      },
        error => console.error('❌ Error al crear usuario:', error)
     );
    }
  }

  deleteUsuario(id: number, tarjeta?: Tarjeta | null): void {
    let mensaje = tarjeta
      ? '⚠️ Este usuario tiene una tarjeta asociada. Si lo eliminas, la tarjeta quedará en el registro pero será inutilizable.\n\n¿Deseas eliminar el usuario igualmente?'
      : '¿Estás seguro de eliminar este usuario?';
  
    if (confirm(mensaje)) {
      this.usuariosService.deleteUsuario(id).subscribe(() => {
        console.log('✅ Usuario eliminado correctamente');
        this.getUsuarios();
      },
        error => console.error('❌ Error al eliminar usuario:', error)
      );
    }
  }

  formatDate(date: any): string {
    if (!date) return '';
    // Si es una cadena y no tiene "T", reemplazar el espacio por "T"
    if (typeof date === 'string' && date.indexOf('T') === -1 && date.indexOf(' ') !== -1) {
      date = date.replace(' ', 'T');
    }
    const d = new Date(date);
    if (isNaN(d.getTime())) return '';
    return `${d.getFullYear()}-${String(d.getMonth() + 1).padStart(2, '0')}-${String(d.getDate()).padStart(2, '0')}`;
  }
  

  agregarTarjeta(): void {
    if (!this.selectedUsuario.tarjeta) {
      this.selectedUsuario.tarjeta = {
        numeroTarjeta: '',
        fechaCaducidad: '',
        numeroSeguridad: '',
        fondosDisponibles: 0
      };
    }
  }

  eliminarTarjeta(): void {
    this.selectedUsuario.tarjeta = null;
  }
}