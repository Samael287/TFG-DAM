import { Component, OnInit } from '@angular/core';
import { ActoresService } from '../../../services/actores.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

interface Actor {
  id?: number;
  name?: string;
}

@Component({
  selector: 'app-actores',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './actores.component.html',
  styleUrl: './actores.component.css'
})
export class ActoresComponent implements OnInit {
  actores: Actor[] = [];
  showModal: boolean = false;
  isEditing: boolean = false;
  selectedActor: Actor = { name: '' };

  constructor(private actoresService: ActoresService) {}

  ngOnInit(): void {
    this.getActores();
  }

  getActores(): void {
    this.actoresService.getActores().subscribe(data => {
      this.actores = data;
    });
  }

  openModal(actor?: Actor): void {
    this.showModal = true;
    this.isEditing = !!actor;
    this.selectedActor = actor ? { ...actor } : { name: '' };
  }

  closeModal(): void {
    this.showModal = false;
    this.selectedActor = { name: '' };
  }

  saveActor(): void {
    if (this.isEditing && this.selectedActor.id) {
      this.actoresService.updateActor(this.selectedActor.id, this.selectedActor).subscribe(() => {
        this.getActores();
        this.closeModal();
      });
    } else {
      this.actoresService.createActor(this.selectedActor).subscribe(() => {
        this.getActores();
        this.closeModal();
      });
    }
  }

  deleteActor(id: number): void {
    if (confirm('¿Estás seguro de eliminar este actor?')) {
      this.actoresService.deleteActor(id).subscribe(() => {
        this.getActores();
      });
    }
  }
}
