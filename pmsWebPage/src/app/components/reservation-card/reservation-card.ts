import { Component, inject, input } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { Reservation } from '../../models/reservation.model';
import { ReservationService } from '../../services/reservation-service';

@Component({
  selector: 'app-reservation-card',
  imports: [MatButtonModule],
  templateUrl: './reservation-card.html',
  styleUrl: './reservation-card.scss'
})
export class ReservationCard {
  private reservationService = inject(ReservationService)

  parentComponent = input.required<"profilePage" | "adminPage">()
  reservationDetails = input.required<Reservation>()

  cancelReservation() {
  }

  showReservationDetails() {
  }
}
