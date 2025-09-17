import { ChangeDetectionStrategy, Component, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { provideNativeDateAdapter } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { ReservationService } from '../../services/reservation-service';
import { PopUp } from '../pop-up/pop-up';
import { Reservation } from '../../models/reservation.model';
import { ListCard } from '../list-card/list-card';

@Component({
  selector: 'app-admin-page',
  imports: [MatCardModule, MatDatepickerModule, PopUp, ListCard],
  templateUrl: './admin-page.html',
  styleUrl: './admin-page.scss',
  providers: [provideNativeDateAdapter()],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class AdminPage implements OnInit {
  private reservationService = inject(ReservationService)
  private destroyRef = inject(DestroyRef)

  todaysReservation = signal<Reservation[]>([])
  reservationsOfSelectedDate = signal<Reservation[]>([])

  isShowPupUp = signal<boolean>(false)
  popUpTitle = signal<string>("")
  popUpButtonText = signal<string>("")

  ngOnInit(): void {

  }

  selectObjectList(){

    this.isShowPupUp.set(true)
  }

  //Ez majd az output altal fog ervenyesulni
  changeObjectList(){

  }
}
