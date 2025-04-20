import { Pelicula } from "./pelicula.model";
import { Tarjeta } from "./tarjeta.model";
import { Usuario } from "./usuario.model";

export class Compra {
    id?: number;
    pelicula?: Pelicula;
    usuario?: Usuario;
    tarjeta?: Tarjeta;
    precio?: number;
    fechaCompra?: Date;
    transaccionId?: number;
}