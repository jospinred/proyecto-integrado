import { Component } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { PAjaxService } from '../../services/p-ajax.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-usuarios-apuntados-oferta',
  standalone: true,
  imports: [TableModule, ButtonModule],
  templateUrl: './usuarios-apuntados-oferta.component.html',
  styleUrl: './usuarios-apuntados-oferta.component.css'
})
export class UsuariosApuntadosOfertaComponent {
  listaUsuariosApuntados: any[] = [];
  constructor(private rutaActiva: ActivatedRoute, private peticion: PAjaxService, private ruta: Router) {
    this.cargarDatos();
  }

  cargarDatos(){
    this.peticion.listaUsuariosApuntadosOferta(this.rutaActiva.snapshot.params["id"]).subscribe({
      next: datos => {
        this.listaUsuariosApuntados = datos;
      }
    })
  }

  volverAOferta() {
    this.ruta.navigate(["datos-oferta/" + this.rutaActiva.snapshot.params["id"]]);
  }
}
