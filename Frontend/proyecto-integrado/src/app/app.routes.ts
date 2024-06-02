import { Routes } from '@angular/router';
import { MainComponent } from './components/main/main.component';
import { LoginRegisterComponent } from './components/login-register/login-register.component';
import { PerfilComponent } from './components/perfil/perfil.component';
import { ListaOfertasComponent } from './components/lista-ofertas/lista-ofertas.component';
import { FormularioOfertaActividadComponent } from './components/formulario-oferta-actividad/formulario-oferta-actividad.component';
import { DatosOfertaComponent } from './components/datos-oferta/datos-oferta.component';
import { FormularioDemandaActividadComponent } from './components/formulario-demanda-actividad/formulario-demanda-actividad.component';
import { ListaDemandasComponent } from './components/lista-demandas/lista-demandas.component';
import { AdminPanelComponent } from './components/admin-panel/admin-panel.component';
import { UsuariosApuntadosOfertaComponent } from './components/usuarios-apuntados-oferta/usuarios-apuntados-oferta.component';

export const routes: Routes = [
  {
    path: "",
    component: MainComponent
  },
  {
    path: "login-register",
    component: LoginRegisterComponent
  },
  {
    path: "perfil/:id",
    component: PerfilComponent
  },
  {
    path: "actividades",
    component: ListaOfertasComponent
  },
  {
    path: "demandas",
    component: ListaDemandasComponent
  },
  {
    path: "crear-oferta-actividad",
    component: FormularioOfertaActividadComponent
  },
  {
    path: "crear-demanda-actividad",
    component: FormularioDemandaActividadComponent
  },
  {
    path: "datos-oferta/:id",
    component: DatosOfertaComponent
  },
  {
    path: "admin",
    component: AdminPanelComponent
  },
  {
    path: "usuarios-apuntados-oferta/:id",
    component: UsuariosApuntadosOfertaComponent
  }
];
