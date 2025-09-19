import { Component, DestroyRef, inject, input, OnInit, Signal, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { Router } from '@angular/router';
import { ReservationService } from '../../../services/reservation-service';
import { Reservation } from '../../../models/reservation.model';
import { PaymentMethod } from '../../../models/paymentMethod.model';
import { PopUp } from '../../pop-up/pop-up';

@Component({
  selector: 'app-reservation-finalize',
  imports: [MatCheckboxModule, MatSlideToggleModule, MatButtonModule, PopUp],
  templateUrl: './reservation-finalize.html',
  styleUrl: './reservation-finalize.scss'
})

export class ReservationFinalize implements OnInit{
  private reservationService = inject(ReservationService)
  private destroyRef = inject(DestroyRef)
  private router = inject(Router)

  paymentMethods = signal<PaymentMethod[]>([])
  isAddedToGoogleCalendar = signal<boolean>(false)
  baseReservation!: Signal<Reservation>;
  totalPrice: number = 0
  isReservationFinished = signal<boolean>(false)

  ngOnInit(): void {
    this.baseReservation = signal<Reservation>(this.reservationService.baseReservation())
    this.totalPrice = this.baseReservation().reservationTypeId.price * (this.baseReservation().reservedHours.end - this.baseReservation().reservedHours.start)

    const subscription = this.reservationService.getPaymentMethods().subscribe({
      next: response => this.paymentMethods.set(response)
    })

    this.destroyRef.onDestroy(() => {
      subscription.unsubscribe()
    })
  }

  selectPaymentMethod(selectedPaymentMethod: PaymentMethod){
    this.baseReservation().paymentMethod = selectedPaymentMethod
  }

  finalizeReservation(){
    this.reservationService.baseReservation().reservedAt = new Date().toISOString()
    console.log(this.baseReservation())

    this.reservationService.makeReservation().subscribe({
      next: response => console.log(response),
      complete: () => this.isReservationFinished.set(true)
    })

    // this.router.navigate([""])
  }
}
