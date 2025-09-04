import { Component, DestroyRef, inject, input, OnInit, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { ReservationService } from '../../services/reservation-service';
import { Reservation } from '../../models/reservation.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-reservation-finalize',
  imports: [MatCheckboxModule, MatSlideToggleModule, MatButtonModule],
  templateUrl: './reservation-finalize.html',
  styleUrl: './reservation-finalize.scss'
})

export class ReservationFinalize implements OnInit{
  private reservationService = inject(ReservationService)
  private destroyRef = inject(DestroyRef)
  private router = inject(Router)

  paymentMethods = signal<{ id: number, name: string }[]>([])
  isAddedToGoogleCalendar = signal<boolean>(false)

  ngOnInit(): void {
    const subscription = this.reservationService.getPaymentMethods().subscribe({
      next: response => this.paymentMethods.set(response)
    })

    this.destroyRef.onDestroy(() => {
      subscription.unsubscribe()
    })
  }

  selectPaymentMethod(id:number){
    console.log(id)
  }

  finalizeReservation(){

  }
}
