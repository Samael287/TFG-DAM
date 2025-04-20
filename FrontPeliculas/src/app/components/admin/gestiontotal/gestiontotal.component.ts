import { CommonModule } from '@angular/common';
import { Component, ElementRef, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { PeliculasService } from '../../../services/peliculas.service';
import { AuthService } from '../../../services/auth.service';
import { CompraService } from '../../../services/compra.service';

@Component({
  selector: 'app-gestiontotal',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './gestiontotal.component.html',
  styleUrl: './gestiontotal.component.css'
})
export class GestionTotalComponent {

  @ViewChild('dropdownMenu') dropdownMenu!: ElementRef;
    @ViewChild('userDropdownMenu') userDropdownMenu!: ElementRef;
    // Referencias para mostrar/ocultar
  @ViewChild('estadisticasDropdown') estadisticasDropdown!: ElementRef;
    
      peliculas: any[] = [];
    peliculasFiltradas: any[] = [];
    categoriasFiltradas: string[] = [];
    generosFiltrados: string[] = [];
    filtroTitulo: string = '';
    agruparPorCategoria: boolean = false;
    agruparPorGenero: boolean = false;
    ordenAlfabetico: boolean = false;
    peliculaSeleccionada: any = null;
    dropdownAbierto: boolean = false;
    userDropdownOpen: boolean = false;
    isLoggedIn: boolean = false;
    estadisticasDropdownAbierto: boolean = false;
    estadisticasModalAbierto: boolean = false;
    tipoEstadisticaSeleccionado: string = 'DIARIO';
    estadisticas: any = null;

  
    constructor(
      private peliculasService: PeliculasService,
      private authService: AuthService, 
      private compraService: CompraService,
      private router: Router
    ) {}
  
    ngOnInit(): void {
      this.peliculasService.getPeliculas().subscribe(data => {
        this.peliculas = data;
        this.filtrarPeliculas();
      });
      this.isLoggedIn = this.authService.isAuthenticated();
    }
  
    filtrarPeliculas(): void {
      this.peliculasFiltradas = this.peliculas.filter(p =>
        p.titulo.toLowerCase().includes(this.filtroTitulo.toLowerCase())
      );
  
      // Ordenamos las categorías según el orden deseado
      this.categoriasFiltradas = [...new Set(this.peliculasFiltradas.map(p => p.category))]
  
      // Revisamos cómo llegan los géneros de cada película
    this.peliculasFiltradas.forEach((p, index) => {
      console.log(`🎬 Película ${index + 1}:`, p.titulo, "➡ Géneros:", p.genres);
    });
  
    // Corregimos la extracción de géneros
    this.generosFiltrados = [...new Set(this.peliculasFiltradas.flatMap(p => {
      if (!p.genres || !Array.isArray(p.genres)) {
        console.warn(`⚠️ Película "${p.titulo}" no tiene géneros válidos:`, p.genres);
        return []; // Si no tiene géneros, retornamos array vacío
      }
      return p.genres.map((g: any) => {
        if (typeof g === "string") return g; // Si es un string, lo dejamos como está
        if (g.genre) return g.genre; // Si es un objeto con `genre`, lo extraemos
        return null;
      }).filter((g: null) => g !== null);
    }))].sort();
  
    console.log("📌 Géneros disponibles después de corrección:", this.generosFiltrados);
  
    }
  
    toggleDropdown() {
      this.dropdownAbierto = !this.dropdownAbierto;
      if (this.dropdownMenu) {
        this.dropdownMenu.nativeElement.style.display = this.dropdownAbierto ? 'block' : 'none';
      }
    }
  
    obtenerPeliculasPorCategoria(categoria: string): any[] {
      return this.peliculasFiltradas.filter(p => p.category === categoria);
    }
  
    obtenerPeliculasPorGenero(genero: string): any[] {
      if (!genero) {
        console.error("❌ Error: Se intentó buscar películas con un género inválido:", genero);
        return [];
      }
    
      console.log(`🔎 Buscando películas para el género: "${genero}"`);
    
      const peliculasPorGenero = this.peliculasFiltradas.filter(pelicula => {
        if (!pelicula.genres || !Array.isArray(pelicula.genres)) {
          console.warn(`⚠️ Película "${pelicula.titulo}" tiene géneros inválidos:`, pelicula.genres);
          return false;
        }
        return pelicula.genres.includes(genero) || pelicula.genres.some((g: any) => g?.genre === genero);
      });
    
      console.log(`✅ Películas encontradas para género "${genero}":`, peliculasPorGenero);
      return peliculasPorGenero;
    }
    
    toggleAgruparPorCategoria(): void {
      this.agruparPorCategoria = true;
      this.agruparPorGenero = false;
      this.ordenAlfabetico = false;
      this.cerrarDropdown();
    }
  
    toggleAgruparPorGenero(): void {
      console.log("🔄 Agrupando por Género...");
      this.agruparPorGenero = true;
      this.agruparPorCategoria = false;
      this.ordenAlfabetico = false;
      
      if (this.generosFiltrados.length === 0) {
        console.warn("⚠️ No hay géneros disponibles para agrupar.");
        return;
      }
    
      console.log("🎭 Géneros encontrados para agrupar:", this.generosFiltrados);
      this.cerrarDropdown();
    }
    
  
    ordenarAlfabeticamente(): void {
      console.log("🔤 Ordenando películas alfabéticamente...");
    
      this.agruparPorCategoria = false;
      this.agruparPorGenero = false;
      this.ordenAlfabetico = true;
    
      if (!this.peliculasFiltradas || this.peliculasFiltradas.length === 0) {
        console.warn("⚠️ No hay películas disponibles para ordenar.");
        return;
      }
    
      this.peliculasFiltradas.sort((a, b) => a.titulo.localeCompare(b.titulo));
    
      console.log("✅ Películas ordenadas alfabéticamente:", this.peliculasFiltradas);
      this.cerrarDropdown();
    }
    
  
    quitarAgrupacion(): void {
      this.agruparPorCategoria = false;
      this.agruparPorGenero = false;
      this.ordenAlfabetico = false;
      this.filtrarPeliculas(); // Restaurar la lista original sin ordenación
      this.cerrarDropdown();
    }
  
    cerrarDropdown(): void {
      this.dropdownAbierto = false;
      if (this.dropdownMenu) {
        this.dropdownMenu.nativeElement.style.display = 'none';
      }
    }
    seleccionarPelicula(pelicula: any): void {
      this.peliculaSeleccionada = pelicula;
    }
  
    // Métodos para convertir listas en texto seguro para el template HTML
    obtenerDirectores(): string {
      if (!this.peliculaSeleccionada || !this.peliculaSeleccionada.directores) {
        console.log("❌ Directores no encontrados");
        return 'No disponible';
      }
    
      console.log("✅ Directores encontrados:", this.peliculaSeleccionada.directores);
    
      return this.peliculaSeleccionada.directores.length > 0
        ? this.peliculaSeleccionada.directores.join(', ') 
        : 'No disponible';
    }
    
    obtenerActores(): string {
      if (!this.peliculaSeleccionada || !this.peliculaSeleccionada.actores) {
        console.log("❌ Actores no encontrados");
        return 'No disponible';
      }
    
      console.log("✅ Actores encontrados:", this.peliculaSeleccionada.actores);
    
      return this.peliculaSeleccionada.actores.length > 0
        ? this.peliculaSeleccionada.actores.join(', ')  
        : 'No disponible';
    }
    
    obtenerGeneros(): string {
      if (!this.peliculaSeleccionada || !this.peliculaSeleccionada.genres) {
        console.log("❌ Géneros no encontrados");
        return 'No disponible';
      }
    
      console.log("✅ Géneros encontrados:", this.peliculaSeleccionada.genres);
    
      return this.peliculaSeleccionada.genres.length > 0
        ? this.peliculaSeleccionada.genres.join(', ') 
        : 'No disponible';
    }
    
    obtenerIdiomas(): string {
      if (!this.peliculaSeleccionada || !this.peliculaSeleccionada.languages) {
        console.log("❌ Idiomas no encontrados");
        return 'No disponible';
      }
    
      console.log("✅ Idiomas encontrados:", this.peliculaSeleccionada.languages);
    
      return this.peliculaSeleccionada.languages.length > 0
        ? this.peliculaSeleccionada.languages.join(', ') 
        : 'No disponible';
    }  
    cerrarDetalle(): void {
      this.peliculaSeleccionada = null;
    }
  
    abrirPerfil(): void {
      console.log("Abrir perfil de usuario");
    }
  
    abrirCarrito(): void {
      console.log("Abrir carrito de compras");
    }
  
     toggleUserDropdown() {
      this.userDropdownOpen = !this.userDropdownOpen;
      if (this.userDropdownMenu) {
        this.userDropdownMenu.nativeElement.style.display = this.userDropdownOpen ? 'block' : 'none';
      }
    }
  
    goToRegister() {
      this.router.navigate(['/registro']);
    }
  
    goToLogin() {
      this.router.navigate(['/login']);
    }
  
    goToEditProfile() {
      this.router.navigate(['/editarperfiladmin']);
    }
  
    goToPurchasedMovies() {
      this.router.navigate(['/mis-peliculas']);
    }
  
    logout() {
      this.authService.logout();
      this.isLoggedIn = false;
      this.router.navigate(['/peliculas']);
    }

  goTo(route: string): void {
    this.router.navigate([route]);
  }
   
  toggleEstadisticasDropdown(): void {
    this.estadisticasDropdownAbierto = !this.estadisticasDropdownAbierto;
  }

  abrirEstadisticasModal(tipo: string): void {
    // Cierra el dropdown
    this.estadisticasDropdownAbierto = false;
    // Asigna el tipo seleccionado y carga las estadísticas
    this.tipoEstadisticaSeleccionado = tipo;
    this.compraService.getEstadisticasParam(tipo).subscribe(data => {
      this.estadisticas = data;
      // Una vez cargadas, abre el modal
      this.estadisticasModalAbierto = true;
    }, error => {
      console.error("Error al cargar estadísticas:", error);
    });
  }

  cerrarEstadisticasModal(): void {
    this.estadisticasModalAbierto = false;
  }
}
