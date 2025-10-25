import { Component, input } from '@angular/core';
import { Reservation } from '../../models/reservation.model';

@Component({
  selector: 'app-reservation-pop-up',
  imports: [],
  templateUrl: './reservation-pop-up.html',
  styleUrl: './reservation-pop-up.scss'
})
export class ReservationPopUp {
  reservationList = input.required<Reservation[]>()
}
