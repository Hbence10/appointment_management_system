import { Component, inject, input, OnInit, output } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { Reservation } from '../../models/reservation.model';
import { ReservationService } from '../../services/reservation-service';

@Component({
  selector: 'app-reservation-card',
  imports: [MatButtonModule],
  templateUrl: './reservation-card.html',
  styleUrl: './reservation-card.scss'
})
export class ReservationCard implements OnInit {
  private reservationService = inject(ReservationService)

  parentComponent = input.required<"profilePage" | "adminPage">()
  reservationDetails = input.required<Reservation>()

  showDetailsOutput = output<Reservation>()
  cancelReservationOutput = output()

  ngOnInit(): void {
    console.log(this.parentComponent())
  }

  cancelReservation() {
    this.cancelReservationOutput.emit()
  }

  showReservationDetails() {
    this.showDetailsOutput.emit(this.reservationDetails())
  }
}
