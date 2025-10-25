import { Component, input, output } from '@angular/core';
import { Reservation } from '../../models/reservation.model';
import { ReservationCard } from '../reservation-card/reservation-card';
import { MatButton } from '@angular/material/button';

@Component({
  selector: 'app-reservation-pop-up',
  imports: [ReservationCard, MatButton],
  templateUrl: './reservation-pop-up.html',
  styleUrl: './reservation-pop-up.scss'
})
export class ReservationPopUp {
  reservationList = input.required<Reservation[]>()
  eventType = input.required<'close' | 'reservation'>()

  eventConfirm = output()
  closePopUp = output()

  confirmEvent(){
    this.eventConfirm.emit()
  }

  close(){

  }
}
