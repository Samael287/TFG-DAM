import { Component } from '@angular/core';
import { StudiosService } from '../../../services/studios.service';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';

interface Studio {
  id?: number;
  studio?: string;
}

@Component({
  selector: 'app-studios',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './studios.component.html',
  styleUrl: './studios.component.css'
})
export class StudiosComponent {

  studios: Studio[] = [];
  showModal: boolean = false;
  isEditing: boolean = false;
  selectedStudio: Studio = { studio: '' };

  constructor(private studiosService: StudiosService) {}

  ngOnInit(): void {
    this.getStudios();
  }

  getStudios(): void {
    this.studiosService.getStudios().subscribe(data => {
      this.studios = data;
    });
  }

  openModal(studio?: Studio): void {
    this.showModal = true;
    this.isEditing = !!studio;
    this.selectedStudio = studio ? { ...studio } : { studio: '' };
  }

  closeModal(): void {
    this.showModal = false;
    this.selectedStudio = { studio: '' };
  }

  saveStudio(): void {
    if (this.isEditing && this.selectedStudio.id) {
      this.studiosService.updateStudio(this.selectedStudio.id, this.selectedStudio).subscribe(() => {
        this.getStudios();
        this.closeModal();
      });
    } else {
      this.studiosService.createStudio(this.selectedStudio).subscribe(() => {
      this.getStudios();
      this.closeModal();
      });
    }
  }

  deleteStudio(id: number): void {
    if (confirm('¿Estás seguro de eliminar este estudio?')) {
      this.studiosService.deleteStudio(id).subscribe(() => {
        this.getStudios();
      });
    }
  }
}
