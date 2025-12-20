import { Component, DestroyRef, inject, OnInit } from '@angular/core';
import { OtherService } from '../../services/other-service';
import { Details } from '../../models/details.model';
import { OpeningDetails } from '../../models/openingDetails.model';
import { CommonModule } from '@angular/common';
import { GoogleMapsModule } from '@angular/google-maps';

@Component({
  selector: 'app-footer',
  imports: [CommonModule, GoogleMapsModule],
  templateUrl: './footer.html',
  styleUrl: './footer.scss'
})
export class Footer implements OnInit {
  otherService = inject(OtherService)
  openingDetails: OpeningDetails[] = []

  //Google Maps:
  center: google.maps.LatLngLiteral = { lat: 40.73061, lng: -73.935242 };
  zoom = 12;
  markers = [
    { lat: 40.73061, lng: -73.935242 },
    { lat: 40.74988, lng: -73.968285 }
  ];

  ngOnInit(): void {
    this.otherService.getDetails().subscribe({
      next: response => {
        this.otherService.detailsForFooter = Object.assign(new Details(), response)
      },
      error: error => console.log(error)
    })

    this.otherService.getOpeningDetails().subscribe({
      next: responseList => {this.otherService.openingDetails = responseList.map(response => Object.assign(new OpeningDetails(), response))},
      error: error => console.log(error)
    })
  }
}
