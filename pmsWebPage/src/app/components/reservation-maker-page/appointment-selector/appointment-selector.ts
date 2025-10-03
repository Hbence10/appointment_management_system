import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, computed, DestroyRef, inject, OnInit, output, Signal, signal } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { RouterModule } from '@angular/router';
import { Reservation } from '../../../models/reservation.model';
import { ReservedDates } from '../../../models/reservedDates.model';
import { ReservedHours } from '../../../models/reservedHours.model';
import { User } from '../../../models/user.model';
import { ReservationService } from '../../../services/reservation-service';
import { UserService } from '../../../services/user-service';

@Component({
  selector: 'app-appointment-selector',
  imports: [MatCardModule, MatDatepickerModule, RouterModule, CommonModule],
  templateUrl: './appointment-selector.html',
  styleUrl: './appointment-selector.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class AppointmentSelector implements OnInit {
  private reservationService = inject(ReservationService)
  private userService = inject(UserService)
  private destroyRef = inject(DestroyRef)
  nextStep = output()

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

  //Egyeb dolgok:
  listCardAmount: number = 5;
  baseReservation!: Signal<Reservation>

  ngOnInit(): void {
    this.user.set(this.userService.user())

    if (this.user()?.getRole.getName == "ROLE_admin" || this.user()?.getRole.getName == "ROLE_superAdmin") {
      this.listCardAmount = this.reservableHourAmounts.length
    }

    this.baseReservation = signal<Reservation>(this.reservationService.baseReservation())

    try {
      this.selectedHourAmount.set(this.baseReservation()?.getReservedHours.getEnd - this.baseReservation()?.getReservedHours.getStart)
    } catch (error) { }

    try {
      this.selectedDate.set(new Date(this.baseReservation().getReservedHours.getDate.getDate))
      this.setCheckerList()
    } catch (error) { }

    const subscription = this.reservationService.getReservedDatesOfActualMonth(this.formattedSelectedDate(), this.maxDate.toISOString().split("T")[0]).subscribe({
      next: responseList => {
        responseList.forEach(response => {
          let reservedDate = Object.assign(new ReservedDates(), response)
          const hourList: ReservedHours[] = []
          reservedDate.getReservedHours.forEach(element => {
            hourList.push(Object.assign(new ReservedHours(), element))
          });
          reservedDate.setReservedHours = hourList
          this.reservedDatesOfActualPeriod.update(old => [...old, reservedDate])
        })

      },
      complete: () => {
        this.showSelectedDatesOfHours()
      }
    })

    this.destroyRef.onDestroy(() => {
      subscription.unsubscribe()
    })
  }

  showSelectedDatesOfHours() {
    const selectedReservedDate: ReservedDates | undefined = (this.reservedDatesOfActualPeriod().find(element => this.formattedSelectedDate() == String(element.getDate)))

    if (this.baseReservation().getReservedHours.getDate == undefined) {
      if (!selectedReservedDate) {
        this.baseReservation().getReservedHours.setDate = new ReservedDates(this.selectedDate(), null, false, false, false)
        this.baseReservation().getReservedHours.getDate.setAvailableHours = [10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21]
      } else {
        this.baseReservation().getReservedHours.setDate = selectedReservedDate
        this.baseReservation().getReservedHours.getDate.setUnavailableHours = this.setUnavailableHours()
        this.baseReservation().getReservedHours.getDate.setAvailableHours = this.setAvailableHours()
      }
    }
  }

  resetReservedHour() {
    this.baseReservation().setReservedHours = new ReservedHours()
    this.selectedHourAmount.set(null)
  }

  setUnavailableHours(): number[] {
    const startHours: number[] = this.baseReservation().getReservedHours.getDate.getReservedHours.map(element => element.start)
    const endHours: number[] = this.baseReservation().getReservedHours.getDate.getReservedHours.map(element => element.end)
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
      if (!this.baseReservation().getReservedHours.getDate.getUnavailableHours.includes(i)) {
        availableHours.push(i)
      }
    }

    return availableHours;
  }

  selectHoursAmountOfReservation(wantedHourAmount: number | string) {
    const endHour: number = wantedHourAmount == "Egész napra" ? 22 : Number(this.baseReservation().getReservedHours.getStart) + Number(wantedHourAmount)
    this.baseReservation().getReservedHours.setEnd = endHour

    this.selectedHourAmount.set(
      this.baseReservation().getReservedHours.getEnd - this.baseReservation().getReservedHours.getStart
    )

    this.reservationService.progressBarSteps[1] = true
    this.nextStep.emit()
  }

  setCheckerList() {
    const checkerList: boolean[] = []
    for (let i: number = this.baseReservation().getReservedHours.getStart; i < this.baseReservation().getReservedHours.getStart + this.listCardAmount; i++) {
      let result: boolean = this.baseReservation().getReservedHours.getDate.getUnavailableHours.includes(i)
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
}
