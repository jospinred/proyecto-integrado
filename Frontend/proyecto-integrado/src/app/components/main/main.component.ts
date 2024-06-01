import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { DividerModule } from 'primeng/divider';

@Component({
  selector: 'app-main',
  standalone: true,
  imports: [ButtonModule, RouterLink, DividerModule],
  templateUrl: './main.component.html',
  styleUrl: './main.component.css'
})
export class MainComponent {
  noLogeado?: boolean;
  listaNoticias: any[] = [];

  ngOnInit(){    
    if (!localStorage["jwtToken"]) {
      this.noLogeado = true;
    }
  }

  constructor() {
    this.listaNoticias[0] = {
      titulo: "Montar a Caballo en la Sierra de Guadarrama",
      cuerpo: "La Sierra de Guadarrama, situada en la Comunidad de Madrid, ofrece rutas espectaculares para los amantes de la equitación. Los recorridos a caballo permiten explorar paisajes naturales únicos, desde bosques frondosos hasta praderas abiertas. Empresas locales organizan excursiones guiadas aptas tanto para principiantes como para jinetes experimentados, proporcionando una experiencia inigualable de conexión con la naturaleza y la vida silvestre de la región.",
      enlace: "https://www.elperiodicodelturismo.com/rutas/2916-la-ruta-a-caballo-por-el-parque-de-guadarrama-y-el-de-la-pedriza-en-el-boalo-madrid-ha-sido-una-de-las-experiencias-mas-valoradas-de-espana-en-2023-por-los-usuarios-de-aladinia-com"
    }
    this.listaNoticias[1] = {
      titulo: "Escalada en Riglos, Aragón",
      cuerpo: "Los Mallos de Riglos, en Aragón, son uno de los destinos más populares para la escalada en España. Estas impresionantes formaciones rocosas, con alturas que alcanzan los 300 metros, ofrecen rutas para escaladores de todos los niveles. Recientemente, se ha inaugurado un centro de interpretación en Riglos que proporciona información detallada sobre las rutas y la historia geológica de la zona, haciendo de este lugar un destino ideal para los entusiastas de la escalada.",
      enlace: "https://www.diariodelaltoaragon.es/noticias/comarcas/somontano/2024/05/11/riglos-el-flechazo-con-la-montana-y-un-trabajo-que-permite-gozar-1732861-daa.html"
    }
    this.listaNoticias[2] = {
      titulo: "Piragüismo en el Descenso del Sella, Asturias",
      cuerpo: "El famoso Descenso del Sella, en Asturias, es una actividad imprescindible para los amantes del piragüismo. Cada año, miles de personas participan en esta emocionante travesía que recorre el río Sella desde Arriondas hasta Ribadesella. La ruta ofrece vistas espectaculares de los Picos de Europa y la posibilidad de disfrutar de la fauna local. Empresas especializadas ofrecen alquiler de piraguas y servicios de guía para asegurar una experiencia segura y divertida para todos los participantes.",
      enlace: "https://www.elfielato.es/articulo/descenso-del-sella/descenso-del-sella/20230621121422050435.html"
    }
    this.listaNoticias[3] = {
      titulo: "Buceo en la Isla de Tabarca, Alicante",
      cuerpo: "La Isla de Tabarca, situada frente a la costa de Alicante, es un destino de buceo de renombre gracias a sus aguas cristalinas y rica biodiversidad marina. Declarada Reserva Marina, la isla ofrece una experiencia de buceo incomparable, con la oportunidad de explorar arrecifes de coral, cuevas submarinas y una gran variedad de especies marinas. Centros de buceo locales organizan inmersiones para buceadores de todos los niveles, garantizando una experiencia segura y emocionante en este entorno protegido.",
      enlace: "https://www.economiasolidaria.org/noticias/reas-madrid-noticias-buceo-responsable-y-solidario-la-reserva-marina-de-isla-de-tabarca-alicante/"
    }
  }
}
