import { Component } from '@angular/core';
import { LanguagesService } from '../../../services/languages.service';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';

interface Languages {
  id?: number;
  language?: string;
}

@Component({
  selector: 'app-languages',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './languages.component.html',
  styleUrl: './languages.component.css'
})
export class LanguagesComponent {

  languages: Languages[] = [];
  showModal: boolean = false;
  isEditing: boolean = false;
  selectedLanguage: Languages = { language: '' };

  constructor(private languagesService: LanguagesService) {}

  ngOnInit(): void {
    this.getLanguages();
  }

  getLanguages(): void {
    this.languagesService.getLanguages().subscribe(data => {
    this.languages = data;
   });
  }

  openModal(language?: Languages): void {
    this.showModal = true;
    this.isEditing = !!language;
    this.selectedLanguage = language ? { ...language } : { language: '' };
  }

  closeModal(): void {
    this.showModal = false;
    this.selectedLanguage = { language: '' };
  }

  saveLanguage(): void {
    if (this.isEditing && this.selectedLanguage.id) {
      this.languagesService.updateLanguage(this.selectedLanguage.id, this.selectedLanguage).subscribe(() => {
        this.getLanguages();
         this.closeModal();
      });
    } else {
      this.languagesService.createLanguage(this.selectedLanguage).subscribe(() => {
        this.getLanguages();
        this.closeModal();
      });
    }
  }

  deleteLanguage(id: number): void {
    if (confirm('¿Estás seguro de eliminar este lenguaje?')) {
      this.languagesService.deleteLanguage(id).subscribe(() => {
        this.getLanguages();
      });
    }
  }
}
