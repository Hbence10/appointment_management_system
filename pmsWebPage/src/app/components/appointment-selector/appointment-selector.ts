import { ChangeDetectionStrategy, Component, computed, DestroyRef, inject, input, OnInit, Signal, signal } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { provideNativeDateAdapter } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatIconModule } from '@angular/material/icon';
import { Router, RouterModule } from '@angular/router';
import { Reservation } from '../../models/reservation.model';
import { ReservedDates } from '../../models/reservedDates.model';
import { ReservedHours } from '../../models/reservedHours.model';
import { User } from '../../models/user.model';
import { ReservationService } from '../../services/reservation-service';
import { UserService } from '../../services/user-service';

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
  private router = inject(Router)

  //Naptar dolgai:
  currentDate: Date = new Date()
  selectedDate = signal<Date>(this.currentDate);
  maxDate: Date = new Date(this.currentDate.getFullYear(), this.currentDate.getMonth() + 1, this.currentDate.getDate())
  monthsName = signal(["Január", "Február", "Március", "Április", "Május", "Június", "Július", "Augusztus", "Szeptember", "Október", "November", "December"])
  daysName = signal(["Vasárnap", "Hetfő", "Kedd", "Szerda", "Csütörtök", "Péntek", "Szombat"])
  selectedDateText: Signal<String> = computed(() =>
    `${this.monthsName()[this.selectedDate().getMonth()]} ${this.selectedDate().getDate()}, ${this.daysName()[this.selectedDate().getDay()]}`
  )

  //Foglalas dolgai:
  baseReservation = input(new Reservation(1, "asdasd"))
  availableHours: number[] = [10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22]
  reservableHours: (number | string)[] = ["Egész nap", 1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
  startHour: number | null = null
  hoursOfSelectedDate = signal<ReservedHours[]>([])
  reservedDatesOfMonth = signal<ReservedDates[]>([])
  user = signal<User | null>(null)
  selectedReservedDate = signal<ReservedDates>(new ReservedDates(1, new Date(), false, false, false))

  ngOnInit(): void {
    this.user.set(this.userService.user())

    const subscription = this.reservationService.getReservedDateByMonth("2025-09-03").subscribe({
      next: response => this.reservedDatesOfMonth.set(response),
      complete: () => {
        this.showSelectedDatesOfHours()
      }
    })

    this.destroyRef.onDestroy(() => {
      subscription.unsubscribe()
    })
  }

  showSelectedDatesOfHours(){
    console.log(this.selectedDate())
  }

  selectReservationAmount(selectedReservableHour: string | number){
    // this.router.navigate(["", this.baseReservation])
  }
}
