import { ChangeDetectionStrategy, Component, computed, inject, OnInit, signal } from '@angular/core';
import { ReservationService } from '../../../services/reservation-service';
import { UserService } from '../../../services/user-service';
import { Users } from '../../../models/user.model';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { provideNativeDateAdapter } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { ReservedHours } from '../../../models/reservedHours.model';
import { MatAnchor } from "@angular/material/button";
import { AdminService } from '../../../services/admin-service';
import { ReservedDates } from '../../../models/reservedDates.model';
import { Reservation } from '../../../models/reservation.model';

@Component({
  selector: 'app-room-control-panel',
  imports: [MatFormFieldModule, MatInputModule, MatDatepickerModule, MatSelectModule, MatInputModule, ReactiveFormsModule, MatAnchor],
  templateUrl: './room-control-panel.html',
  styleUrl: './room-control-panel.scss',
  providers: [provideNativeDateAdapter()],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class RoomControlPanel implements OnInit {
  private adminService = inject(AdminService)
  private userService = inject(UserService)
  private reservationService = inject(ReservationService)
  private user!: Users
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
  reservationList: Reservation[] = []

  startDateText = computed<string>(() => {
    const asd = signal(this.range.controls['start'].value!)
    console.log(asd)
    return asd().toString();
  })
  endDateText: string = ''

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

    this.range = new FormGroup({
      start: new FormControl<Date | null>(null),
      end: new FormControl<Date | null>(null),
    });

    this.selectedDay = null
    this.selectedCloseType = null
    this.selectedDate = new FormControl(new Date());
    this.selectedHourAmount = null
    this.selectedStartHour = null
  }

  checkReservation(methodType: "single" | "betweenTwoDate" | "betweenTwoDateRepetitive"){
    if (methodType == 'single'){
      this.reservationService.getReservationByDate('').subscribe({

      })
    } else {
      this.reservationService.getReservationBetweenIntervallum('', '').subscribe({

      })
    }
  }

  //bezaras
  closeRoom(closeType: "single" | "betweenTwoDate" | "betweenTwoDateRepetitive") {

    const dateText: string = this.selectedDate.value!.toISOString().split("T")[0]

    this.checkReservation(closeType)

    // if (closeType == 'single') {
    //   this.closeRoomForADay(dateText)
    // } else if (closeType == 'betweenTwoDate') {
    //   this.closeRoomBetweenPeriod(startDateText!, endDateText!)
    // } else if (closeType == 'betweenTwoDateRepetitive') {
    //   this.closeByRepetitiveDates(startDateText!, endDateText!)
    // }
  }

  //ENDPOINTOK
  closeRoomForADay(dateText: string) {
    this.adminService.closeRoomForADay(dateText, this.selectedCloseType!).subscribe({
      next: response => console.log(response),
      error: error => console.log(error)
    })
  }

  closeRoomBetweenPeriod(startDateText: string, endDateText: string) {
    this.adminService.closeRoomBetweenPeriod(startDateText, endDateText, this.selectedCloseType!).subscribe({
      next: response => console.log(response),
      error: error => console.log(error)
    })
  }

  closeByRepetitiveDates(startDateText: string, endDateText: string) {
    this.adminService.closeByRepetitiveDates(startDateText, endDateText, this.selectedCloseType!, this.selectedDay!).subscribe({
      next: response => console.log(response),
      error: error => console.log(error)
    })
  }

  //foglalas
  makeReservation(reservationType: "single" | "betweenTwoDate" | "betweenTwoDateRepetitive") {
    let reservedHour: ReservedHours = new ReservedHours();
    if (reservationType == 'single') {
      reservedHour = new ReservedHours(null, this.selectedStartHour!, this.selectedStartHour! + this.selectedHourAmount!, new ReservedDates(this.selectedDate.value!, null))
    } else {
      reservedHour = new ReservedHours(null, this.selectedStartHour!, this.selectedStartHour! + this.selectedHourAmount!)
    }

    this.checkReservation(reservationType)

    // if (reservationType == 'single') {
    //   this.makeAdminReservation(reservedHour)
    // } else if (reservationType == 'betweenTwoDate') {
    //   this.makeReservationBetweenPeriod(this.startDateText!, this.endDateText!, reservedHour)
    // } else if (reservationType == 'betweenTwoDateRepetitive') {
    //   this.makeReservationByRepetitiveDates(this.startDateText!, this.endDateText!, reservedHour)
    // }
  }


  //ENDPOINTOK:
  makeAdminReservation(reservedHour: ReservedHours) {
  }

  makeReservationBetweenPeriod(startDateText: string, endDateText: string, reservedHour: ReservedHours) {
  }

  makeReservationByRepetitiveDates(startDateText: string, endDateText: string, reservedHour: ReservedHours) {
  }
}
