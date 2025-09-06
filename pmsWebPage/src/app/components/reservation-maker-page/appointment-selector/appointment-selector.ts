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
import { Reservation } from '../../../models/reservation.model';

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
  selectedHourAmount = signal<number | string | null>(null)
  availableHours: number[] = [10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21]
  reservableHourAmounts: number[] = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
  reservedDatesOfActualPeriod = signal<ReservedDates[]>([])
  user = signal<User | null>(null)
  formattedSelectedDate = computed(() =>
    `${this.selectedDate().getFullYear()}-${this.selectedDate().getMonth() + 1 < 10 ? '0' : ''}${this.selectedDate().getMonth() + 1}-${this.selectedDate().getDate() < 10 ? '0' : ''}${this.selectedDate().getDate()}`
  )

  checkerList: boolean[] = []

  setCheckerList() {
    const checkerList: boolean[] = []
    for (let i: number = this.baseReservation().reservedHours.start; i < this.baseReservation().reservedHours.start + this.listCardAmount; i++) {
      let result: boolean = this.baseReservation().reservedHours.date.unavailableHours.includes(i)
      if (result) {
        while (checkerList.length != this.listCardAmount) {
          checkerList.push(true)
        }
        break;
      } else {
        checkerList.push(result)
      }

    }

    this.checkerList = checkerList
  }


  //Egyeb dolgok:
  listCardAmount: number = 0;
  baseReservation!: Signal<Reservation>

  ngOnInit(): void {
    this.user.set(this.userService.user())
    if (this.user()?.role == "admin" || this.user()?.role == "superAdmin") {
      this.listCardAmount = this.reservableHourAmounts.length
    } else if (this.user() == null || this.user()?.role == "user") {
      this.listCardAmount = 5
    }

    this.baseReservation = signal<Reservation>(this.reservationService.baseReservation())
    console.log("ngOnInit:", this.baseReservation().toString())


    try {
      this.selectedHourAmount.set(this.baseReservation().reservedHours.end - this.baseReservation().reservedHours.start)
    } catch (error) { }

    try {
      this.selectedDate.set(new Date(this.baseReservation().reservedHours.date.date))
      this.setCheckerList()
    } catch (error) { }

    const subscription = this.reservationService.getReservedDatesOfActualMonth(this.formattedSelectedDate(), this.maxDate.toISOString().split("T")[0]).subscribe({
      next: response => this.reservedDatesOfActualPeriod.set(response),
      complete: () => {
        this.showSelectedDatesOfHours(false)
      }
    })
  }

  showSelectedDatesOfHours(isSelect: boolean) {
    const selectedReservedDate: ReservedDates | undefined = (this.reservedDatesOfActualPeriod().find(element => this.formattedSelectedDate() == String(element.date)))

    if (this.baseReservation().reservedHours.date == undefined) {
      if (!selectedReservedDate) {
        this.baseReservation().reservedHours.date = new ReservedDates(0, this.selectedDate(), false, false, false)
        this.baseReservation().reservedHours.date.availableHours = [10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21]
      } else {
        this.baseReservation().reservedHours.date = selectedReservedDate
        this.baseReservation().reservedHours.date.unavailableHours = this.setUnavailableHours()
        this.baseReservation().reservedHours.date.availableHours = this.setAvailableHours()
      }
    }
  }

  resetReservedHour() {
    this.baseReservation().reservedHours = new ReservedHours()
    this.selectedHourAmount.set(null)
  }

  setUnavailableHours(): number[] {
    const startHours: number[] = this.baseReservation().reservedHours.date.reservedHours.map(element => element.start)
    const endHours: number[] = this.baseReservation().reservedHours.date.reservedHours.map(element => element.end)
    const unavailableHours: number[] = []

    for (let i: number = 0; i < startHours.length; i++) {
      for (let j = startHours[i]; j < endHours[i]; j++) {
        unavailableHours.push(j)
      }
    }

    unavailableHours.sort(function (a, b) { return a - b });

    return unavailableHours
  }

  setAvailableHours(): number[] {
    const availableHours: number[] = []

    for (let i: number = 10; i < 22; i++) {
      if (!this.baseReservation().reservedHours.date.unavailableHours.includes(i)) {
        availableHours.push(i)
      }
    }

    return availableHours;
  }

  selectHoursAmountOfReservation(wantedHourAmount: number | string) {
    const endHour: number = wantedHourAmount == "Egész napra" ? 22 : Number(this.baseReservation().reservedHours.start) + Number(wantedHourAmount)
    this.baseReservation().reservedHours.end = endHour

    this.selectedHourAmount.set(
      this.baseReservation().reservedHours.end - this.baseReservation().reservedHours.start
    )

    this.router.navigate(["/makeReservation/reservationForm"])
  }
}
