import { Component, inject, input, OnInit, output, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { CardItem } from '../../models/notEntityModels/card.model';
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
import { Details } from '../../models/notEntityModels/details.model';


// type details = { title: string, buttonText: string, objectType: string }
@Component({
  selector: 'app-pop-up',
  imports: [MatButtonModule, ListCard, RuleEditor, ReservationDetail, MatFormFieldModule, ObjectEditor],
  templateUrl: './pop-up.html',
  styleUrl: './pop-up.scss'
})
export class PopUp implements OnInit {
  reservation = input<Reservation>()
  closePopUp = output()
  cardList = signal<CardItem[]>([])

  readonly baseDetails = input.required<Details>()
  actualDetails!: Details;

  actualPage: "listPage" | "editPage" | "deletePage" = "listPage"

  private deviceService = inject(DeviceService)
  private newsService = inject(NewsService)
  private reservationService = inject(ReservationService)
  private otherService = inject(OtherService)

  ngOnInit() {
    // this.actualDetails = this.baseDetails()
    this.actualDetails = new Details(this.baseDetails().title, this.baseDetails().buttonText, this.baseDetails().buttonText)

    if (this.baseDetails().objectType == 'deviceCategory') {
      this.deviceService.getAllDevicesByCategories().subscribe({
        next: response => {
          response.forEach(element => this.cardList.update(old => [...old, new CardItem(element.name, "deviceCategory", element, "delete")]))
        }
      })
    }
    else if (this.baseDetails().objectType == "news") {
      this.newsService.getAllNews().subscribe({
        next: response => {
          response.forEach(element => this.cardList.update(old => [...old, new CardItem(element.title, "news", element, "delete")]))
        }
      })
    } else if (this.baseDetails().objectType == "reservationType") {
      this.reservationService.getReservationTypes().subscribe({
        next: response => {
          response.forEach(element => this.cardList.update(old => [...old, new CardItem(element.name, "reservationType", element, "delete")]))
        }
      })
    } else if (this.baseDetails().objectType == "gallery") {
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

  }

  showDevices(deviceCategory: DeviceCategory) {
    this.cardList.set([])
    this.actualDetails = new Details(deviceCategory.name, "Eszköz hozzáadása", "device")

    deviceCategory.devicesList.forEach(element => {
      this.cardList.update(old => [...old, new CardItem(element.name, "device", element, "delete")])
    })
  }

  backToListPage() {
    if (this.baseDetails().objectType == "deviceCategory" && this.actualDetails.objectType == "device" && this.actualPage == "listPage") {
      this.cardList.set([])

      this.deviceService.getAllDevicesByCategories().subscribe({
        next: response => {
          response.forEach(element => this.cardList.update(old => [...old, new CardItem(element.name, "deviceCategory", element, "delete")]))
        },
        complete: () => {
          this.actualDetails = new Details("Kategóriák listája", "Kategória hozzáadása", "deviceCategory")
        }
      })
    } else if (this.actualPage == "editPage"){
      console.log(this.baseDetails())
      this.actualDetails = this.baseDetails()

      this.actualPage = "listPage"
    }
  }

  edit(wantedObject: CardItem) {
    console.log(this.baseDetails())
    this.actualDetails.buttonText = "Mentés"
    this.actualDetails.title = wantedObject.name

    // console.log(this.baseDetails())

    this.actualPage = "editPage"
  }

  setListCards(){

  }

  delete(wantedObject: CardItem){

  }
}
