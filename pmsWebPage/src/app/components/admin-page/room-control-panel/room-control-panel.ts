import { ChangeDetectionStrategy, Component, inject, OnInit } from '@angular/core';
import { ReservationService } from '../../../services/reservation-service';
import { UserService } from '../../../services/user-service';
import { Users } from '../../../models/user.model';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { provideNativeDateAdapter } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-room-control-panel',
  imports: [MatFormFieldModule, MatInputModule, MatDatepickerModule, MatSelectModule, MatInputModule, ReactiveFormsModule],
  templateUrl: './room-control-panel.html',
  styleUrl: './room-control-panel.scss',
  providers: [provideNativeDateAdapter()],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class RoomControlPanel implements OnInit{
  private reservationService = inject(ReservationService)
  private userService = inject(UserService)
  form: FormGroup = new FormGroup({});

  showList: boolean[] = [false, false, false, false, false, false]

  private user!: Users


  ngOnInit(): void {
    this.user = this.userService.user()!
  }

  makeReservation(){
  }


}
