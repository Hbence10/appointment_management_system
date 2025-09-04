import { Component, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { ReservationService } from '../../services/reservation-service';
import { ReservationType } from '../../models/reservationType.model';

@Component({
  selector: 'app-price-list',
  imports: [],
  templateUrl: './price-list.html',
  styleUrl: './price-list.scss'
})
export class PriceList implements OnInit{
  private reservationService = inject(ReservationService)
  private destroyRef = inject(DestroyRef)

  reservationTypes = signal<ReservationType[]>([])

  ngOnInit(): void {
    const subscription = this.reservationService.getReservationTypes().subscribe({
      next: response => this.reservationTypes.set(response),
      complete: () => this.splitTheList()
    })

    this.destroyRef.onDestroy(() => {
      subscription.unsubscribe()
    })
  }

  splitTheList(){

  }
}
