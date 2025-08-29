import { ChangeDetectionStrategy, Component, DestroyRef, inject, model, OnInit, signal } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { provideNativeDateAdapter } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatIconModule } from '@angular/material/icon';
import { Router } from '@angular/router';
import { ReservationService } from '../../services/reservation-service';
import { UserService } from '../../services/user-service';
import { User } from '../../models/user.model';

@Component({
  selector: 'app-appointment-selector',
  imports: [MatCardModule, MatDatepickerModule, MatIconModule],
  templateUrl: './appointment-selector.html',
  styleUrl: './appointment-selector.scss',
  providers: [provideNativeDateAdapter()],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class AppointmentSelector implements OnInit {
  private reservationService = inject(ReservationService)
  private userService = inject(UserService)
  private router = inject(Router)
  private destroyRef = inject(DestroyRef)


  selected = model<Date | null>(null);
  availableHours: number[] = [10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22]
  showHours = signal<boolean>(false)
  user!: User;
  actualDate: string = new Date().toJSON().slice(0, 10)


  ngOnInit(): void {
    this.user = this.userService.user()!
    const subscription = this.reservationService.getReservedDateByMonth(this.actualDate).subscribe({
      next: response => console.log(response)
    })

    this.destroyRef.onDestroy(() => {
      console.log("destroyed!!!!! - appointmentSelector Component!")
      subscription.unsubscribe()
    })
  }

  selectDay() {
    this.showHours.update(old => !old)
  }

  selectHour() {
  }
}
