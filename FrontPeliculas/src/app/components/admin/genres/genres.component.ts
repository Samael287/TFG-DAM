import { Component } from '@angular/core';
import { GenresService } from '../../../services/genres.service';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';

interface Genre {
  id?: number;
  genre?: string;
}

@Component({
  selector: 'app-genres',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './genres.component.html',
  styleUrl: './genres.component.css'
})
export class GenresComponent {

  genres: Genre[] = [];
  showModal: boolean = false;
  isEditing: boolean = false;
  selectedGenre: Genre = { genre: '' };

  constructor(private genresService: GenresService) {}

  ngOnInit(): void {
    this.getGenres();
  }

  getGenres(): void {
    this.genresService.getGenres().subscribe(data => {
      this.genres = data;
    });
  }

  openModal(genre?: Genre): void {
    this.showModal = true;
    this.isEditing = !!genre;
    this.selectedGenre = genre ? { ...genre } : { genre: '' };
  }

  closeModal(): void {
    this.showModal = false;
    this.selectedGenre = { genre: '' };
  }

  saveGenre(): void {
    if (this.isEditing && this.selectedGenre.id) {
      this.genresService.updateGenre(this.selectedGenre.id, this.selectedGenre).subscribe(() => {
        this.getGenres();
        this.closeModal();
      });
    } else {
      this.genresService.createGenre(this.selectedGenre).subscribe(() => {
        this.getGenres();
        this.closeModal();
      });
    }
  }

  deleteGenre(id: number): void {
    if (confirm('¿Estás seguro de eliminar este Genre?')) {
      this.genresService.deleteGenre(id).subscribe(() => {
        this.getGenres();
      });
    }
  }
}
