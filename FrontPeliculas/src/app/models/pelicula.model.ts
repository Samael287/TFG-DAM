export class Pelicula {
    
    titulo: string;
    descripcion: string;
    fecha_estreno: Date;
    duracion: number;
    precio: number;
    portada: string;
    studio: { id: number} | null;
    category: { id: number} | null;
    actores: {id: number} [];
    directores: {id: number} [];
    genres: {id: number} [];
    languages: {id: number} [];

    constructor(titulo: string, descripcion: string, fecha_estreno: Date, duracion: number, 
        precio: number, portada: string, studio: { id: number} | null, category: { id: number} | null, 
        actores: {id: number} [], directores: {id: number} [], genres: {id: number} [], 
        languages: {id: number} []) {

        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha_estreno = fecha_estreno;
        this.duracion = duracion;
        this.precio = precio;
        this.portada = portada;
        this.studio = studio;
        this.category = category;
        this.actores = actores;
        this.directores = directores;
        this.genres = genres;
        this.languages = languages;
    }
}
