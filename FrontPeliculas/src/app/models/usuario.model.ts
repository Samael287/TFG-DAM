import { UserRole } from "./user-role.enum";

export class Usuario {
    id: number;
    nombre: string;
    apellidos: string;
    email: string;
    password: string;
    rol: UserRole;
    tarjeta: { 
        id: number,
        numeroTarjeta: string,
        fechaCaducidad: Date,
        numeroSeguridad: string
    } | null;

    constructor(id: number, nombre: string, apellidos: string, email: string, password: string, rol: UserRole, tarjeta: { 
        id: number,
        numeroTarjeta: string,
        fechaCaducidad: Date,
        numeroSeguridad: string
    } | null) {

        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.tarjeta = tarjeta;
    }
}
