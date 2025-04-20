import { Component, OnInit } from '@angular/core';
import { Categories } from '../../../models/categories.model';
import { CategoriesService } from '../../../services/categories.service';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-categories',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './categories.component.html',
  styleUrl: './categories.component.css'
})
export class CategoriesComponent implements OnInit {
  categories: Categories[] = [];
  showModal: boolean = false;
  isEditing: boolean = false;
  selectedCategory: Categories = { category: '' };

  constructor(private categoriesService: CategoriesService) {}

  ngOnInit(): void {
    this.getCategories();
  }

  getCategories(): void {
    this.categoriesService.getCategories().subscribe(data => {
      this.categories = data;
    });
  }

  openModal(category?: Categories): void {
    this.showModal = true;
    this.isEditing = category ? true : false;
    this.selectedCategory = category ? { ...category } : { category: '' };
  }

  closeModal(): void {
    this.showModal = false;
    this.selectedCategory = { category: '' };
  }

  saveCategory(): void {
    if (this.isEditing && this.selectedCategory.id) {
      this.categoriesService.updateCategory(this.selectedCategory.id, this.selectedCategory).subscribe(() => {
        this.getCategories();
        this.closeModal();
      });
    } else {
      this.categoriesService.createCategory(this.selectedCategory).subscribe(() => {
        this.getCategories();
        this.closeModal();
      });
    }
  }  

  deleteCategory(id: number): void {
    if (confirm('¿Estás seguro de eliminar esta categoría?')) {
      this.categoriesService.deleteCategory(id).subscribe(() => {
        this.getCategories();
      });
    }
  }
}
