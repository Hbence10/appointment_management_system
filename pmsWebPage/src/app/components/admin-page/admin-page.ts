import { ChangeDetectionStrategy, Component, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { provideNativeDateAdapter } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { ReservationService } from '../../services/reservation-service';
import { PopUp } from '../pop-up/pop-up';
import { Reservation } from '../../models/reservation.model';

@Component({
  selector: 'app-admin-page',
  imports: [MatCardModule, MatDatepickerModule, PopUp],
  templateUrl: './admin-page.html',
  styleUrl: './admin-page.scss',
  providers: [provideNativeDateAdapter()],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class AdminPage implements OnInit{
  private reservationService = inject(ReservationService)
  private destroyRef = inject(DestroyRef)

  todaysReservation = signal<Reservation[]>([])
  isShowPupUp = signal<boolean>(false)

  ngOnInit(): void {
    const subscription = this.reservationService.getReservationByDate("2025-08-28").subscribe({
      next: response => console.log(response)
    })

    this.destroyRef.onDestroy(() => {
      subscription.unsubscribe()
    })
  }
}
