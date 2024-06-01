import { Component } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { PAjaxService } from '../../services/p-ajax.service';
import { ButtonModule } from 'primeng/button';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';

@Component({
  selector: 'app-datos-oferta',
  standalone: true,
  imports: [ButtonModule, RouterLink, ToastModule],
  templateUrl: './datos-oferta.component.html',
  styleUrl: './datos-oferta.component.css'
})
export class DatosOfertaComponent {
  ofertaCompleta: any;
  actividad: any;
  usuarioId?:any;
  usuarioCreadorOferta: any;
  esConsumidor?: any;
  estaApuntado?: Boolean;
  listaOfertasApuntado:any[] = [];
  logged = localStorage.getItem("logged");
  constructor(private rutaActiva: ActivatedRoute, private peticion: PAjaxService, private ruta: Router, private messageService: MessageService) {
    peticion.obtenerOfertaPorId(this.ofertaId).subscribe({
      next: datos => {
        if(localStorage.getItem("permisos") == "1" || localStorage.getItem("permisos") == "4" ||localStorage.getItem("permisos") == "6" || localStorage.getItem("permisos") == "9"){
          this.esConsumidor = true;
        } else {
          this.esConsumidor = false;
        }
        this.usuarioId = localStorage.getItem("id");
        this.ofertaCompleta = datos;
        this.actividad = this.parseTextToObject(this.ofertaCompleta.actividad);
        this.usuarioCreadorOferta = this.parseTextToObject(this.ofertaCompleta.creadorOferta);
        this.ofertaCompleta.actividad = this.actividad.nombre;
        this.ofertaCompleta.creadorOferta = this.usuarioCreadorOferta.username;
        this.ofertaCompleta.fecha = this.formatDate(this.ofertaCompleta.fecha);


        this.peticion.listarOfertasApuntado(this.usuarioId).subscribe({
          next: datos => {
            let regex = /nombre='(.*?)'/;
            this.listaOfertasApuntado = datos;
            this.listaOfertasApuntado.forEach(ofertaApuntado => {
              let match = ofertaApuntado.actividad.match(regex);
              ofertaApuntado.actividad = match[1];
            });
            this.comprobarOferta();
            
          },
          error: error => console.log(error)
        });
      }
    })
  }

  comprobarOferta() {
    const ofertaExistente = this.listaOfertasApuntado.some(ofertaApuntada => ofertaApuntada.id === this.ofertaCompleta.id);
    this.listaOfertasApuntado.forEach(ofertaApuntada => console.log(ofertaApuntada.id));
    if (ofertaExistente) {
      this.estaApuntado = true;
    } else {
      this.estaApuntado = false;
    }
  }

  formatDate(inputDate: string): string {
    const date = new Date(inputDate);

    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0'); // Los meses comienzan desde 0
    const year = date.getFullYear();

    return `${day}/${month}/${year}`;
  }

  parseTextToObject(texto: string): any {
    // Expresión regular para encontrar las propiedades y sus valores
    const regex = /(\w+)=('([^']*)'|(\d+(\.\d+)?|true|false))/g;
    let match: RegExpExecArray | null;
    const obj: { [key: string]: any } = {};

    while ((match = regex.exec(texto)) !== null) {
      const key = match[1];
      const value = match[3] !== undefined ? match[3] : match[2];

      // Determinar el tipo de valor y convertirlo adecuadamente
      if (value === 'true') {
        obj[key] = true;
      } else if (value === 'false') {
        obj[key] = false;
      } else if (!isNaN(Number(value))) {
        obj[key] = Number(value);
      } else {
        obj[key] = value;
      }
    }

    return obj;
  }

  apuntarseOferta() {    
    if (this.logged && (localStorage.getItem("permisos") == "1" || localStorage.getItem("permisos") == "4" || localStorage.getItem("permisos") == "6" || localStorage.getItem("permisos") == "9")) {
      let idUsuarioString: string | null = localStorage.getItem("username") ?? "";
      this.peticion.apuntarseUsuarioOferta(this.ofertaId, idUsuarioString).subscribe({
        next: datos => {          
          if(datos.Resultado == "OK"){
            this.messageService.add({ severity: 'success', summary: 'Dado de alta', detail: 'Te has dado de alta en la actividad correctamente' });
            setTimeout(() => {
              this.ruta.navigate(["actividades"]);
            }, 2000);
          } else {
            this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Se ha producido un error' });
          }
        }
      })
    }
  }

  private ofertaId = this.rutaActiva.snapshot.params["id"];
}
