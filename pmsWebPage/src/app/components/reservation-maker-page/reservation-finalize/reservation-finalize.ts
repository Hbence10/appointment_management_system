import { Component, DestroyRef, inject, OnInit, Signal, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { Router } from '@angular/router';
import { PaymentMethod } from '../../../models/paymentMethod.model';
import { Reservation } from '../../../models/reservation.model';
import { ReservationService } from '../../../services/reservation-service';

@Component({
  selector: 'app-reservation-finalize',
  imports: [MatCheckboxModule, MatSlideToggleModule, MatButtonModule],
  templateUrl: './reservation-finalize.html',
  styleUrl: './reservation-finalize.scss'
})

export class ReservationFinalize implements OnInit{
  private reservationService = inject(ReservationService)
  private destroyRef = inject(DestroyRef)

  paymentMethods = signal<PaymentMethod[]>([])
  isAddedToGoogleCalendar = signal<boolean>(false)
  baseReservation!: Signal<Reservation>;
  totalPrice: number = 0
  isReservationFinished = signal<boolean>(false)

  ngOnInit(): void {
    this.baseReservation = signal<Reservation>(this.reservationService.baseReservation())
    this.totalPrice = this.baseReservation().getReservationTypeId.price! * (this.baseReservation().getReservedHours.getEnd - this.baseReservation().getReservedHours.getStart)

    const subscription = this.reservationService.getPaymentMethods().subscribe({
      next: response => this.paymentMethods.set(response)
    })

    this.destroyRef.onDestroy(() => {
      subscription.unsubscribe()
    })
  }

  selectPaymentMethod(selectedPaymentMethod: PaymentMethod){
    this.baseReservation().setPaymentMethod = selectedPaymentMethod
  }

  finalizeReservation(){
    this.reservationService.baseReservation.set(this.baseReservation())
    this.reservationService.baseReservation().setReservedAt = new Date().toISOString()
    console.log(this.reservationService.baseReservation())

    this.reservationService.makeReservation().subscribe({
      next: response => console.log(response),
      complete: () => this.isReservationFinished.set(true)
    })

    // this.router.navigate([""])
  }
}
