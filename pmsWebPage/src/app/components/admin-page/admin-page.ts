import { ChangeDetectionStrategy, Component, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { provideNativeDateAdapter } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { ReservationService } from '../../services/reservation-service';
import { PopUp } from '../pop-up/pop-up';
import { Reservation } from '../../models/reservation.model';
import { ListCard } from '../list-card/list-card';
import { DeviceService } from '../../services/device-service';
import { CardItem } from '../../models/card.model';

@Component({
  selector: 'app-admin-page',
  imports: [MatCardModule, MatDatepickerModule, PopUp, ListCard],
  templateUrl: './admin-page.html',
  styleUrl: './admin-page.scss',
  providers: [provideNativeDateAdapter()],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class AdminPage implements OnInit {
  private destroyRef = inject(DestroyRef)
  private reservationService = inject(ReservationService)

  private deviceService = inject(DeviceService)


  todaysReservation = signal<Reservation[]>([])
  reservationsOfSelectedDate = signal<Reservation[]>([])

  isShowPupUp = signal<boolean>(false)
  popUpTitle = signal<string>("")
  popUpButtonText = signal<string>("")

  cardList: CardItem[] = []

  ngOnInit(): void {
  }

  selectObjectList(title: string, buttonText: string, objectType: "deviceCategory" | "news" | "device" | "gallery" | "reservationType" | "rule" | "other") {
    this.popUpTitle.set(title)
    this.popUpButtonText.set(buttonText)

    if (objectType == 'deviceCategory') {
      this.deviceService.getAllDevicesByCategories().subscribe({
        // next: response => this.cardList = response,
        // complete: () => console.log(this.cardList)
      })
    }

    this.isShowPupUp.set(true)
  }

  //Ez majd az output altal fog ervenyesulni
  changeObjectList() {

  }
}
