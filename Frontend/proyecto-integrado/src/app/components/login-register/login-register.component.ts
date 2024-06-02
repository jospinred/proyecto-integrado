import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Usuario } from '../../models/Usuario';
import { FloatLabelModule } from 'primeng/floatlabel';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { ButtonModule } from 'primeng/button';
import { PAjaxService } from '../../services/p-ajax.service';
import { SelectButtonModule } from 'primeng/selectbutton';
import { Router } from '@angular/router';
import { UpdateNavService } from '../../services/update-nav.service';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';

@Component({
  selector: 'app-login-register',
  standalone: true,
  imports: [FormsModule, FloatLabelModule, IconFieldModule, InputIconModule, ButtonModule, SelectButtonModule, ToastModule],
  templateUrl: './login-register.component.html',
  styleUrl: './login-register.component.css'
})

export class LoginRegisterComponent {
  constructor(private peticion: PAjaxService, private ruta: Router, private updateLogin: UpdateNavService, private messageService: MessageService) {
    if(localStorage.getItem("logged") ==  "true"){
      this.ruta.navigate([""]);
    }
  }

  public usuarioLogin: any = {
    username: "",
    password: ""
  }

  public usuarioRegister: any = {
    username: "",
    email: "",
    password: "",
    nombre: "",
    apellidos: "",
    fechaNacimiento: "",
    sexo: "",
    telefono: "",
    rol: ""
  }
  sexoOpciones: any[] = [
    { label: 'Masculino', value: "M" },
    { label: 'Femenino', value: "F" }
  ];
  rolOpciones: any[] = [
    { label: 'Consumidor', value: "consumidor" },
    { label: 'Ofertante', value: "ofertante" }
  ];

  toggleForm = () => {
    const container = document.querySelector('.container');
    console.log(container);
    container?.classList.toggle('active');
  };

  logearUsuario(usuario: any) {
    console.log(this.usuarioLogin);
    this.messageService.add({ severity: 'info', summary: 'Iniciando sesión', detail: 'Se está iniciando sesión con las credenciales.' });
    this.peticion.logear(this.usuarioLogin).subscribe({
      next: datos => {
        localStorage.setItem('logged', "true");
        localStorage.setItem('permisos', datos.permisos);
        localStorage.setItem("username", datos.username);
        localStorage.setItem('id', datos.id);
        this.peticion.setToken(datos.token, datos.username);
        this.updateLogin.establecerLogin({ login: true, username: datos.username })
        this.messageService.add({ severity: 'success', summary: 'Sesión iniciada', detail: 'Se ha iniciado sesión correctamente.' });
        setTimeout(() => {
          this.ruta.navigate([""]);
        }, 2000);
      },
      error: err => {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'El usuario no existe.' });
      }
    })
  }

  registrarUsuario(usuario: any) {
    this.peticion.registrar(this.usuarioRegister).subscribe(datos => {
      this.messageService.add({ severity: 'success', summary: 'Usuario registrado', detail: 'Se ha registrado al usuario correctamente.' });
      setTimeout(() => {
        this.ruta.navigate([""]);
      }, 2000);
    })
  }

}
