import { Component } from '@angular/core';
import { AppointmentSelector } from '../appointment-selector/appointment-selector';
import { RouterModule, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-reservation-maker-page',
  imports: [AppointmentSelector, RouterModule, RouterOutlet],
  templateUrl: './reservation-maker-page.html',
  styleUrl: './reservation-maker-page.scss'
})
export class ReservationMakerPage {

}
