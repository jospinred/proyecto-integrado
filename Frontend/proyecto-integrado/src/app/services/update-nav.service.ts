import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UpdateNavService {
  private resLogin$: Subject<any> = new Subject<any>();

  constructor() {
    this.obtenerResLogin(); // Llamar a ObtenerResLogin al inicio para inicializar el estado
  }

  obtenerResLogin(): Observable<any> {
    if (localStorage.getItem("jwtToken") && localStorage.getItem("username") && localStorage.getItem("username") !== "") {
      const resLogin = { login: true, usuario: localStorage.getItem('username'), id: localStorage.getItem("id") };
      this.establecerLogin(resLogin);
    }
    return this.resLogin$.asObservable();
  }

  establecerLogin(res: Object) {
    this.resLogin$.next(res);
  }
}