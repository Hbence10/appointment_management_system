import { ChangeDetectionStrategy, Component, computed, DestroyRef, inject, input, OnInit, Signal, signal, ViewChild, viewChild } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { provideNativeDateAdapter } from '@angular/material/core';
import { MatDatepicker, MatDatepickerModule } from '@angular/material/datepicker';
import { MatIconModule } from '@angular/material/icon';
import { Router, RouterModule } from '@angular/router';
import { Reservation } from '../../models/reservation.model';
import { ReservedDates } from '../../models/reservedDates.model';
import { ReservedHours } from '../../models/reservedHours.model';
import { User } from '../../models/user.model';
import { ReservationService } from '../../services/reservation-service';
import { UserService } from '../../services/user-service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-appointment-selector',
  imports: [MatCardModule, MatDatepickerModule, MatIconModule, RouterModule, MatDatepicker, CommonModule],
  templateUrl: './appointment-selector.html',
  styleUrl: './appointment-selector.scss',
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

  //Inputok:
  baseReservation = input.required<Reservation>()
  ifRegisterWithReservation = input.required<boolean>()

  //Foglalas dolgai:
  availableHours: number[] = [10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22]
  reservableHours: number[] = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
  startHour: number | null = null
  hoursOfSelectedDate = signal<ReservedHours[]>([])
  reservedDatesOfMonth = signal<ReservedDates[]>([])
  user = signal<User | null>(null)
  selectedReservedDate = signal<ReservedDates>(new ReservedDates(1, new Date(), false, false, false))
  selectedHourAmount = signal<number | string | null>(null)

  //Egyeb dolgok:
  listCardAmount: number = 0;

  ngOnInit(): void {
    this.user.set(this.userService.user())
    if(this.user()?.role == "admin" || this.user()?.role == "superAdmin"){
      this.listCardAmount = this.reservableHours.length
    } else if (this.user() == null || this.user()?.role == "user") {
      this.listCardAmount = 5
    }

    const subscription = this.reservationService.getReservedDateByMonth("2025-09-03").subscribe({
      next: response => this.reservedDatesOfMonth.set(response),
      complete: () => {
        console.log(this.selectedDate())
        console.log(this.reservedDatesOfMonth())
      }
    })

    this.destroyRef.onDestroy(() => {
      subscription.unsubscribe()
    })
  }

  showSelectedDatesOfHours(){

  }

  selectReservationAmount(selectedReservableHour: string | number){
    this.selectedHourAmount.set(selectedReservableHour)
    // this.router.navigate(["", this.baseReservation])
  }
}
