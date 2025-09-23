import { Component, inject, input, OnInit, output, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { DeviceCategory } from '../../models/deviceCategory.model';
import { CardItem } from '../../models/notEntityModels/card.model';
import { Details } from '../../models/notEntityModels/details.model';
import { Reservation } from '../../models/reservation.model';
import { DeviceService } from '../../services/device-service';
import { NewsService } from '../../services/news-service';
import { OtherService } from '../../services/other-service';
import { ReservationService } from '../../services/reservation-service';
import { ListCard } from '../list-card/list-card';
import { ObjectEditor } from '../admin-page/object-editor/object-editor';
import { ReservationDetail } from '../reservation-detail/reservation-detail';
import { RuleEditor } from '../admin-page/rule-editor/rule-editor';
import { FormGroup } from '@angular/forms';
import { NewsDetails } from '../../models/newsDetails.model';
import { ReservationType } from '../../models/reservationType.model';
import { GalleryImage } from '../../models/galleryImage.model';
import { Device } from '../../models/device.model';


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
  selectedObject: any = null

  actualPage: "listPage" | "editPage" | "deletePage" = "listPage"

  private deviceService = inject(DeviceService)
  private newsService = inject(NewsService)
  private reservationService = inject(ReservationService)
  private otherService = inject(OtherService)

  ngOnInit() {
    this.actualDetails = new Details(this.baseDetails().title, this.baseDetails().buttonText, this.baseDetails().buttonText)

    if (this.baseDetails().objectType == 'deviceCategory') {
      this.deviceService.getAllDevicesByCategories().subscribe({
        next: response => {
          response.forEach(element => this.cardList.update(old => [...old, new CardItem(element.name, "deviceCategory", new DeviceCategory(element.id, element.name, element.devicesList), "delete")]))
        }
      })
    }
    else if (this.baseDetails().objectType == "news") {
      this.newsService.getAllNews().subscribe({
        next: response => {
          response.forEach(element => this.cardList.update(old => [...old, new CardItem(element.title, "news", new NewsDetails(element.id, element.title, element.text, element.bannerImgPath, element.placement, element.createdAt), "delete")]))
        }
      })
    } else if (this.baseDetails().objectType == "reservationType") {
      this.reservationService.getReservationTypes().subscribe({
        next: response => {
          response.forEach(element => this.cardList.update(old => [...old, new CardItem(element.name, "reservationType", new ReservationType(element.id, element.name, element.price), "delete")]))
        }
      })
    } else if (this.baseDetails().objectType == "gallery") {
      this.otherService.getAllGalleryImages().subscribe({
        next: response => {
          response.forEach(element => this.cardList.update(old => [...old, new CardItem(element.photoName, "gallery", new GalleryImage(element.id, element.photoName, element.photoPath, element.placement), "viewImage")]))
        }
      })
    }
  }

  close() {
    this.closePopUp.emit()
  }

  buttonEvent() {
    console.log("buttonEvent()")
  }

  showDevices(deviceCategory: DeviceCategory) {
    this.cardList.set([])
    this.actualDetails = new Details(deviceCategory.name, "Eszköz hozzáadása", "device", deviceCategory.name)

    deviceCategory.devicesList.forEach(element => {
      this.cardList.update(old => [...old, new CardItem(element.name, "device", new Device(element.id, element.name, element.amount), "delete")])
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
    } else if (this.actualPage == "editPage" && this.actualDetails.objectType == "device") {
      this.actualDetails = new Details("", "Eszköz hozzáadása", "device")
      this.actualPage = "listPage"
    } else if (this.actualPage == "editPage") {
      this.actualDetails = this.baseDetails()
      this.actualPage = "listPage"
    } else if (this.actualPage == "deletePage") {
      this.actualPage = "listPage"
    }
  }

  edit(wantedObject: CardItem) {
    let deviceCategoryName: string | undefined = this.actualDetails.deviceCategory

    this.selectedObject = wantedObject.object
    this.actualDetails = new Details(wantedObject.name, "Mentés", wantedObject.objectType, deviceCategoryName)
    this.actualPage = "editPage"
  }

  delete(wantedObject: CardItem) {

    this.actualDetails = new Details("", "Törlés", wantedObject.objectType)

    this.actualPage = "deletePage"
  }
}

