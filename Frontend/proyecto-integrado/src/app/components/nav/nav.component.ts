import { Component, ElementRef, ViewChild } from '@angular/core';
import { MenubarModule } from 'primeng/menubar';
import { PAjaxService } from '../../services/p-ajax.service';
import { UpdateNavService } from '../../services/update-nav.service';
import { Observable } from 'rxjs';
import { MenuItem } from 'primeng/api';
import { Router } from '@angular/router';
import { InputTextModule } from 'primeng/inputtext';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';

@Component({
  selector: 'app-nav',
  standalone: true,
  imports: [MenubarModule, InputTextModule, FormsModule, ButtonModule, ToastModule],
  templateUrl: './nav.component.html',
  styleUrl: './nav.component.css'
})
export class NavComponent {
  public logeado = false;
  public resLogin$!: Observable<any>

  public items: MenuItem[] = []
  id?: string | null;
  otroRol?: string;
  user?: any;
  public userPass?: string;
  @ViewChild('modal') modal!: ElementRef;
  @ViewChild('modalBackground') modalBackground!: ElementRef;
  constructor(private authService: PAjaxService, private updateNav: UpdateNavService, private ruta: Router, private messageService: MessageService) {
    if ((!localStorage["jwtToken"]) || (localStorage["jwtToken"].split(".").length != 3)) {
      this.logeado = false;
      localStorage.setItem('logged', "false");
      this.id = localStorage.getItem("id");
    } else {
      this.authService.validarToken(localStorage["jwtToken"], localStorage["username"]).subscribe({
        next: res => {
          if (res) {
            this.logeado = true;
            this.comprobarOtroRol(localStorage.getItem("permisos"));
          }
        },
        error: error => console.log(error)
      });
      
    }

    this.items = this.items = [
      {
        label: 'Inicio',
        icon: 'pi pi-fw pi-home',
        routerLink: '/'
      },
      {
        label: 'Actividades',
        icon: 'pi pi-fw pi-list',
        routerLink: '/actividades'
      },
      {
        label: 'Demandas',
        icon: 'pi pi-fw pi-list',
        routerLink: '/demandas',
        visible: this.logeado && this.comprobarPermisos("ofertante")
      },
      {
        label: 'Crear actividad',
        icon: 'pi pi-fw pi-plus-circle',
        routerLink: '/crear-oferta-actividad',
        visible: this.logeado && this.comprobarPermisos("ofertante")
      },
      {
        label: 'Demandar actividad',
        icon: 'pi pi-fw pi-plus-circle',
        routerLink: '/crear-demanda-actividad',
        visible: this.logeado && this.comprobarPermisos("consumidor")
      },
      {
        label: 'Login/Register',
        icon: 'pi pi-fw pi-user',
        routerLink: '/login-register',
        visible: !this.logeado
      },
      {
        label: 'Perfil',
        icon: 'pi pi-fw pi-user',
        visible: this.logeado,
        routerLink: '/perfil/' + this.id
      },
      {
        label: 'Registrarse como ' + this.otroRol,
        icon: 'pi pi-fw pi-user',
        visible: this.logeado && this.mostrarRegistroRapido(localStorage.getItem("permisos")),
        command: () => {
          this.abrirRegistroRapido(localStorage.getItem("id"));
        }
      },
      {
        label: 'Cerrar Sesión',
        icon: 'pi pi-fw pi-sign-out',
        visible: this.logeado,
        command: () => {
          this.logout();
        }
      }
    ]

    this.actualizarNav();
  }
  actualizarNav() {
    this.id = localStorage.getItem("id");
    this.comprobarOtroRol(localStorage.getItem("permisos"));
    this.resLogin$ = this.updateNav.obtenerResLogin();    
    this.resLogin$.subscribe({
      next: res => {
        this.logeado = res.login;
        this.items = [
          {
            label: 'Inicio',
            icon: 'pi pi-fw pi-home',
            routerLink: '/'
          },
          {
            label: 'Actividades',
            icon: 'pi pi-fw pi-list',
            routerLink: '/actividades'
          },
          {
            label: 'Demandas',
            icon: 'pi pi-fw pi-list',
            routerLink: '/demandas',
            visible: this.logeado && this.comprobarPermisos("ofertante")
          },
          {
            label: 'Crear actividad',
            icon: 'pi pi-fw pi-plus-circle',
            routerLink: '/crear-oferta-actividad',
            visible: this.logeado && this.comprobarPermisos("ofertante")
          },
          {
            label: 'Demandar actividad',
            icon: 'pi pi-fw pi-plus-circle',
            routerLink: '/crear-demanda-actividad',
            visible: this.logeado && this.comprobarPermisos("consumidor")
          },
          {
            label: 'Login/Register',
            icon: 'pi pi-fw pi-user',
            routerLink: '/login-register',
            visible: !this.logeado
          },
          {
            label: 'Perfil',
            icon: 'pi pi-fw pi-user',
            visible: this.logeado,
            routerLink: '/perfil/' + localStorage.getItem("id")
          },
          {
            label: 'Registrarse con el otro rol',
            icon: 'pi pi-fw pi-user',
            visible: this.logeado && this.mostrarRegistroRapido(localStorage.getItem("permisos")),
            command: () => {
              this.abrirRegistroRapido(localStorage.getItem("id"));
            }
          },
          {
            label: 'Cerrar Sesión',
            icon: 'pi pi-fw pi-sign-out',
            visible: this.logeado,
            command: () => {
              this.logout();
            }
          }
        ]
      }
    })
  }
  mostrarRegistroRapido(permisos: string | null){
    if(permisos == "1" || permisos == "3"){
      return true;
    } else{
      return false;
    }
  }

  comprobarOtroRol(permisos: string | null) {
    if (permisos == "1") this.otroRol = "ofertante";
    else this.otroRol = "consumidor";
    return true;
  }

  abrirRegistroRapido(id: string | null) {
    if (confirm("¿Seguro que quieres registrarte con el otro rol?")) {      
      let userId = id ?? "-1";
      let idNum: number = parseInt(userId);
      console.log(id);

      this.authService.obtenerUsuarioPorId(idNum).subscribe(datos => {
        this.user = datos;
        if (localStorage.getItem("permisos") == "1") this.user.rol = "ofertante";
        else this.user.rol = "consumidor";
        console.log(this.user);
        this.abrirModal();
      })
    }
  }

  registroRapido() {
    this.user.password = this.userPass;
    console.log(this.user);
    this.authService.logear(this.user).subscribe({
      next: datos => {
        this.authService.registrar(this.user).subscribe(datos => {
          this.authService.logear(this.user).subscribe(datos => {
            localStorage.setItem('permisos', datos.permisos);
            this.actualizarNav();
            this.messageService.add({ severity: 'success', summary: 'Registrado', detail: 'Te has registrado con el otro rol correctamente.' });
            setTimeout(() => {
              this.ruta.navigate([""]);
            }, 2000);
          })
        })
      },
      error: err => {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Se ha producido un error.' });
        console.error(err);
      }
    })
    this.cerrarModal();
  }

  abrirModal() {
    this.modal.nativeElement.style.display = 'flex';
    this.modalBackground.nativeElement.style.display = 'flex';
  }

  cerrarModal() {
    this.userPass = "";
    this.modal.nativeElement.style.display = 'none';
    this.modalBackground.nativeElement.style.display = 'none';
  }

  logout() {
    if (confirm("¿Seguro que quieres cerrar sesión?")) {
      this.authService.removeToken();
      localStorage.clear();
      this.updateNav.establecerLogin({ login: false, usuario: "" });
      this.logeado = false;
      localStorage.setItem('logged', "false");
      this.actualizarNav();
      this.ruta.navigate([""])
    }
  }

  ngOnInit() {
    this.actualizarNav();
  }

  comprobarPermisos(rol: string) {
    let permisosPermitidos: (string | null)[] = [];
    let permisos: string | null = localStorage.getItem("permisos");
    if (rol == "ofertante") {
      permisosPermitidos.push("3", "4", "5", "8", "9");
    } else if (rol == "consumidor") {
      permisosPermitidos.push("1", "4", "5", "6", "9");
    } else if (rol == "admin") {
      permisosPermitidos.push("5", "6", "8", "9");
    }

    if (permisosPermitidos.includes(permisos)) {
      return true;
    }
    return false;
  }

  confirmarLogout(e: Event){
    console.log(e);
    
  }
}