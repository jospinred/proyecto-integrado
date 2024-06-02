import { Component, ElementRef, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { DataViewModule } from 'primeng/dataview';
import { PAjaxService } from '../../services/p-ajax.service';
import { UpdateNavService } from '../../services/update-nav.service';
import { TagModule } from 'primeng/tag';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';

@Component({
  selector: 'app-perfil',
  standalone: true,
  imports: [ButtonModule, FormsModule, DataViewModule, RouterLink, TagModule, ToastModule],
  templateUrl: './perfil.component.html',
  styleUrl: './perfil.component.css'
})
export class PerfilComponent {
  @ViewChild('modalModificacionDemanda') modalModificacionDemanda!: ElementRef;
  @ViewChild('modalModificacionOferta') modalModificacionOferta!: ElementRef;
  @ViewChild('contenedorModificacionDemandaOferta') contenedorModificacionDemandaOferta!: ElementRef;
  userUsername?: string;
  passModificar?: any = "";
  esConsumidor?: boolean;
  esOfertante?: boolean;
  esAdmin?: boolean;
  profileOwner: boolean = false;
  actAddPref?: any;
  actAddCual?: any;
  idUsuario?: any;
  preferenciasModificar: any[] = [];
  cualificacionesModificar: any[] = [];
  datosModificarDemanda?: any = {
    id: -1,
    fecha: "",
    localidad: "",
    actividad: "",
    creadorDemanda: ""
  };
  datosModificarOferta?: any = {
    id: -1,
    fecha: "",
    localidad: "",
    actividad: "",
    creadorOferta: "",
    descripcion: "",
    duracion: "",
    materialNecesario: "",
    materialOfertado: "",
    maximoPlazas: -1,
    minimoPlazas: -1,
    participantes: -1,
    preparacionNecesaria: "",
    tarifa: -1
  };
  public actividadesDemanda: any[] = [];
  public actividadesOferta: any[] = [];
  public actividades: any[] = [];
  public actividadesFiltradas: any[] = [];
  public cualificacionesFiltradas: any[] = [];
  listaOfertasUsuario: any[] = []
  listaOfertasApuntado: any[] = [];
  listaDemandasUsuario: any[] = [];
  telefonoUsuario?: any;
  constructor(private rutaActiva: ActivatedRoute, private peticion: PAjaxService, private ruta: Router, private updateNav: UpdateNavService, private messageService: MessageService) {
    this.idUsuario = parseInt(localStorage.getItem("id") ?? "-1");
    let permisos = localStorage.getItem("permisos");
    if (permisos == "1" || permisos == "4" || permisos == "6" || permisos == "9") {
      this.esConsumidor = true;
    }
    if(permisos == "3" || permisos == "4" || permisos == "8" || permisos == "9"){
      this.esOfertante = true;
    }
    if (permisos == "5" || permisos == "6" || permisos == "8" || permisos == "9") {
      this.esAdmin = true;
    }
    if (rutaActiva.snapshot.params["id"] == localStorage.getItem("id")) {
      this.profileOwner = true;
    }
    this.peticion.listarActividades().subscribe({
      next: datos => {
        this.actividades = datos;
      }
    })
    this.cargarDatos();
  }

  cargarDatos() {
    this.peticion.obtenerUsuarioPorId(this.rutaActiva.snapshot.params["id"]).subscribe({
      next: datos => {
        this.userUsername = datos.username;
        this.telefonoUsuario = datos.telefono;
      },
      error: error => console.log(error)
    });
    this.peticion.listaOfertasUsuario(this.rutaActiva.snapshot.params["id"]).subscribe({
      next: datos => {
        this.listaOfertasUsuario = datos;
      },
      error: error => console.log(error)
    });
    this.peticion.listaDemandasUsuario(this.rutaActiva.snapshot.params["id"]).subscribe({
      next: datos => {
        this.listaDemandasUsuario = datos;
      },
      error: error => console.log(error)
    });
    this.peticion.listarPreferenciasConsumidor(this.idUsuario).subscribe({
      next: datos => {
        this.preferenciasModificar = datos;
        this.preferenciasModificar.sort((a, b) => a.id - b.id);
        this.actividadesFiltradas = this.actividades.filter(actividad =>
          !this.preferenciasModificar.some(pref => pref.id === actividad.id && pref.nombre === actividad.nombre)
        );
        this.actAddPref = "";
      }
    });
    this.peticion.listarCualificacionesConsumidor(this.rutaActiva.snapshot.params["id"]).subscribe({
      next: datos => {
        this.cualificacionesModificar = datos;
        this.cualificacionesModificar.sort((a, b) => a.id - b.id);
        this.cualificacionesFiltradas = this.actividades.filter(actividad =>
          !this.cualificacionesModificar.some(cua => cua.id === actividad.id && cua.nombre === actividad.nombre)
        );
        this.actAddCual = "";
      }
    });
    this.peticion.listarOfertasApuntado(this.rutaActiva.snapshot.params["id"]).subscribe({
      next: datos => {
        let regex = /nombre='(.*?)'/;
        this.listaOfertasApuntado = datos;

        this.listaOfertasApuntado.forEach(ofertaApuntado => {
          let match = ofertaApuntado.actividad.match(regex);
          ofertaApuntado.actividad = match[1];
        });
      },
      error: error => console.log(error)
    });
  }

  modificarDatos() {
    if (this.passModificar == "") {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'La contraseña no puede estar vacía' });
    } else if (confirm("¿Seguro que quieres actualizar tu contraseña?")) {
      let datos = {
        id: localStorage.getItem("id"),
        password: this.passModificar
      }
      this.peticion.actualizarUsuarioPorId(datos).subscribe({
        next: datos => {
          this.messageService.add({ severity: 'success', summary: 'Contraseña cambiada', detail: 'La contraseña ha sido cambiada correctamente' });
        },
        error: error => {
          this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Se ha producido un error al cambiar la contraseña' });
          console.log(error);
        }
      });
      this.passModificar = "";
    }

  }

  borrarOfertaUsuario(id: number) {
    this.peticion.borrarOfertaPorId(id).subscribe({
      next: datos => {
        this.messageService.add({ severity: 'success', summary: 'Eliminado', detail: 'Has eliminado el anuncio correctamente.' });
        this.cargarDatos();
      },
      error: error => {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Se ha producido un error.' });
        console.log(error);
      }
    });
  }

  modificarOfertaUsuario() {
    this.peticion.actualizarOferta(this.datosModificarOferta).subscribe({
      next: datos => {
        this.messageService.add({ severity: 'success', summary: 'Modificado', detail: 'Has modificado el anuncio correctamente.' });
        this.cargarDatos();
        this.cerrarModal();
      },
      error: error => {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Se ha producido un error.' });
        console.log(error);
      }
    });
  }

  borrarDemandaUsuario(id: number) {
    this.peticion.borrarDemandaPorId(id).subscribe({
      next: datos => {
        this.messageService.add({ severity: 'success', summary: 'Eliminado', detail: 'Has eliminado el anuncio correctamente.' });
        this.cargarDatos();
      },
      error: error => {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Se ha producido un error.' });
        console.log(error);
      }
    });
  }

  abrirModalModificarDemanda(demanda: any) {
    this.datosModificarDemanda = demanda;
    this.peticion.listarActividades().subscribe({
      next: datos => {
        this.actividadesDemanda = datos;
        this.datosModificarDemanda.actividad = datos.filter((item: { id: string, nombre: string; }) => item.nombre.toLowerCase() === demanda.actividad.toLowerCase())[0].id;
      }
    })
    this.modalModificacionOferta.nativeElement.style.display = 'none';
    this.modalModificacionDemanda.nativeElement.style.display = 'flex';
    this.contenedorModificacionDemandaOferta.nativeElement.style.display = 'flex';
  }

  abrirModalModificarOferta(oferta: any) {
    this.datosModificarOferta = oferta;
    this.peticion.listarActividades().subscribe({
      next: datos => {
        this.actividadesOferta = datos;
        this.datosModificarOferta.actividad = datos.filter((item: { id: string, nombre: string; }) => item.nombre.toLowerCase() === oferta.actividad.toLowerCase())[0].id;
      }
    })
    this.modalModificacionDemanda.nativeElement.style.display = 'none';
    this.modalModificacionOferta.nativeElement.style.display = 'flex';
    this.contenedorModificacionDemandaOferta.nativeElement.style.display = 'flex';
  }

  cerrarModal() {
    this.modalModificacionOferta.nativeElement.style.display = 'none';
    this.modalModificacionDemanda.nativeElement.style.display = 'none';
    this.contenedorModificacionDemandaOferta.nativeElement.style.display = 'none';
    this.cargarDatos();
  }


  modificarDemandaUsuario() {
    this.peticion.actualizarDemanda(this.datosModificarDemanda).subscribe({
      next: datos => {
        this.messageService.add({ severity: 'success', summary: 'Modificado', detail: 'Has modificado el anuncio correctamente.' });
        this.cargarDatos();
        this.cerrarModal();
      },
      error: error => {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Se ha producido un error.' });
        console.log(error);
      }
    });
  }

  borrarCuentaUsuario() {
    if (confirm("¿Estás seguro de que quieres borrar tu cuenta?")) {
      let stringId = localStorage.getItem("id") ?? "-1";
      let idUsuario = parseInt(stringId)
      //Primero borrar
      this.peticion.borrarUsuarioPorId(idUsuario).subscribe({
        next: datos => {
          this.cargarDatos();
          this.cerrarModal();
        },
        error: error => console.log(error)
      });
      //Segundo deslogear
      this.logout();
    }
  }

  addPreferencia(e: any) {
    let idActividad = parseInt(e.target.value);
    let actividad: any = this.actividades.filter(item => item.id === idActividad);

    let nuevaPreferencia: any = {
      id: e.target.value,
      nombre: actividad[0].nombre,
      operacion: "add"
    }
    this.peticion.actualizarPreferenciasConsumidor(this.idUsuario, nuevaPreferencia).subscribe({
      next: datos => {
        this.messageService.add({ severity: 'success', summary: 'Añadida', detail: 'Preferencia añadida correctamente' });
        this.cargarDatos();
      },
      error: error => {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Se ha producido un error' });
        console.log(error);
      }
    });
  }

  addCualificacion(e: any) {
    let idActividad = parseInt(e.target.value);
    let actividad: any = this.actividades.filter(item => item.id === idActividad);

    let nuevaCualificacion: any = {
      id: e.target.value,
      nombre: actividad[0].nombre,
      operacion: "add"
    }
    this.peticion.actualizarCualificacionConsumidor(this.idUsuario, nuevaCualificacion).subscribe({
      next: datos => {
        this.messageService.add({ severity: 'success', summary: 'Añadida', detail: 'Cualificación añadida correctamente' });
        this.cargarDatos();
      },
      error: error => {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Se ha producido un error' });
        console.log(error);
      }
    });
    
  }

  delPreferencia(id: number) {
    let actividad: any = this.actividades.filter(item => item.id === id);

    let borrarPreferencia: any = {
      id: id,
      nombre: actividad[0].nombre,
      operacion: "del"
    }
    this.peticion.actualizarPreferenciasConsumidor(this.idUsuario, borrarPreferencia).subscribe({
      next: datos => {
        this.messageService.add({ severity: 'success', summary: 'Eliminada', detail: 'Preferencia eliminada correctamente' });
        this.cargarDatos();
      },
      error: error => {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Se ha producido un error' });
        console.log(error);
      }
    });
  }

  delCualificacion(id: number) {
    let actividad: any = this.actividades.filter(item => item.id === id);

    let borrarCualificacion: any = {
      id: id,
      nombre: actividad[0].nombre,
      operacion: "del"
    }
    console.log(borrarCualificacion);
    this.peticion.actualizarCualificacionConsumidor(this.idUsuario, borrarCualificacion).subscribe({
      next: datos => {
        this.messageService.add({ severity: 'success', summary: 'Eliminada', detail: 'Cualificación eliminada correctamente' });
        this.cargarDatos();
      },
      error: error => {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Se ha producido un error' });
        console.log(error);
      }
    });
  }

  logout() {
    this.peticion.removeToken();
    localStorage.clear();
    this.updateNav.establecerLogin({ login: false, usuario: "" });
    localStorage.setItem('logged', "false");
    this.ruta.navigate([""])
  }

  irPanelAdmin() {
    this.ruta.navigate(["/admin"]);
  }

  verDatosOferta(id: number) {
    this.ruta.navigate(["/datos-oferta/" + id]);
  }

  desapuntarUsuario(idOferta: number) {
    let idUsuario = localStorage.getItem("username") ?? "-1";
    if (confirm("¿Estas seguro de que quieres darte de baja de la actividad?")) {
      this.peticion.desapuntarseUsuarioOferta(idOferta, idUsuario).subscribe({
        next: datos => {
          this.messageService.add({ severity: 'success', summary: 'Dado de baja', detail: 'Te has dado de baja correctamente.' });
          this.cargarDatos();
        },
        error: error => {
          this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Se ha producido un error.' });
          console.log(error);
        }
      });
    }
  }
}
