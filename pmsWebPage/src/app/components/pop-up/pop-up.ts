import { Component, inject, input, OnInit, output, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { CardItem } from '../../models/card.model';
import { DeviceCategory } from '../../models/deviceCategory.model';
import { DeviceService } from '../../services/device-service';
import { NewsService } from '../../services/news-service';
import { OtherService } from '../../services/other-service';
import { ReservationService } from '../../services/reservation-service';
import { ListCard } from '../list-card/list-card';
import { RuleEditor } from '../rule-editor/rule-editor';
import { ReservationDetail } from '../reservation-detail/reservation-detail';
import { Reservation } from '../../models/reservation.model';
import { MatFormFieldModule } from '@angular/material/form-field';
import { ObjectEditor } from '../object-editor/object-editor';

@Component({
  selector: 'app-pop-up',
  imports: [MatButtonModule, ListCard, RuleEditor, ReservationDetail, MatFormFieldModule, ObjectEditor],
  templateUrl: './pop-up.html',
  styleUrl: './pop-up.scss'
})
export class PopUp implements OnInit {
  title = input.required<string>()
  buttonText = input.required<string>()
  objectType = input.required<string>()
  reservation = input<Reservation>()
  closePopUp = output()
  cardList = signal<CardItem[]>([])

  baseDetails!: { title: string, buttonText: string, objectType: string };
  actualPage: "listPage" | "editPage" = "listPage"

  private deviceService = inject(DeviceService)
  private newsService = inject(NewsService)
  private reservationService = inject(ReservationService)
  private otherService = inject(OtherService)

  ngOnInit() {
    this.baseDetails = { title: this.title(), buttonText: this.buttonText(), objectType: this.objectType()! }

    if (this.objectType()! == 'deviceCategory') {
      this.deviceService.getAllDevicesByCategories().subscribe({
        next: response => {
          response.forEach(element => this.cardList.update(old => [...old, new CardItem(element.name, "deviceCategory", element, "delete")]))
        }
      })
    }
    else if (this.objectType()! == "news") {
      this.newsService.getAllNews().subscribe({
        next: response => {
          response.forEach(element => this.cardList.update(old => [...old, new CardItem(element.title, "news", element, "delete")]))
        }
      })
    } else if (this.objectType()! == "reservationType") {
      this.reservationService.getReservationTypes().subscribe({
        next: response => {
          response.forEach(element => this.cardList.update(old => [...old, new CardItem(element.name, "reservationType", element, "delete")]))
        }
      })
    } else if (this.objectType()! == "gallery") {
      this.otherService.getAllGalleryImages().subscribe({
        next: response => {
          response.forEach(element => this.cardList.update(old => [...old, new CardItem(element.photoName, "gallery", element, "viewImage")]))
        }
      })
    }
  }

  close() {
    this.closePopUp.emit()
  }

  buttonEvent() {
    console.log(this.buttonText())
  }

  showDevices(deviceCategory: DeviceCategory) {
    this.cardList.set([])
    this.baseDetails = { title: deviceCategory.name, buttonText: "Eszköz hozzáadása", objectType: "device" }

    deviceCategory.devicesList.forEach(element => {
      this.cardList.update(old => [...old, new CardItem(element.name, "device", element, "delete")])
    })
  }

  backToListPage() {
    if (this.objectType() == "deviceCategory" && this.baseDetails.objectType == "device" && this.actualPage == "listPage") {
      this.cardList.set([])

      this.deviceService.getAllDevicesByCategories().subscribe({
        next: response => {
          response.forEach(element => this.cardList.update(old => [...old, new CardItem(element.name, "deviceCategory", element, "delete")]))
        },
        complete: () => {
          this.baseDetails = {title: "Kategóriák listája", buttonText: "Kategória hozzáadása", objectType: "deviceCategory"}
        }
      })
    } else {

    }
  }

  edita(wantedObject: any){
    this.actualPage = "editPage"

    console.log(wantedObject)
  }
}
