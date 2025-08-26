import { Component, input } from '@angular/core';
import { Reservation } from '../../models/reservation.model';

@Component({
  selector: 'app-reservation-card',
  imports: [],
  templateUrl: './reservation-card.html',
  styleUrl: './reservation-card.scss'
})
export class ReservationCard {
  reservationDetails = input.required<Reservation>()
}
