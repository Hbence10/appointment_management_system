import { ChangeDetectionStrategy, Component, inject, OnInit } from '@angular/core';
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
  private user!: Users
  showList: boolean[] = [false, false, false, false, false, false]

  dayNames: string[] = ["MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"]
  hunDayNames: string[] = ["Hétfő", "Kedd", "Szerda", "Csütörtök", "Péntek", "Szombat", "Vasárnap"]
  closeTypes: string[] = ["holiday", "full", "other"]
  startHours: number[] = [10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21]
  hours: number[] = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]

  readonly range = new FormGroup({
    start: new FormControl<Date | null>(null),
    end: new FormControl<Date | null>(null),
  });

  selectedDay: string | null = null
  selectedCloseType: "holiday" | "full" | "other" = "holiday"
  readonly selectedDate = new FormControl(new Date());
  selectedReservedHour: ReservedHours = new ReservedHours()

  ngOnInit(): void {
    this.user = this.userService.user()!
  }

  manageExpansionPanel(index: number){

  }

  //ENDPOINTOK:
  //bezaras
  closeRoomForADay() {
    this.adminService.closeRoomForADay(this.selectedDate.value!, this.selectedCloseType).subscribe({
      next: response => console.log(response)
    })
  }

  closeRoomBetweenPeriod() {
    this.adminService.closeRoomBetweenPeriod(this.range.controls["start"].value!, this.range.controls["end"].value!, this.selectedCloseType)
  }

  closeByRepetitiveDates(){
  }

  //foglalas
  makeAdminReservation() {
  }

  makeReservationByRepetitiveDates() {
  }

  makeReservationAlwaysBetweenTwoDates() {
  }
}
