import { Component } from '@angular/core';
import { PAjaxService } from '../../services/p-ajax.service';
import { DataViewModule } from 'primeng/dataview';
import { TagModule } from 'primeng/tag';
import { ButtonModule } from 'primeng/button';
import { CommonModule } from '@angular/common';
import { DividerModule } from 'primeng/divider';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-lista-ofertas',
  standalone: true,
  imports: [DataViewModule, TagModule, ButtonModule, CommonModule, DividerModule, RouterLink, FormsModule],
  templateUrl: './lista-ofertas.component.html',
  styleUrl: './lista-ofertas.component.css'
})
export class ListaOfertasComponent {
  listaActividades:any[]=[];
  filtro?:any = {
    fecha: "",
    localidad: "",
    actividad: ""
  }
  public actividades: any[] = [];
  listaActividadesFiltrada:any[]=[];

  constructor(private peticion: PAjaxService){
    this.peticion.listaOfertas().subscribe(datos => {
      console.log("Lista de ofertas: ", datos); 
      this.listaActividades = datos;
      this.listaActividadesFiltrada = this.listaActividades;
    })

    peticion.listarActividades().subscribe({
      next: datos => {
        this.actividades = datos;
      }
    })
  }
  
  
  filtroActividades() {
    this.listaActividadesFiltrada = this.listaActividades.filter(oferta =>
      (!this.filtro.actividad || oferta.actividad.includes(this.filtro.actividad)) &&
      (!this.filtro.fecha || oferta.fecha.includes(this.filtro.fecha)) &&
      (!this.filtro.localidad || oferta.localidad.includes(this.filtro.localidad))
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
