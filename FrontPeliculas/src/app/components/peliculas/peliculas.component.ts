import { Component, ElementRef, NgModule, OnInit, ViewChild } from '@angular/core';
import { PeliculasService } from '../../services/peliculas.service';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { CarritoService } from '../../services/carrito.service';
import { CompraService } from '../../services/compra.service';
import { forkJoin } from 'rxjs';

interface Pelicula {
  titulo: string;
  descripcion: string;
  fecha_estreno: Date;
  duracion: number;
  precio: number;
  portada: string;
  studio: { id: number };
  category: { id: number };
  actores: {
    name: any; id: number 
}[];
  directores: {
    name: any; id: number 
}[];
  genres: {
    genre: any; id: number 
}[];
  languages: {
    language: any; id: number 
}[];  
}  

@Component({
  selector: 'app-peliculas',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './peliculas.component.html',
  styleUrl: './peliculas.component.css'
})
export class PeliculasComponent implements OnInit {
  @ViewChild('dropdownMenu') dropdownMenu!: ElementRef;
  @ViewChild('userDropdownMenu') userDropdownMenu!: ElementRef;
  @ViewChild('cartDropdownMenu') cartDropdownMenu!: ElementRef;

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
  username: string = '';
  carrito: any[] = [];
  carritoAbierto: boolean = false;
  totalCarrito: number = 0;
  // NUEVAS propiedades para las compras:
  compras: any[] = [];
  comprasAbierto: boolean = false;

   // Nuevas propiedades para tendencias y recomendaciones
   tendenciasModalAbierto: boolean = false;
   //tendencias: any = null;  // Objeto para guardar las estadísticas globales
   tendencias: any = {
    masVendidas: [],
    masVendidaMes: null
  };
  
   recomendacionModalAbierto: boolean = false;
   recomendaciones: any[] = [];  // Recomendaciones por historial

  constructor(
    private peliculasService: PeliculasService,
    private authService: AuthService, 
    private carritoService: CarritoService,
    private compraService: CompraService,  // Inyectamos el servicio de compras
    private router: Router
  ) {}

  ngOnInit(): void {
    this.peliculasService.getPeliculas().subscribe(data => {
      this.peliculas = data;
      this.filtrarPeliculas();
    });
    this.isLoggedIn = this.authService.isAuthenticated();
    if (this.isLoggedIn) {
      this.username = this.authService.getUsername(); // ✅ Obtiene el nombre
      this.obtenerCarrito();
      this.obtenerCompras(); // Cargar compras del usuario
    }
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
    this.router.navigate(['/editarperfil']);
  }
  
  logout() {
    this.authService.logout();
    this.isLoggedIn = false;
    this.carritoAbierto = false; // Cierra el dropdown del carrito
    this.carrito = []; // Opcional: limpia el carrito
    this.compras = [];
    this.router.navigate(['/peliculas']);
  }

  agregarAlCarrito(pelicula: any): void {
    if (!this.isLoggedIn) {
      alert('Debes iniciar sesión para agregar al carrito');
      return;
    }
  
    const usuarioId = this.authService.getUserId();
    if (usuarioId === null) {
      console.error('No se pudo obtener el ID del usuario');
      return;
    }
  
    this.carritoService.agregarAlCarrito(usuarioId, pelicula.id).subscribe(() => {
      this.obtenerCarrito();
      alert('Película añadida al carrito');
    }, error => {
      console.error('Error al agregar al carrito', error);
    });
  }
  
  obtenerCarrito(): void {
    const usuarioId = this.authService.getUserId();
    if (usuarioId === null) {
      console.error('No se pudo obtener el ID del usuario');
      return;
    }
  
    this.carritoService.getCarritoPorUsuario(usuarioId).subscribe(data => {
      this.carrito = data;
      this.calcularTotal();
    });
  }
  
  eliminarDelCarrito(peliculaId: number): void {
    const usuarioId = this.authService.getUserId();
    if (usuarioId === null) {
      console.error('No se pudo obtener el ID del usuario');
      return;
    }
  
    this.carritoService.eliminarPeliculaDelCarrito(usuarioId, peliculaId).subscribe(() => {
      this.obtenerCarrito();
    });
  }
  

  calcularTotal(): void {
    this.totalCarrito = this.carrito.reduce((total, item) => total + item.pelicula.precio, 0).toFixed(2);
  }

  toggleCarrito(): void {
    if (!this.isLoggedIn) {
      alert("No disponible, debes iniciar sesión.");
      return;
    }
    this.carritoAbierto = !this.carritoAbierto;
    if (this.cartDropdownMenu) {
      this.cartDropdownMenu.nativeElement.style.display = this.carritoAbierto ? 'block' : 'none';
    }
  }

  // Función para realizar la compra de todos los ítems del carrito
  /*comprar(): void {
    if (!this.isLoggedIn) {
      alert("Debes iniciar sesión para comprar.");
      return;
    }
    const usuarioId = this.authService.getUserId();
    if (!usuarioId || this.carrito.length === 0) {
      alert("No hay películas en el carrito para comprar.");
      return;
    }
    
    const user = this.authService.getUser(); 
    if (!user || !user.tarjeta) {
      alert("No tienes tarjeta asociada para comprar.");
      return;
    }
    // Preparar objeto de compra: se envía el id del usuario, el id de la tarjeta (suponiendo que se almacena en el usuario o en el carrito) y la lista de películas
    const compra = {
      usuario: { id: usuarioId },
      tarjeta: { id: this.carrito[0].usuario.tarjeta.id },
      peliculaIds: this.carrito.map(item => item.pelicula.id)
    };

    this.compraService.realizarCompra(compra).subscribe(response => {
      alert('Compra realizada con éxito.');
      // Vaciar carrito (o volver a obtenerlo)
      this.carrito = [];
      this.totalCarrito = 0;
      // Actualizar compras del usuario
      this.obtenerCompras();
    }, error => {
      console.error('Error al realizar la compra', error);
      alert(error.error.mensaje || 'Error al realizar la compra');
    });
  }*/

    comprar(): void {
      if (!this.isLoggedIn) {
        alert("Debes iniciar sesión para comprar.");
        return;
      }
      const usuarioId = this.authService.getUserId();
      if (!usuarioId || this.carrito.length === 0) {
        alert("No hay películas en el carrito para comprar.");
        return;
      }
      
      const user = this.authService.getUser(); 
      if (!user || !user.tarjeta) {
        alert("No tienes tarjeta asociada para comprar.");
        return;
      }
      // Preparar objeto de compra: se envía el id del usuario, el id de la tarjeta (suponiendo que se almacena en el usuario o en el carrito) y la lista de películas
      const compra = {
        usuario: { id: usuarioId },
        tarjeta: { id: user.tarjeta.id },
        peliculaIds: this.carrito.map(item => item.pelicula.id)
      };
  
      this.compraService.realizarCompra(compra).subscribe(response => {
        alert('Compra realizada con éxito.');
        // Vaciar carrito (o volver a obtenerlo)
        this.carrito = [];
        this.totalCarrito = 0;
        // Actualizar compras del usuario
        this.obtenerCompras();
      }, error => {
        console.error('Error al realizar la compra', error);
        alert(error.error.mensaje || 'Error al realizar la compra');
      });
    }

  // Funcionalidad para compras (películas ya compradas)
  toggleCompras(): void {
    this.comprasAbierto = !this.comprasAbierto;
    if (this.comprasAbierto) {
      this.obtenerCompras();
    }
    // Si se abre el dropdown de compras, se puede cerrar el de carrito (opcional)
    if (this.comprasAbierto && this.cartDropdownMenu) {
      this.cartDropdownMenu.nativeElement.style.display = 'none';
      this.carritoAbierto = false;
    }
  }

  obtenerCompras(): void {
    const usuarioId = this.authService.getUserId();
    if (usuarioId === null) {
      console.error('No se pudo obtener el ID del usuario');
      return;
    }
    this.compraService.getComprasPorUsuario(usuarioId).subscribe(data => {
      this.compras = data;
    }, error => {
      console.error('Error al obtener las compras', error);
    });
  }

  toggleTendenciasModal(): void {
    this.tendenciasModalAbierto = !this.tendenciasModalAbierto;
    if (this.tendenciasModalAbierto) {
      this.cargarTendencias();
    }
  }

  cargarTendencias(): void {
    console.log("Cargando tendencias globales y mensual...");
    forkJoin({
      masVendidas: this.compraService.getPeliculasMasVendidas(),
      masVendidaMes: this.compraService.getPeliculaMasVendidaMes()
    }).subscribe(
      data => {
        console.log("Tendencias recibidas:", data);
        // Si no se encuentra película del mes, se asigna null (o algún objeto de control)
        if (!data.masVendidaMes) {
          console.warn("No se encontró película para el mes.");
          data.masVendidaMes = null;
        }
        this.tendencias = data;
      },
      error => {
        console.error("Error al cargar las tendencias:", error);
      }
    );
  }
    

  // Abre o cierra el modal de recomendaciones (sólo para usuarios logueados)
  toggleRecomendacionModal(): void {
    if (!this.isLoggedIn) {
      alert("Debes iniciar sesión para ver recomendaciones.");
      return;
    }
    this.recomendacionModalAbierto = !this.recomendacionModalAbierto;
    if (this.recomendacionModalAbierto) {
      this.cargarRecomendaciones();
    }
  }

  cargarRecomendaciones(): void {
    const usuarioId = this.authService.getUserId();
    if (!usuarioId) {
      return;
    }
    this.compraService.getRecomendaciones(usuarioId).subscribe(
      data => {
        this.recomendaciones = data;
      },
      error => {
        console.error("Error al cargar las recomendaciones:", error);
      }
    );
  }
}