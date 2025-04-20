import { Component, OnInit } from '@angular/core';
import { PeliculasService } from '../../../services/peliculas.service';
import { CategoriesService } from '../../../services/categories.service';
import { ActoresService } from '../../../services/actores.service';
import { FlagsService } from '../../../services/flags.service';
import { GenresService } from '../../../services/genres.service';
import { LanguagesService } from '../../../services/languages.service';
import { StudiosService } from '../../../services/studios.service';
import { DirectoresService } from '../../../services/directores.service';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-gestionpeliculas',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './gestionpeliculas.component.html',
  styleUrl: './gestionpeliculas.component.css'
})
export class GestionPeliculasComponent implements OnInit {
  peliculas: any[] = [];
  categories: any[] = [];
  actores: any[] = [];
  flags: any[] = [];
  genres: any[] = [];
  languages: any[] = [];
  studios: any[] = [];
  directores: any[] = [];
  showModal: boolean = false;
  isEditing: boolean = false;
  selectedPelicula: any = {
    titulo: '',
    descripcion: '',
    fecha_estreno: '',
    duracion: 0,
    precio: 0,
    portada: '',
    studio: null,
    category: null,
    actores: [],
    directores: [],
    flagses: [],
    genres: [],
    languages: []
  };

  constructor(
    private peliculasService: PeliculasService,
    private categoriesService: CategoriesService,
    private actoresService: ActoresService,
    private flagsService: FlagsService,
    private genresService: GenresService,
    private languagesService: LanguagesService,
    private studiosService: StudiosService,
    private directoresService: DirectoresService
  ) {}

  ngOnInit(): void {
    this.getPeliculas();
    this.getCategories();
    this.getActores();
    this.getFlags();
    this.getGenres();
    this.getLanguages();
    this.getStudios();
    this.getDirectores();
  }

  getPeliculas(): void {
    this.peliculasService.getPeliculas().subscribe(data => {
      this.peliculas = data;
    });
  }

  getCategories(): void {
    this.categoriesService.getCategories().subscribe(data => {
      this.categories = data;
    });
  }

  getActores(): void {
    this.actoresService.getActores().subscribe(data => {
      this.actores = data;
    });
  }

  getFlags(): void {
    this.flagsService.getFlags().subscribe(data => {
      this.flags = data;
    });
  }

  getGenres(): void {
    this.genresService.getGenres().subscribe(data => {
      this.genres = data;
    });
  }

  getLanguages(): void {
    this.languagesService.getLanguages().subscribe(data => {
      this.languages = data;
    });
  }

  getStudios(): void {
    this.studiosService.getStudios().subscribe(data => {
      this.studios = data;
    });
  }

  getDirectores(): void {
    this.directoresService.getDirectores().subscribe(data => {
      this.directores = data;
    });
  }

  openModal(pelicula?: any): void {
    this.showModal = true;
    this.isEditing = !!pelicula;
  
    if (pelicula) {
      console.log("Datos de la película antes de asignar:", pelicula);
  
      this.selectedPelicula = {
        id: pelicula.id,
        titulo: pelicula.titulo,
        descripcion: pelicula.descripcion,
        fecha_estreno: pelicula.fecha_estreno,
        duracion: pelicula.duracion,
        precio: pelicula.precio,
        portada: pelicula.portada,
  
        // Buscar el ID del estudio por el nombre
        studio: this.studios.find(st => st.studio === pelicula.studio)?.id || null,
  
        // Buscar el ID de la categoría por el nombre
        category: this.categories.find(cat => cat.category === pelicula.category)?.id || null,
  
        // Buscar los IDs de los actores por nombre
        actores: pelicula.actores?.map((actorNombre: string) => 
          this.actores.find(actor => actor.name === actorNombre)?.id || null
        ).filter((id: number) => id !== null) || [],
  
        // Buscar los IDs de los directores por nombre
        directores: pelicula.directores?.map((directorNombre: string) => 
          this.directores.find(director => director.name === directorNombre)?.id || null
        ).filter((id: number) => id !== null) || [],
  
        // Buscar los IDs de los flags por nombre
        flagses: pelicula.flagses?.map((flagNombre: string) => 
          this.flags.find(flag => flag.flags === flagNombre)?.id || null
        ).filter((id: number) => id !== null) || [],
  
        // Buscar los IDs de los géneros por nombre
        genres: pelicula.genres?.map((genreNombre: string) => 
          this.genres.find(genre => genre.genre === genreNombre)?.id || null
        ).filter((id: number) => id !== null) || [],
  
        // Buscar los IDs de los idiomas por nombre
        languages: pelicula.languages?.map((languageNombre: string) => 
          this.languages.find(language => language.language === languageNombre)?.id || null
        ).filter((id: number) => id !== null) || []
      };
  
    } else {
      this.selectedPelicula = {
        titulo: '',
        descripcion: '',
        fecha_estreno: '',
        duracion: 0,
        precio: 0,
        portada: '',
        studio: null,
        category: null,
        actores: [],
        directores: [],
        flagses: [],
        genres: [],
        languages: []
      };
    }
  
    console.log("Película seleccionada para edición después de asignar:", this.selectedPelicula);
  }
  
  
  

  closeModal(): void {
    this.showModal = false;
    this.selectedPelicula = {
      titulo: '',
      descripcion: '',
      fecha_estreno: '',
      duracion: 0,
      precio: 0,
      portada: '',
      studio: null,
      category: null,
      actores: [],
      directores: [],
      flagses: [],
      genres: [],
      languages: []
    };
  }

  savePelicula(): void {
    // Convertir las relaciones en objetos con ID antes de enviarlas al backend
    this.selectedPelicula.studio = this.selectedPelicula.studio ? { id: this.selectedPelicula.studio } : null;
    this.selectedPelicula.category = this.selectedPelicula.category ? { id: this.selectedPelicula.category } : null;
    
    this.selectedPelicula.actores = this.selectedPelicula.actores.map((id: number) => ({ id }));
    this.selectedPelicula.directores = this.selectedPelicula.directores.map((id: number) => ({ id }));
    this.selectedPelicula.genres = this.selectedPelicula.genres.map((id: number) => ({ id }));
    this.selectedPelicula.flagses = this.selectedPelicula.flagses.map((id: number) => ({ id }));
    this.selectedPelicula.languages = this.selectedPelicula.languages.map((id: number) => ({ id }));
  
    console.log("Enviando JSON al backend:", JSON.stringify(this.selectedPelicula, null, 2));
  
    if (this.isEditing) {
      this.peliculasService.updatePelicula(this.selectedPelicula.id, this.selectedPelicula).subscribe(() => {
        this.getPeliculas();
        this.closeModal();
      });
    } else {
      this.peliculasService.createPelicula(this.selectedPelicula).subscribe(() => {
        this.getPeliculas();
        this.closeModal();
      });
    }
  }
  

  deletePelicula(id: number): void {
    if (confirm('¿Estás seguro de eliminar esta película?')) {
      this.peliculasService.deletePelicula(id).subscribe(() => {
        this.getPeliculas();
      });
    }
  }

  onFileChange(event: any): void {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.selectedPelicula.portada = e.target.result.split(',')[1]; // Se guarda la imagen en Base64
      };
      reader.readAsDataURL(file);
    }
  }  
}