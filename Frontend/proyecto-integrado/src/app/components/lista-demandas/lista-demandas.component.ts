import { Component, ElementRef, ViewChild } from '@angular/core';
import { PAjaxService } from '../../services/p-ajax.service';
import { Router, RouterLink } from '@angular/router';
import { DividerModule } from 'primeng/divider';
import { CommonModule } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { TagModule } from 'primeng/tag';
import { DataViewModule } from 'primeng/dataview';
import { FormsModule } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';

@Component({
  selector: 'app-lista-demandas',
  standalone: true,
  imports: [DataViewModule, TagModule, ButtonModule, CommonModule, DividerModule, RouterLink, FormsModule, ToastModule],
  templateUrl: './lista-demandas.component.html',
  styleUrl: './lista-demandas.component.css'
})
export class ListaDemandasComponent {
  listaActividades:any[]=[];
  listaActividadesFiltrada:any[]=[];
  idDemanda?: string;
  public actividades: any[] = [];
  filtro?:any = {
    fecha: "",
    localidad: "",
    actividad: ""
  }
  datosParaOfertar: any = {
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
    minimoPlazas: "",
    preparacionNecesaria: ""
  }

  @ViewChild('modalDemanda') modalDemanda!: ElementRef;
  @ViewChild('modalBackgroundDemanda') modalBackgroundDemanda!: ElementRef;

  constructor(private peticion: PAjaxService, private ruta: Router, private messageService: MessageService){
    this.cargarDatos();
  }

  cargarDatos(){
    this.peticion.listaDemandas().subscribe(datos => {
      console.log("Lista de demandas: ", datos); 
      this.listaActividades = datos;
      this.listaActividadesFiltrada = this.listaActividades;
    })
    this.peticion.listarActividades().subscribe({
      next: datos => {
        this.actividades = datos;
      }
    })
  }

  abrirModalDemanda(datosDemanda: any) {
    console.log(datosDemanda);
    this.idDemanda = datosDemanda.id;
    this.datosParaOfertar.creadorOferta = localStorage.getItem("username");
    this.datosParaOfertar.actividad = datosDemanda.actividad;
    this.datosParaOfertar.fecha = datosDemanda.fecha;
    this.datosParaOfertar.localidad = datosDemanda.localidad;

    this.modalDemanda.nativeElement.style.display = 'flex';
    this.modalBackgroundDemanda.nativeElement.style.display = 'flex';
  }

  cerrarModalDemanda() {
    this.modalDemanda.nativeElement.style.display = 'none';
    this.modalBackgroundDemanda.nativeElement.style.display = 'none';
  }

  crearOfertaRapida(){
    this.peticion.obtenerActividadPorNombre(this.datosParaOfertar.actividad).subscribe({
      next: datos => {
        this.datosParaOfertar.actividad = datos.id
        this.peticion.crearOferta(this.datosParaOfertar).subscribe({
          next: datos => {
            this.messageService.add({ severity: 'success', summary: 'Oferta creada', detail: 'Se ha creado la oferta correctamente' });
            let numId = parseInt(this.idDemanda ?? "-1");
            this.peticion.borrarDemandaPorId(numId).subscribe({
              next: datos => {
                console.log(datos);
                this.cerrarModalDemanda();
                this.cargarDatos();
              }
            })
          }
        })
      }
    })
  }

  filtroActividades() {
    this.listaActividadesFiltrada = this.listaActividades.filter(demanda =>
      (!this.filtro.actividad || demanda.actividad.includes(this.filtro.actividad)) &&
      (!this.filtro.fecha || demanda.fecha.includes(this.filtro.fecha)) &&
      (!this.filtro.localidad || demanda.localidad.includes(this.filtro.localidad))
    );
  }

  borrarFechaInput(){
    this.filtro.fecha = "";
  }

  borrarActividadInput(){
    this.filtro.actividad = "";
  }

  borrarFiltros(){
    this.filtro = {
      actividad: '',
      fecha: '',
      localidad: ''
    };
    this.filtroActividades();
  }
}
