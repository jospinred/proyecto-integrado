import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { PAjaxService } from '../../services/p-ajax.service';
import { ButtonModule } from 'primeng/button';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';

@Component({
  selector: 'app-formulario-oferta-actividad',
  standalone: true,
  imports: [FormsModule, ButtonModule, ToastModule],
  templateUrl: './formulario-oferta-actividad.component.html',
  styleUrl: './formulario-oferta-actividad.component.css'
})
export class FormularioOfertaActividadComponent {
  datosOferta: any = {
    creadorOferta: "",
    actividad: "",
    fecha: "",
    localidad: "",
    materialOfertado: "",
    materialNecesario: "",
    tarifa: "",
    descripcion: "",
    duracion: "",
    maximoPlazas: "",
    minimoPlazas: ""
  };
  public actividades: any[] = [];
  constructor(private peticion: PAjaxService, private ruta: Router, private messageService: MessageService) {
    if(localStorage.getItem("permisos") !=  "3" && localStorage.getItem("permisos") !=  "4" && localStorage.getItem("permisos") !=  "8" && localStorage.getItem("permisos") !=  "9"){
      this.ruta.navigate([""]);
    }
    peticion.listarActividades().subscribe({
      next: datos => {
        this.actividades = datos;
      }
    })
  }

  crearOferta(datosOferta: any) {
    if (datosOferta.materialOfertado == undefined) {
      datosOferta.materialOfertado = "";
    }
    if (datosOferta.materialNecesario == undefined) {
      datosOferta.materialNecesario = "";
    }
    datosOferta.creadorOferta = localStorage.getItem("username");
    this.peticion.crearOferta(datosOferta).subscribe({
      next: datos => {
        console.log(datos);
        this.messageService.add({ severity: 'success', summary: 'Oferta creada', detail: 'Se ha creado la oferta correctamente' });
      },
      error: e => {
        console.log(e);
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Se ha producido un error' });
      }
    })
    setTimeout(() => {
      this.ruta.navigate([""]);
    }, 2000);
  }
}
