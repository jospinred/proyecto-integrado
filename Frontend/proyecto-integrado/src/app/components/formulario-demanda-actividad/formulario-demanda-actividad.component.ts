import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { PAjaxService } from '../../services/p-ajax.service';
import { Router } from '@angular/router';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-formulario-demanda-actividad',
  standalone: true,
  imports: [FormsModule, ButtonModule, ToastModule],
  templateUrl: './formulario-demanda-actividad.component.html',
  styleUrl: './formulario-demanda-actividad.component.css'
})
export class FormularioDemandaActividadComponent {
  datosDemanda: any = {
    creadorDemanda: "",
    actividad: "",
    fecha: "",
    localidad: ""
  };
  public actividadesDemanda: any[] = [];
  constructor(private peticion: PAjaxService, private ruta: Router, private messageService: MessageService) {
    if(localStorage.getItem("permisos") !=  "1" && localStorage.getItem("permisos") !=  "4" && localStorage.getItem("permisos") !=  "6" && localStorage.getItem("permisos") !=  "9"){
      this.ruta.navigate([""]);
    }
    peticion.listarActividades().subscribe({
      next: datos => {
        this.actividadesDemanda = datos;
        console.log(this.actividadesDemanda);
        
      }
    })
  }

  crearDemanda(demanda:any){
    this.datosDemanda.creadorDemanda = localStorage.getItem("username");
    this.datosDemanda.actividad = demanda.actividadDemanda;
    this.datosDemanda.fecha = demanda.fechaDemanda;
    this.datosDemanda.localidad = demanda.localidadDemanda;
    console.log(this.datosDemanda);
    this.peticion.crearDemanda(this.datosDemanda).subscribe({
      next: datos => {
        console.log(datos);
        this.messageService.add({ severity: 'success', summary: 'Demanda creada', detail: 'Se ha creado la demanda correctamente' });
        setTimeout(() => {
          this.ruta.navigate([""]);
        }, 2000);
      },
      error: e => {
        console.log(e);
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Se ha producido un error' });
      }
    })
  }
}
