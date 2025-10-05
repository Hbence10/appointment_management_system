import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, computed, DestroyRef, inject, OnInit, Signal, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { provideNativeDateAdapter } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { RouterModule } from '@angular/router';
import { Details } from '../../models/notEntityModels/details.model';
import { Reservation } from '../../models/reservation.model';
import { ReservationService } from '../../services/reservation-service';
import { PopUp } from '../pop-up/pop-up';
import { ReservationCard } from '../reservation-card/reservation-card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';

@Component({
  selector: 'app-admin-page',
  imports: [MatCardModule, MatDatepickerModule, CommonModule, PopUp, MatButtonModule, RouterModule, ReservationCard, MatFormFieldModule, MatSelectModule],
  templateUrl: './admin-page.html',
  styleUrl: './admin-page.scss',
  providers: [provideNativeDateAdapter()],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class AdminPage implements OnInit {
  private destroyRef = inject(DestroyRef)
  private reservationService = inject(ReservationService)

  todaysReservation = signal<Reservation[]>([])
  reservationsOfSelectedDate = signal<Reservation[]>([])
  selectedReservation = signal<null | Reservation>(null)

  isShowPupUp = signal<boolean>(false)
  popUpDetails!: Details

  //Naptar dolgai:
  currentDate: Date = new Date()
  selectedDate = signal<Date>(this.currentDate);
  maxDate: Date = new Date(this.currentDate.getFullYear(), this.currentDate.getMonth() + 1, this.currentDate.getDate())
  monthsName = signal(["Január", "Február", "Március", "Április", "Május", "Június", "Július", "Augusztus", "Szeptember", "Október", "November", "December"])
  daysName = signal(["Vasárnap", "Hetfő", "Kedd", "Szerda", "Csütörtök", "Péntek", "Szombat"])
  selectedDateText: Signal<String> = computed(() =>
    `${this.monthsName()[this.selectedDate().getMonth()]} ${this.selectedDate().getDate()}, ${this.daysName()[this.selectedDate().getDay()]}`
  )
  formattedSelectedDate = computed(() =>
    `${this.selectedDate().getFullYear()}-${this.selectedDate().getMonth() + 1 < 10 ? '0' : ''}${this.selectedDate().getMonth() + 1}-${this.selectedDate().getDate() < 10 ? '0' : ''}${this.selectedDate().getDate()}`
  )

  ngOnInit(): void {
    const subscription = this.reservationService.getReservationByDate(this.formattedSelectedDate()).subscribe({
      next: responseList => {
        this.todaysReservation.set(this.reservationService.setObject(responseList))
        this.reservationsOfSelectedDate.set(this.reservationService.setObject(responseList))
      }
    })

    this.destroyRef.onDestroy(() => {
      subscription.unsubscribe()
    })
  }

  selectObjectList(title: string, buttonText: "newEntity" | "saveChanges" | "deleteEntity" | "galleryView" | "", objectType: "deviceCategory" | "device" | "news" | "reservationType" | "gallery" | "rule" | "other") {
    this.popUpDetails = new Details(title, buttonText, objectType)
    this.isShowPupUp.set(true)
  }

  closePopUp() {
    this.isShowPupUp.set(false)
  }

  showSelectedDaysReservation() {
    this.reservationService.getReservationByDate(this.formattedSelectedDate()).subscribe({
      next: responseList => this.reservationsOfSelectedDate.set(this.reservationService.setObject(responseList))
    })
  }

  showReservationDetails(wantedReservation: Reservation) {
    console.log(wantedReservation)
    console.log(wantedReservation.getId)

    this.popUpDetails = new Details(`#${wantedReservation.getId} Foglalás`, "cancelReservation", "reservation")
    this.selectedReservation.set(wantedReservation)
    this.isShowPupUp.set(true)
  }
}
