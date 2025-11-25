import { Component } from '@angular/core';
import {GoogleMap} from '@angular/google-maps';

@Component({
  selector: 'app-footer',
  imports: [GoogleMap],
  templateUrl: './footer.html',
  styleUrl: './footer.scss'
})
export class Footer {

  center: google.maps.LatLngLiteral = {lat: 24, lng: 12};
  zoom = 4;
  display!: google.maps.LatLngLiteral;

  moveMap(event: google.maps.MapMouseEvent) {
    this.center = (event.latLng!.toJSON());
  }

  move(event: google.maps.MapMouseEvent) {
    this.display = event.latLng!.toJSON();
  }

}
