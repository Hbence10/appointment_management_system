import { ChangeDetectionStrategy, Component, inject, model, OnInit, signal } from '@angular/core';
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
export class AppointmentSelector implements OnInit{
  private reservationService = inject(ReservationService)
  private userService = inject(UserService)
  private router = inject(Router)

  selected = model<Date | null>(null);
  availableHours: number[] = [10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22]
  showHours = signal<boolean>(false)
  user!: User;


  ngOnInit(): void {
    this.user = this.userService.user()!
  }

  selectDay(){
    this.showHours.update(old => !old)
  }

  selectHour(){

  }
}
