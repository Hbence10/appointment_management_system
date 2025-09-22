import { ChangeDetectionStrategy, Component, computed, DestroyRef, inject, OnInit, Signal, signal } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { provideNativeDateAdapter } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { Reservation } from '../../models/reservation.model';
import { PopUp } from '../pop-up/pop-up';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { RouterModule } from '@angular/router';
import { ReservationCard } from '../reservation-card/reservation-card';
import { ReservationService } from '../../services/reservation-service';
import { Details } from '../../models/notEntityModels/details.model';

@Component({
  selector: 'app-admin-page',
  imports: [MatCardModule, MatDatepickerModule, CommonModule, PopUp, MatButtonModule, RouterModule, ReservationCard],
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
  popUpTitle = signal<string>("")
  popUpButtonText = signal<string>("")
  popUpObjectType = signal<string>("")
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
      next: response => {
        this.todaysReservation.set(response)
        this.reservationsOfSelectedDate.set(response)
      }
    })

    this.destroyRef.onDestroy(() => {
      subscription.unsubscribe()
    })
  }

  selectObjectList(title: string, buttonText: string, objectType: "deviceCategory" | "news" | "device" | "gallery" | "reservationType" | "rule" | "other") {
    this.popUpTitle.set(title)
    this.popUpButtonText.set(buttonText)
    this.popUpObjectType.set(objectType)

    this.popUpDetails = new Details(title, buttonText, objectType)

    this.isShowPupUp.set(true)
  }

  closePopUp() {
    this.isShowPupUp.set(false)
  }

  showSelectedDaysReservation(){

  }
}
