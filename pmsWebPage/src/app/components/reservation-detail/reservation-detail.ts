import { Component, inject, input } from '@angular/core';
import { Reservation } from '../../models/reservation.model';
import { ReservationService } from '../../services/reservation-service';

@Component({
  selector: 'app-reservation-detail',
  imports: [],
  templateUrl: './reservation-detail.html',
  styleUrl: './reservation-detail.scss'
})
export class ReservationDetail {
  reservation = input.required<Reservation>()
}
