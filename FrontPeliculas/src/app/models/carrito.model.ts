import { Pelicula } from "./pelicula.model";
import { Usuario } from "./usuario.model";

export class Carrito {
    id?: number;
    usuario?: Usuario;
    pelicula?: Pelicula;
}
