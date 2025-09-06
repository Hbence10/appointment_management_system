import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, computed, DestroyRef, inject, OnInit, Signal, signal } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatDatepicker, MatDatepickerModule } from '@angular/material/datepicker';
import { MatIconModule } from '@angular/material/icon';
import { Router, RouterModule } from '@angular/router';
import { ReservedDates } from '../../../models/reservedDates.model';
import { ReservedHours } from '../../../models/reservedHours.model';
import { User } from '../../../models/user.model';
import { ReservationService } from '../../../services/reservation-service';
import { UserService } from '../../../services/user-service';

@Component({
  selector: 'app-appointment-selector',
  imports: [MatCardModule, MatDatepickerModule, MatIconModule, RouterModule, CommonModule, MatDatepicker],
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

  //Foglalas dolgai:
  selectedHourAmount = signal<number | null>(null)
  selectedReservedDate = signal<ReservedDates>(new ReservedDates(1, new Date(), false, false, false))
  availableHours: number[] = [10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21]
  reservableHourAmounts: number[] = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
  // startHour = signal<number | null>(null)
  user = signal<User | null>(null)
  reservedDatesOfActualPeriod = signal<ReservedDates[]>([])
  formattedSelectedDate = computed(() =>
    `${this.selectedDate().getFullYear()}-${this.selectedDate().getMonth() + 1 < 10 ? '0' : ''}${this.selectedDate().getMonth() + 1}-${this.selectedDate().getDate() < 10 ? '0' : ''}${this.selectedDate().getDate()}`
  )
  baseReservation = this.reservationService.baseReservation()

  //Egyeb dolgok:
  listCardAmount: number = 0;

  ngOnInit(): void {
    this.user.set(this.userService.user())

    if (this.baseReservation.reservedHours.date != undefined) {
      this.selectedDate.set(new Date(String(this.baseReservation.reservedHours.date.date)))
      this.selectedReservedDate.set(this.baseReservation.reservedHours.date)
      this.selectedHourAmount.set(this.baseReservation.reservedHours.end - this.baseReservation.reservedHours.start!)
    } else {
      console.log("elso alkalom")
    }


    if (this.user()?.role == "admin" || this.user()?.role == "superAdmin") {
      this.listCardAmount = this.reservableHourAmounts.length
    } else if (this.user() == null || this.user()?.role == "user") {
      this.listCardAmount = 5
    }

    const subscription = this.reservationService.getReservedDatesOfActualMonth(this.formattedSelectedDate(), this.maxDate.toISOString().split("T")[0]).subscribe({
      next: response => this.reservedDatesOfActualPeriod.set(response),
      complete: () => this.showSelectedDatesOfHours()
    })

    this.destroyRef.onDestroy(() => {
      subscription.unsubscribe()
    })
  }

  showSelectedDatesOfHours() {
    // this.baseReservation.reservedHours.start = undefined
    const selectedReservedDate: ReservedDates | undefined = (this.reservedDatesOfActualPeriod().find(element => this.formattedSelectedDate() == String(element.date)))

    if (!selectedReservedDate) {
      this.selectedReservedDate.set(new ReservedDates(0, this.selectedDate()))
      console.log(this.selectedReservedDate())
    } else {
      this.selectedReservedDate.set(selectedReservedDate)
    }

    this.setUnavailableHours()
    this.baseReservation.reservedHours.date = this.selectedReservedDate()
  }

  setUnavailableHours() {
    const startHours: number[] = this.selectedReservedDate().reservedHours.map(element => element.start!)
    const endHours: number[] = this.selectedReservedDate().reservedHours.map(element => element.end)
    const unavailableHours: number[] = []

    for (let i: number = 0; i < startHours.length; i++) {
      for (let j = startHours[i]; j <= endHours[i]; j++) {
        unavailableHours.push(j)
      }
    }

    unavailableHours.sort(function (a, b) { return a - b });
    this.selectedReservedDate().unavailableHours = unavailableHours
  }

  setStartHour(){
    this.baseReservation.reservedHours.start = 1
  }

  selectHoursAmountOfReservation(wantedHourAmount: number | string) {
    this.selectedHourAmount.set(wantedHourAmount == "Egész nap" ? 12 : Number(wantedHourAmount))
    const newReservedHour: ReservedHours = new ReservedHours(0, this.baseReservation.reservedHours.start, this.baseReservation.reservedHours.start!+this.selectedHourAmount()!, this.selectedReservedDate())
    this.reservationService.baseReservation().reservedHours = newReservedHour
    this.router.navigate(["makeReservation/reservationForm"])
  }
}
