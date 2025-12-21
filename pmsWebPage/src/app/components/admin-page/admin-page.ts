
import { ChangeDetectionStrategy, Component, computed, DestroyRef, inject, OnInit, Signal, signal } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { provideNativeDateAdapter } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { RouterModule } from '@angular/router';
import { Details } from '../../models/notEntityModels/details.model';
import { Reservation } from '../../models/reservation.model';
import { ReservationService } from '../../services/reservation-service';
import { HistoryPopup } from '../history-popup/history-popup';
import { PopUp } from '../pop-up/pop-up';
import { ReservationCard } from '../reservation-card/reservation-card';
import { RoomControlPanel } from './room-control-panel/room-control-panel';
import { UserService } from '../../services/user-service';
import { Users } from '../../models/user.model';
import { DetailsEditor } from './details-editor/details-editor';
import { Carousel } from '../gallery/carousel/carousel';
import { Gallery } from '../../models/galleryImage.model';
import { GalleryService } from '../../services/gallery-service';

@Component({
  selector: 'app-admin-page',
  imports: [Carousel ,HistoryPopup, DetailsEditor , RoomControlPanel, MatCardModule, MatDatepickerModule, PopUp, RouterModule, ReservationCard, MatFormFieldModule, MatSelectModule, FormsModule, ReactiveFormsModule],
  templateUrl: './admin-page.html',
  styleUrl: './admin-page.scss',
  providers: [provideNativeDateAdapter()],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class AdminPage implements OnInit {
  private destroyRef = inject(DestroyRef)
  private reservationService = inject(ReservationService)
  private userService = inject(UserService)
  galleryService = inject(GalleryService)

  reservationsOfSelectedDate = signal<Reservation[]>([])
  selectedReservation = signal<null | Reservation>(null)
  user!: Users
  reservationCallError: boolean = false

  //Pop-upok dolgai:
  isShowPupUp = signal<boolean>(false)
  popUpDetails!: Details
  showCloseContainer = signal<boolean>(false)
  showHistoryPopup = signal<boolean>(false)
  showDetailsEditor = signal<boolean>(false)
  showGalleryCarousel = signal<boolean>(false)

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
    this.user = this.userService.user()!
    this.reservationCallError = false
    const subscription = this.reservationService.getReservationByDate(this.formattedSelectedDate()).subscribe({
      next: responseList => {
        this.reservationsOfSelectedDate.set(this.reservationService.setReservationObject(responseList))
      },
      error: error => this.reservationCallError = true
    })

    this.destroyRef.onDestroy(() => {
      subscription.unsubscribe()
    })
  }

  selectObjectList(title: string, buttonText: "newEntity" | "saveChanges" | "deleteEntity" | "galleryView" | "", objectType: "deviceCategory" | "device" | "news" | "reservationType" | "gallery" | "rule" | "other" | "user") {
    this.popUpDetails = new Details(title, buttonText, objectType)
    this.isShowPupUp.set(true)
  }

  closePopUp() {
    this.isShowPupUp.set(false)
  }

  showClose() {
    this.showCloseContainer.update(old => !old)
  }

  showSelectedDaysReservation() {
    this.reservationService.getReservationByDate(this.formattedSelectedDate()).subscribe({
      next: responseList => this.reservationsOfSelectedDate.set(this.reservationService.setReservationObject(responseList))
    })
  }

  showReservationDetails(wantedReservation: Reservation) {
    this.popUpDetails = new Details(`#${wantedReservation.getId} Foglalás`, "cancelReservation", "reservation")
    this.selectedReservation.set(wantedReservation)
    this.isShowPupUp.set(true)
  }

  showCarousel(image: Gallery) {
    this.galleryService.selectedImgForCarousel.set(image)
    this.showGalleryCarousel.set(true)
  }

  closeCarousel(){
    this.galleryService.selectedImgForCarousel.set(null)
    this.showGalleryCarousel.set(false)
  }
}
