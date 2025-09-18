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
import { NewsService } from '../../services/news-service';
import { ReservationDetail } from '../reservation-detail/reservation-detail';
import { RuleEditor } from '../rule-editor/rule-editor';

@Component({
  selector: 'app-admin-page',
  imports: [MatCardModule, MatDatepickerModule, PopUp, ListCard, ReservationDetail, RuleEditor],
  templateUrl: './admin-page.html',
  styleUrl: './admin-page.scss',
  providers: [provideNativeDateAdapter()],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class AdminPage implements OnInit {
  private destroyRef = inject(DestroyRef)
  private reservationService = inject(ReservationService)

  private deviceService = inject(DeviceService)
  private newsService = inject(NewsService)

  todaysReservation = signal<Reservation[]>([])
  reservationsOfSelectedDate = signal<Reservation[]>([])
  selectedReservation = signal<null | Reservation>(null)

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
        next: response => {
          response.forEach(element => this.cardList.push(new CardItem(element.name, "deviceCategory", element, "delete")))
        },
        complete: () => this.isShowPupUp.set(true)
      })
    } else if (objectType == "news") {
      this.newsService.getAllNews().subscribe({
        next: response => {
          response.forEach(element => this.cardList.push(new CardItem(element.title, "news", element, "delete")))
        },
        complete: () => this.isShowPupUp.set(true)
      })
    } else if (objectType == "reservationType"){

    }
  }

  closePopUp() {
    this.isShowPupUp.set(false)
    this.cardList = []
  }

  //Ez majd az output altal fog ervenyesulni
  changeObjectList(eventParam: any) {
    console.log("asd")
  }
}
