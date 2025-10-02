import { Component, ElementRef, inject, input, OnInit, output, viewChild } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { Reservation } from '../../models/reservation.model';
import { ReservationService } from '../../services/reservation-service';
import { MatBadgeModule } from '@angular/material/badge';

@Component({
  selector: 'app-reservation-card',
  imports: [MatButtonModule, MatBadgeModule],
  templateUrl: './reservation-card.html',
  styleUrl: './reservation-card.scss'
})
export class ReservationCard implements OnInit{
  private reservationService = inject(ReservationService)
  private elementRef = inject(ElementRef)

  parentComponent = input.required<"profilePage" | "adminPage">()
  reservationDetails = input.required<Reservation>()

  showDetailsOutput = output<Reservation>()
  cancelReservationOutput = output()
  dateText: string = ""

  ngOnInit(): void {

  }

  cancelReservation() {
    this.cancelReservationOutput.emit()
  }

  showReservationDetails() {
    this.showDetailsOutput.emit(this.reservationDetails())
  }
}
