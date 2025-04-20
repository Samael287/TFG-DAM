export class Tarjeta {
    id: number;
    numeroTarjeta: string;
    fechaCaducidad: Date;
    numeroSeguridad: string;
    fondosDisponibles: number;

    constructor(id: number, numeroTarjeta: string, fechaCaducidad: Date, numeroSeguridad: string, fondosDisponibles: number) {
        this.id = id;
        this.numeroTarjeta = numeroTarjeta;
        this.fechaCaducidad = fechaCaducidad;
        this.numeroSeguridad = numeroSeguridad;
        this.fondosDisponibles = fondosDisponibles;
    }
}
