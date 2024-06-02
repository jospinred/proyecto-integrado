import { Component } from '@angular/core';
import { TableModule } from 'primeng/table';
import { PAjaxService } from '../../services/p-ajax.service';
import { ButtonModule } from 'primeng/button';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-panel',
  standalone: true,
  imports: [TableModule, ButtonModule, ToastModule],
  templateUrl: './admin-panel.component.html',
  styleUrl: './admin-panel.component.css'
})
export class AdminPanelComponent {
  usuarios: any []=[];

  constructor(private peticion: PAjaxService, private messageService: MessageService, private ruta: Router){
    this.cargarUsuarios();
    if(localStorage.getItem("permisos")!= "5" && localStorage.getItem("permisos")!= "6" && localStorage.getItem("permisos")!= "8" && localStorage.getItem("permisos")!= "9" ){
      this.ruta.navigate([""]);
    }
  }

  borrarUsuario(username:string){
    if(confirm("Estas seguro de que quieres borrar a "+username+"?")){
      this.peticion.BorrarUsuarioPorUsername(username).subscribe({
        next: datos => {
          this.messageService.add({ severity: 'success', summary: 'Usuario elminado', detail: 'Se ha eliminado al usuario correctamente.' });
          this.cargarUsuarios();
        },
        error: error => {
          this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Se ha producido un error.' });
        }
      })
    }
  }

  cargarUsuarios(){
    this.peticion.listarUsuarios().subscribe({
      next: datos => {
        this.usuarios = datos;
        this.usuarios = this.usuarios.filter(usuario => usuario.username !== localStorage.getItem("username"));
      }
    })
  }
}
