import { Component, DestroyRef, inject, OnInit, Signal, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { PaymentMethod } from '../../../models/paymentMethod.model';
import { Reservation } from '../../../models/reservation.model';
import { ReservationService } from '../../../services/reservation-service';
import { ReservationStuff } from '../../../services/reservation-stuff';
import { Router } from '@angular/router';

@Component({
  selector: 'app-reservation-finalize',
  imports: [MatCheckboxModule, MatSlideToggleModule, MatButtonModule],
  templateUrl: './reservation-finalize.html',
  styleUrl: './reservation-finalize.scss'
})

export class ReservationFinalize implements OnInit {
  private reservationService = inject(ReservationService)
  private reservationStuffService = inject(ReservationStuff)
  private destroyRef = inject(DestroyRef)
  private router = inject(Router)

  paymentMethods = signal<PaymentMethod[]>([])
  isAddedToGoogleCalendar = signal<boolean>(false)
  baseReservation!: Signal<Reservation>;
  totalPrice: number = 0
  isReservationFinished = signal<boolean>(false)
  phoneNumber: string = ""

  ngOnInit(): void {
    this.baseReservation = signal<Reservation>(this.reservationService.baseReservation())
    this.totalPrice = this.baseReservation().getReservationTypeId.getPrice! * (this.baseReservation().getReservedHours.getEnd - this.baseReservation().getReservedHours.getStart)

    const subscription = this.reservationStuffService.getPaymentMethods().subscribe({
      next: responseList => {
        responseList.forEach(response => {
          this.paymentMethods.update(old => [...old, Object.assign(new PaymentMethod(), response)])
        })
        console.log(this.paymentMethods())
      },
    })

    this.destroyRef.onDestroy(() => {
      subscription.unsubscribe()
    })

    let basePhoneNumber = this.baseReservation().getPhone
    this.phoneNumber = "+" + this.baseReservation().getPhoneCode?.countryCode + " " + basePhoneNumber.slice(0, 2) + " " + basePhoneNumber.slice(2, 5) + " " + basePhoneNumber.slice(5)
  }

  selectPaymentMethod(selectedPaymentMethod: PaymentMethod) {
    this.baseReservation().setPaymentMethod = selectedPaymentMethod
  }

  finalizeReservation() {
    this.reservationService.baseReservation.set(this.baseReservation())
    this.reservationService.baseReservation().setReservedAt = new Date().toISOString()
    console.log(this.reservationService.baseReservation())

    this.reservationService.makeReservation().subscribe({
      next: response => console.log(response),
      complete: () => {
        this.isReservationFinished.set(true)
        this.router.navigate([""])
      }
    })
  }
}
