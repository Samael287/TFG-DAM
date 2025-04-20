import { Component } from '@angular/core';
import { DirectoresService } from '../../../services/directores.service';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';

interface Director {
  id?: number;
  name?: string;
}

@Component({
  selector: 'app-directores',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './directores.component.html',
  styleUrl: './directores.component.css'
})
export class DirectoresComponent {

  directores: Director[] = [];
  showModal: boolean = false;
  isEditing: boolean = false;
  selectedDirector: Director = { name: '' };

  constructor(private directoresService: DirectoresService) {}

  ngOnInit(): void {
    this.getDirectores();
  }

  getDirectores(): void {
    this.directoresService.getDirectores().subscribe(data => {
    this.directores = data;
    });
  }

  openModal(director?: Director): void {
    this.showModal = true;
    this.isEditing = !!director;
    this.selectedDirector = director ? { ...director } : { name: '' };
  }

  closeModal(): void {
    this.showModal = false;
    this.selectedDirector = { name: '' };
  }

  saveDirector(): void {
    if (this.isEditing && this.selectedDirector.id) {
      this.directoresService.updateDirector(this.selectedDirector.id, this.selectedDirector).subscribe(() => {
      this.getDirectores();
      this.closeModal();
     });
    } else {
      this.directoresService.createDirector(this.selectedDirector).subscribe(() => {
      this.getDirectores();
      this.closeModal();
      });
    }
  }

  deleteDirector(id: number): void {
    if (confirm('¿Estás seguro de eliminar este director?')) {
      this.directoresService.deleteDirector(id).subscribe(() => {
      this.getDirectores();
      });
    }
  }
}
