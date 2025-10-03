import { Component, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { ReservationType } from '../../models/reservationType.model';
import { ReservationService } from '../../services/reservation-service';

@Component({
  selector: 'app-price-list',
  imports: [],
  templateUrl: './price-list.html',
  styleUrl: './price-list.scss'
})
export class PriceList implements OnInit {
  private reservationService = inject(ReservationService)
  private destroyRef = inject(DestroyRef)

  reservationTypes = signal<ReservationType[]>([])
  splittedReservationTypes = signal<ReservationType[][]>([])
  helperList: number[] = [1, 2, 3, 4, 5]

  ngOnInit(): void {
    const subscription = this.reservationService.getReservationTypes().subscribe({
      next: responseList => {
        responseList.forEach(response => {
          this.reservationTypes.update(old => [...old, Object.assign(new ReservationType(), response)])
        })
      },
      complete: () => this.splitTheList()
    })

    this.destroyRef.onDestroy(() => {
      subscription.unsubscribe()
    })
  }

  splitTheList() {
    for (let i: number = 0; i < this.reservationTypes().length; i += 3) {
      this.splittedReservationTypes().push(this.reservationTypes().slice(i, i + 3))
    }

    console.log(this.splittedReservationTypes())
  }

  calculateHourPrice(basePrice: number, hour: number) {
    return basePrice * hour;
  }
}
