import { Routes } from '@angular/router';
import { PeliculasComponent } from './components/peliculas/peliculas.component';
import { CategoriesComponent } from './components/admin/categories/categories.component';
import { ActoresComponent } from './components/admin/actores/actores.component';
import { DirectoresComponent } from './components/admin/directores/directores.component';
import { FlagsComponent } from './components/admin/flags/flags.component';
import { GenresComponent } from './components/admin/genres/genres.component';
import { LanguagesComponent } from './components/admin/languages/languages.component';
import { StudiosComponent } from './components/admin/studios/studios.component';
import { GestionPeliculasComponent } from './components/admin/gestionpeliculas/gestionpeliculas.component';
import { GestionUsuariosComponent } from './components/admin/gestion-usuarios/gestion-usuarios.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { GestionTotalComponent } from './components/admin/gestiontotal/gestiontotal.component';
import { EditarPerfilComponent } from './components/editarperfil/editarperfil.component';
import { EditarPerfilAdminComponent } from './components/admin/editarperfiladmin/editarperfiladmin.component';

export const routes: Routes = [
    { path: 'peliculas', component: PeliculasComponent },
    { path: 'admin/categories', component: CategoriesComponent },
    { path: 'admin/actores', component: ActoresComponent },
    { path: 'admin/directores', component: DirectoresComponent },
    { path: 'admin/flags', component: FlagsComponent },
    { path: 'admin/genres', component: GenresComponent },
    { path: 'admin/languages', component: LanguagesComponent },
    { path: 'admin/studios', component: StudiosComponent },
    { path: 'admin/gestionpeliculas', component: GestionPeliculasComponent },
    { path: 'admin/gestionusuarios', component: GestionUsuariosComponent },
    { path: 'admin/gestiontotal', component: GestionTotalComponent },
    { path: 'editarperfil', component: EditarPerfilComponent},
    { path: 'editarperfiladmin', component: EditarPerfilAdminComponent},
    { path: 'login', component: LoginComponent },
    { path: 'registro', component: RegisterComponent },
    { path: '', redirectTo: '/peliculas', pathMatch: 'full' },
];
