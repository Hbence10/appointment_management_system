import { Component, computed, input, OnInit, output, signal } from '@angular/core';
import { Reservation } from '../../models/reservation.model';
import { ReservationCard } from '../reservation-card/reservation-card';
import { MatButton } from '@angular/material/button';
import { Details } from '../../models/notEntityModels/details.model';
import { PopUp } from '../pop-up/pop-up';

@Component({
  selector: 'app-reservation-pop-up',
  imports: [ReservationCard, MatButton, PopUp],
  templateUrl: './reservation-pop-up.html',
  styleUrl: './reservation-pop-up.scss'
})
export class ReservationPopUp implements OnInit{
  reservationListInput = input.required<Reservation[]>()
  eventType = input.required<'close' | 'reservation'>()
  popUpDetails!: Details
  selectedReservation = signal<null | Reservation>(null)
  isShowPupUp = signal<boolean>(false)

  confirmReservations = output()
  confirmClose = output()
  closePopUp = output()
  reservationList = signal<Reservation[]>([])
  buttonDisable = computed<boolean>(() => {
    return true
  })


  ngOnInit(): void {
    this.reservationList.set(this.reservationListInput())
  }

  //ameddig az osszes foglalas nincs lemondva az adott admin altal, addig a gomb disable lesz, ha az osszes foglalas le van mondva, csak azutan tudja az admin megcsinalni az adott eventet
  confirmEvent(){
    if (this.eventType() == 'close'){
      this.confirmClose.emit()
    } else if (this.eventType() == 'reservation'){
      this.confirmReservations.emit()
    }
  }

  close(){
    this.closePopUp.emit()
  }

  showReservationDetails(wantedReservation: Reservation) {
      console.log(wantedReservation)
      console.log(wantedReservation.getId)

      this.popUpDetails = new Details(`#${wantedReservation.getId} Foglalás`, "cancelReservation", "reservation")
      this.selectedReservation.set(wantedReservation)
      this.isShowPupUp.set(true)
    }
}
