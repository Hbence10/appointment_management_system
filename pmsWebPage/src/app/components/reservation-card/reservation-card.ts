import { Component, inject, input } from '@angular/core';
import { Reservation } from '../../models/reservation.model';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { ReservationService } from '../../services/reservation-service';

@Component({
  selector: 'app-reservation-card',
  imports: [MatButtonModule, MatIconModule],
  templateUrl: './reservation-card.html',
  styleUrl: './reservation-card.scss'
})
export class ReservationCard {
  private reservationService = inject(ReservationService)
  reservationDetails = input.required<Reservation>()

  cancelReservation() {
  }

  showReservationDetails() {
  }
}
