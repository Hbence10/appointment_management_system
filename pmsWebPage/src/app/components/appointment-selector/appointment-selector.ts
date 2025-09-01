import { ChangeDetectionStrategy, Component, DestroyRef, inject, model, OnInit, signal } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { provideNativeDateAdapter } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatIconModule } from '@angular/material/icon';
import { ReservationService } from '../../services/reservation-service';
import { UserService } from '../../services/user-service';
import { RouterModule } from '@angular/router';
import { ReservedDates } from '../../models/reservedDates.model';
import { User } from '../../models/user.model';

@Component({
  selector: 'app-appointment-selector',
  imports: [MatCardModule, MatDatepickerModule, MatIconModule, RouterModule],
  templateUrl: './appointment-selector.html',
  styleUrl: './appointment-selector.scss',
  providers: [provideNativeDateAdapter()],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class AppointmentSelector implements OnInit {
  private reservationService = inject(ReservationService)
  private userService = inject(UserService)
  private destroyRef = inject(DestroyRef)

  //Naptar dolgai:
  currentDate: Date = new Date()
  selectedDate = signal<Date>(this.currentDate);
  maxDate: Date = new Date(this.currentDate.getFullYear(), this.currentDate.getMonth() + 1, this.currentDate.getDate())

  //Foglalas dolgai:
  availableHours: number[] = [10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22]
  reservableHours: (number | string)[] = ["Egész nap", 1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
  startHour: number | null = null
  reservedDatesOfMonth = signal<ReservedDates[]>([])
  user = signal<User | null>(null)

  ngOnInit(): void {
    this.user.set(this.userService.user())

    const subscription = this.reservationService.getReservedDateByMonth("2025-09-01").subscribe({
      next: response => this.reservedDatesOfMonth.set(response)
    })

    this.destroyRef.onDestroy(() => {
      subscription.unsubscribe()
    })
  }

}
