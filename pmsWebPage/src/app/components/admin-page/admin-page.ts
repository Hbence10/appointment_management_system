import { ChangeDetectionStrategy, Component, inject, OnInit, signal } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { provideNativeDateAdapter } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { ReservationService } from '../../services/reservation-service';
import { PopUp } from '../pop-up/pop-up';

@Component({
  selector: 'app-admin-page',
  imports: [MatCardModule, MatDatepickerModule, PopUp],
  templateUrl: './admin-page.html',
  styleUrl: './admin-page.scss',
  providers: [provideNativeDateAdapter()],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class AdminPage implements OnInit{
  private reservationService = inject(ReservationService)

  isShowPupUp = signal<boolean>(false)

  ngOnInit(): void {

  }
}
