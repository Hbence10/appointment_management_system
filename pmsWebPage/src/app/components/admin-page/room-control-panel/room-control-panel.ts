import { ChangeDetectionStrategy, Component, computed, inject, OnInit, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatAnchor } from "@angular/material/button";
import { provideNativeDateAdapter } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { Reservation } from '../../../models/reservation.model';
import { ReservedDates } from '../../../models/reservedDates.model';
import { ReservedHours } from '../../../models/reservedHours.model';
import { Users } from '../../../models/user.model';
import { AdminService } from '../../../services/admin-service';
import { ReservationService } from '../../../services/reservation-service';
import { UserService } from '../../../services/user-service';
import { ReservationPopUp } from '../../reservation-pop-up/reservation-pop-up';

@Component({
  selector: 'app-room-control-panel',
  imports: [MatFormFieldModule, MatInputModule, MatDatepickerModule, MatSelectModule, MatInputModule, ReactiveFormsModule, MatAnchor, ReservationPopUp],
  templateUrl: './room-control-panel.html',
  styleUrl: './room-control-panel.scss',
  providers: [provideNativeDateAdapter()],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class RoomControlPanel implements OnInit {
  private adminService = inject(AdminService)
  private userService = inject(UserService)
  private reservationService = inject(ReservationService)
  user!: Users
  showList: boolean[] = [false, false, false, false, false, false]

  dayNames: string[] = ["MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"]
  hunDayNames: string[] = ["Hétfő", "Kedd", "Szerda", "Csütörtök", "Péntek", "Szombat", "Vasárnap"]
  closeTypes: string[] = ["holiday", "full", "other"]
  startHours: number[] = [10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21]
  hours: number[] = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
  startDate = new Date()

  range = new FormGroup({
    start: new FormControl<Date | null>(null),
    end: new FormControl<Date | null>(null),
  });

  selectedDay: "MONDAY" | "TUESDAY" | "WEDNESDAY" | "THURSDAY" | "FRIDAY" | "SATURDAY" | "SUNDAY" | null = null
  selectedCloseType: "holiday" | "full" | "other" | null = null
  selectedDate = new FormControl(new Date());
  selectedHourAmount: number | null = null
  selectedStartHour: number | null = null
  reservationList = signal<Reservation[]>([])
  selectedEventType!: 'close' | 'reservation';
  showPopUp = computed(() => this.reservationList().length > 0)
  methodType!: "single" | "betweenTwoDate" | "betweenTwoDateRepetitive"

  ngOnInit(): void {
    this.user = this.userService.user()!
  }

  manageExpansionPanel(index: number) {
    if (!this.showList[index]) {
      this.showList = [false, false, false, false, false, false]
      this.showList[index] = true
    } else {
      this.showList[index] = false
    }

    this.setValuesToNull()
  }

  setValuesToNull() {
    this.range = new FormGroup({
      start: new FormControl<Date | null>(null),
      end: new FormControl<Date | null>(null),
    });

    this.selectedDay = null
    this.selectedCloseType = null
    this.selectedDate = new FormControl(new Date());
    this.selectedHourAmount = null
    this.selectedStartHour = null
    this.reservationList.set([])
  }

  checkReservationForClose(methodType: "single" | "betweenTwoDate" | "betweenTwoDateRepetitive", dateText: string, startDateText: string, endDateText: string) {
    if (methodType == "single") {
      // this.reservationService.getReservationByDate("").subscribe({
      //   next: response => this.reservationList = this.reservationService.setObject(response),
      //   error: error => console.log(error)
      // })
    } else if (methodType == "betweenTwoDate") {
      // this.reservationService.getReservationBetweenIntervallum("", "").subscribe({
      //   next: response => this.reservationList = this.reservationService.setObject(response),
      //   error: error => console.log(error)
      // })
    } else if (methodType == "betweenTwoDateRepetitive") {
      // this.adminService.repetitiveCloseCheck(startDateText, endDateTextm sele).subscribe({
      //   next: response => this.reservationList = this.reservationService.setObject(response),
      //   error: error => console.log(error)
      // })
    }
  }

  checkReservationForReservation(methodType: "single" | "betweenTwoDate" | "betweenTwoDateRepetitive", startDateText: string, endDateText: string) {
    const dateText: string = this.selectedDate.value!.toISOString().split("T")[0]
    if (methodType == "single") {
      this.adminService.checkReservationForSimple(dateText, this.selectedStartHour!, this.selectedStartHour! + this.selectedHourAmount!).subscribe({
        next: response => {
          this.reservationList.set(this.reservationService.setObject(response))
          console.log(response)
        },
        error: error => console.log(error)
      })
    } else if (methodType == "betweenTwoDate") {
      this.adminService.getReservationsForAdminIntervallum(startDateText, endDateText, this.selectedStartHour!, this.selectedStartHour! + this.selectedHourAmount!).subscribe({
        next: response => this.reservationList.set(this.reservationService.setObject(response)),
        error: error => console.log(error),
        complete: () => console.log(this.reservationList)
      })
    } else if (methodType == "betweenTwoDateRepetitive") {
      this.adminService.checkReservationForRepetitive(startDateText, endDateText, this.selectedDay, this.selectedStartHour!, this.selectedStartHour! + this.selectedHourAmount!).subscribe({
        next: response => this.reservationList.set(this.reservationService.setObject(response)),
        error: error => console.log(error),
        complete: () => console.log(this.reservationList)
      })
    }
  }

  //ZARAS
  closeRoom(closeType: "single" | "betweenTwoDate" | "betweenTwoDateRepetitive") {
    const dateText: string = this.selectedDate.value!.toISOString().split("T")[0]
    const startDateText: string | undefined = this.range.controls["start"].value?.toISOString().split("T")[0]
    const endDateText: string | undefined = this.range.controls["end"].value?.toISOString().split("T")[0]
    this.selectedEventType = 'close'

    this.checkReservationForClose(closeType, dateText, startDateText!, endDateText!)

    if (closeType == 'single') {
      this.closeRoomForADay(dateText)
    } else if (closeType == 'betweenTwoDate') {
      this.closeRoomBetweenPeriod(startDateText!, endDateText!)
    } else if (closeType == 'betweenTwoDateRepetitive') {
      this.closeByRepetitiveDates(startDateText!, endDateText!)
    }
  }

  //ENDPOINTOK
  closeRoomForADay(dateText: string) {
    this.adminService.closeRoomForADay(dateText, this.selectedCloseType!).subscribe({
      next: response => console.log(response),
      error: error => console.log(error),
      complete: () => this.setValuesToNull()
    })
  }

  closeRoomBetweenPeriod(startDateText: string, endDateText: string) {
    this.adminService.closeRoomBetweenPeriod(startDateText, endDateText, this.selectedCloseType!).subscribe({
      next: response => console.log(response),
      error: error => console.log(error),
      complete: () => this.setValuesToNull()
    })
  }

  closeByRepetitiveDates(startDateText: string, endDateText: string) {
    this.adminService.closeByRepetitiveDates(startDateText, endDateText, this.selectedCloseType!, this.selectedDay!).subscribe({
      next: response => console.log(response),
      error: error => console.log(error),
      complete: () => this.setValuesToNull()
    })
  }

  //FOGLALASOK
  makeReservation(reservationType: "single" | "betweenTwoDate" | "betweenTwoDateRepetitive") {
    this.methodType = reservationType
    this.selectedEventType = 'reservation'
    const startDateText: string | undefined = this.range.controls["start"].value?.toISOString().split("T")[0]
    const endDateText: string | undefined = this.range.controls["end"].value?.toISOString().split("T")[0]
    this.checkReservationForReservation(reservationType, startDateText!, endDateText!)

    if (this.reservationList().length == 0) {
      this.sendReservation(reservationType)
    }
  }

  sendReservation(reservationType: "single" | "betweenTwoDate" | "betweenTwoDateRepetitive") {
    if (reservationType == 'single') {
      // this.makeAdminReservation(reservedHour)
    } else if (reservationType == 'betweenTwoDate') {
      // this.makeReservationBetweenPeriod(startDateText!, endDateText!, reservedHour)
    } else if (reservationType == 'betweenTwoDateRepetitive') {
      // this.makeReservationByRepetitiveDates(startDateText!, endDateText!, reservedHour)
    }
  }

  getReservedHoursOfDate() {
    const dateText: string = this.selectedDate.value!.toISOString().split("T")[0]

  }

  //ENDPOINTOK:
  makeAdminReservation(reservedHour: ReservedHours) {
    this.adminService.makeAdminReservation(reservedHour, this.user.getAdminDetails.getId!).subscribe({
      next: response => console.log(response),
      error: error => console.log(error),
      complete: () => this.setValuesToNull()
    })
  }

  makeReservationBetweenPeriod(startDateText: string, endDateText: string, reservedHour: ReservedHours) {
    this.adminService.makeReservationBetweenPeriod(startDateText, endDateText, reservedHour, this.user.getAdminDetails.getId).subscribe({
      next: response => console.log(response),
      error: error => console.log(error),
      complete: () => this.setValuesToNull()
    })
  }

  makeReservationByRepetitiveDates(startDateText: string, endDateText: string, reservedHour: ReservedHours) {
    this.adminService.makeReservationByRepetitiveDates(startDateText, endDateText, this.selectedDay!, reservedHour, this.user.getAdminDetails.getId!).subscribe({
      next: response => console.log(response),
      error: error => console.log(error),
      complete: () => this.setValuesToNull()
    })
  }
}
