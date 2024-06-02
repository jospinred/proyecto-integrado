import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class PAjaxService {
  private url: string = "";

  constructor(private http: HttpClient) { }

  registrar(usuario: any) {
    this.url = environment.apiUrlRegistrarUsuarios;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.post<any>(this.url, usuario, httpOptions);
  }

  logear(usuario: any) {
    this.url = environment.apiUrlLogearUsuarios;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.post<any>(this.url, usuario, httpOptions);
  }

  getToken() {
    return localStorage.getItem('jwtToken');
  }

  removeToken() {
    localStorage.removeItem('jwtToken');
  }

  setToken(token: string, username: string) {
    localStorage.setItem('jwtToken', token);
    localStorage.setItem('username', username);
  }

  validarToken(token: string, username: string) {
    this.url = environment.apiUrlValidarToken;
    let cuerpo = {
      token: token,
      username: username
    }
    return this.http.post<any>(this.url, cuerpo);
  }

  listaOfertas() {
    this.url = environment.apiUrlListaOfertas;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.get<any>(this.url, httpOptions);
  }
  listaOfertasUsuario(id:number) {
    this.url = environment.apiUrlListaOfertas+"/"+id;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.get<any>(this.url, httpOptions);
  }

  crearOferta(datosOferta: any){
    console.log(datosOferta);
    this.url = environment.apiUrlCrearOferta;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.post<any>(this.url, datosOferta, httpOptions);
  }

  listarActividades(){
    this.url = environment.apiUrlListaActividades;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.get<any>(this.url, httpOptions);
  }

  listarUsuarios(){
    this.url = environment.apiUrlUsuarioActualizar;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.get<any>(this.url, httpOptions);
  }
  BorrarUsuarioPorUsername(username: string){
    this.url = environment.apiUrlBorrarPorUsername+username;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.delete<any>(this.url, httpOptions);
  }
  
  listarPreferenciasConsumidor(id: number){
    this.url = environment.apiUrlPreferenciasPorId+id;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.get<any>(this.url, httpOptions);
  }
  listarOfertasApuntado(id: number){
    this.url = environment.apiUrlListaOfertasApuntado+id;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.get<any>(this.url, httpOptions);
  }
  

  actualizarPreferenciasConsumidor(id:number, usuarioActualizado: any){
    this.url = environment.apiUrlPreferenciasPorId+id;
    
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.put<any>(this.url, usuarioActualizado, httpOptions);
  }

  actualizarCualificacionConsumidor(id:number, usuarioActualizado: any){
    this.url = environment.apiUrlCualificacionPorId+id;
    
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.put<any>(this.url, usuarioActualizado, httpOptions);
  }
  listarCualificacionesConsumidor(id: number){
    this.url = environment.apiUrlCualificacionPorId+id;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.get<any>(this.url, httpOptions);
  }

  obtenerOfertaPorId(id: number){
    this.url = environment.apiUrlOfertaPorId+id;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.get<any>(this.url, httpOptions);
  }

  obtenerUsuarioPorId(id: number){
    this.url=environment.apiUrlUsuarioPorId+id;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.get<any>(this.url, httpOptions);
  }

  apuntarseUsuarioOferta(idOferta:number,idUsuario:string){
    this.url = environment.apiUrlAddUserOferta+idOferta+"/"+idUsuario;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.post<any>(this.url, httpOptions);
  }

  desapuntarseUsuarioOferta(idOferta:number,idUsuario:string){
    this.url = environment.apiUrlDelUserOferta+idOferta+"/"+idUsuario;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.post<any>(this.url, httpOptions);
  }

  crearDemanda(datosDemanda: any){
    this.url = environment.apiUrlCrearDemanda;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.post<any>(this.url, datosDemanda, httpOptions);
  }

  listaDemandas() {
    this.url = environment.apiUrlListaDemandas;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.get<any>(this.url, httpOptions);
  }
  listaDemandasUsuario(id:number) {
    this.url = environment.apiUrlListaDemandas+"/"+id;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.get<any>(this.url, httpOptions);
  }

  obtenerActividadPorNombre(nombreActividad:string){
    this.url = environment.apiUrlActividadPorNombre+nombreActividad;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.get<any>(this.url, httpOptions);
  }

  borrarDemandaPorId(id:number){
    this.url = environment.apiUrlDemandaPorId+id;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.delete<any>(this.url, httpOptions);
  }

  borrarOfertaPorId(id:number){
    this.url = environment.apiUrlOfertaPorId+id;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.delete<any>(this.url, httpOptions);
  }

  actualizarDemanda(cuerpoDemanda: any){
    this.url = environment.apiUrlCrearDemanda;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.put<any>(this.url, cuerpoDemanda, httpOptions);
  }
  
  actualizarOferta(cuerpoOferta: any){
    this.url = environment.apiUrlCrearOferta;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.put<any>(this.url, cuerpoOferta, httpOptions);
  }
  
  actualizarUsuarioPorId(datos:any){
    this.url = environment.apiUrlUsuarioActualizar;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.put<any>(this.url, datos, httpOptions);
  }

  borrarUsuarioPorId(id:number){
    this.url = environment.apiUrlUsuarioPorId+id;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.delete<any>(this.url, httpOptions);
  }

  listaUsuariosApuntadosOferta(idOferta:number) {
    this.url = environment.apiUrlListaUsuariosApuntadosOferta+idOferta;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.get<any>(this.url, httpOptions);
  }
}
