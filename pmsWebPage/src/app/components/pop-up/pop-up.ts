import { Component, computed, inject, input, OnInit, output, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { Device } from '../../models/device.model';
import { DevicesCategory } from '../../models/deviceCategory.model';
import { GalleryImage } from '../../models/galleryImage.model';
import { NewsDetails } from '../../models/newsDetails.model';
import { CardItem } from '../../models/notEntityModels/card.model';
import { Details } from '../../models/notEntityModels/details.model';
import { Reservation } from '../../models/reservation.model';
import { ReservationType } from '../../models/reservationType.model';
import { DeviceService } from '../../services/device-service';
import { NewsService } from '../../services/news-service';
import { OtherService } from '../../services/other-service';
import { ReservationService } from '../../services/reservation-service';
import { ObjectEditor } from '../admin-page/object-editor/object-editor';
import { RuleEditor } from '../admin-page/rule-editor/rule-editor';
import { ListCard } from '../list-card/list-card';
import { ReservationDetail } from '../reservation-detail/reservation-detail';
import { FormControl, FormGroup, Validators } from '@angular/forms';


@Component({
  selector: 'app-pop-up',
  imports: [MatButtonModule, ListCard, RuleEditor, ReservationDetail, MatFormFieldModule, ObjectEditor],
  templateUrl: './pop-up.html',
  styleUrl: './pop-up.scss'
})
export class PopUp implements OnInit {
  reservation = input.required<Reservation>()
  closePopUp = output()
  cardList = signal<CardItem[]>([])
  form: FormGroup = new FormGroup({
    property1: new FormControl("", [Validators.required]),
    property2: new FormControl("", []),
    property3: new FormControl("", [])
  });

  baseDetails = input.required<Details>()
  actualDetails = signal<Details | null>(null);
  selectedObject: any = null
  editForm!: FormGroup

  buttonText = computed<string>(() => {
    const objectTypes: string[] = ["deviceCategory", "device", "news", "reservationType"]
    const objectText: string[] = ["Eszköz kategória", "Eszköz", "Hír", "Próba kategória"]

    let text = ""
    if (this.actualDetails()?.buttonText == "cancelReservation") {
      text = "Foglalás lemondása"
    } else if (this.actualDetails()?.buttonText == "galleryView") {
      text = "Előnézet"
    } else if (this.actualDetails()?.buttonText == "deleteEntity") {
      text = "Törlés"
    } else if (this.actualDetails()?.buttonText == "saveChanges" || (this.actualDetails()?.buttonText == "newEntity" && this.actualPage == "editPage")) {
      text = "Mentés"
    } else {
      text = `${objectText[objectTypes.indexOf(this.actualDetails()!.objectType)]} hozzáadása`
    }

    return text
  })

  actualPage: "listPage" | "editPage" | "deletePage" = "listPage"

  private deviceService = inject(DeviceService)
  private newsService = inject(NewsService)
  private reservationService = inject(ReservationService)
  private otherService = inject(OtherService)

  ngOnInit() {
    this.actualDetails.set(new Details(this.baseDetails().title, this.baseDetails().buttonText, this.baseDetails().objectType))

    if (this.baseDetails().objectType == 'deviceCategory') {
      this.deviceService.getAllDevicesByCategories().subscribe({
        next: response => {
          response.forEach(element => this.cardList.update(old => [...old, new CardItem(element.name, "deviceCategory", new DevicesCategory(element.id, element.name, element.devicesList), "delete")]))
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
          response.forEach(element => this.cardList.update(old => [...old, new CardItem(element.name, "gallery", new GalleryImage(element.id, element.name, element.photoPath, element.placement), "viewImage")]))
        }
      })
    }
  }

  close() {
    this.closePopUp.emit()
  }

  buttonEvent() {
    if (this.actualDetails()?.buttonText == "newEntity") {
      if (this.selectedObject != null && this.selectedObject?.id == null) {
        this.sendPostRequest()
        return
      }

      if (this.actualDetails()?.objectType == "deviceCategory") {
        this.selectedObject = new DevicesCategory(null, "", [])
      } else if (this.actualDetails()?.objectType == "device") {
        this.selectedObject = new Device(null, "", 1)
      } else if (this.actualDetails()?.objectType == "news") {
        this.selectedObject = new NewsDetails(null, "", "", "", 0)
      } else if (this.actualDetails()?.objectType == "reservationType") {
        this.selectedObject = new ReservationType(null, "", null)
      }

      this.actualDetails.set(new Details(this.actualDetails()!.title, "newEntity", this.actualDetails()!.objectType))
      console.log(this.actualDetails())
      this.actualPage = "editPage"
    } else if (this.actualDetails()?.buttonText == "saveChanges") {
      this.sendPutRequest()
    } else if (this.actualDetails()?.buttonText == "deleteEntity") {
      this.sendDeleteRequest()
    }
  }

  showDevices(deviceCategory: DevicesCategory) {
    this.cardList.set([])
    this.actualDetails.set(new Details(deviceCategory.getName, "newEntity", "device", deviceCategory.getName))

    deviceCategory.getDevicesList.forEach(element => {
      this.cardList.update(old => [...old, new CardItem(element.name, "device", new Device(element.id, element.name, element.amount), "delete")])
    })
  }

  backToListPage() {
    if (this.baseDetails().objectType == "deviceCategory" && this.actualDetails()!.objectType == "device" && this.actualPage == "listPage") {
      this.cardList.set([])

      this.deviceService.getAllDevicesByCategories().subscribe({
        next: response => {
          response.forEach(element => this.cardList.update(old => [...old, new CardItem(element.name, "deviceCategory", element, "delete")]))
        },
        complete: () => {
          this.actualDetails.set(new Details("Kategóriák listája", "newEntity", "deviceCategory"))
        }
      })
    } else if (this.actualPage == "editPage" && this.actualDetails()!.objectType == "device") {
      this.actualDetails.set(new Details("", "newEntity", "device"))
      this.actualPage = "listPage"
    } else if (this.actualPage == "editPage") {
      this.actualDetails.set(this.baseDetails())
      this.actualPage = "listPage"
    } else if (this.actualPage == "deletePage") {
      this.actualDetails.set(new Details(this.baseDetails().title, this.baseDetails().buttonText, this.baseDetails().objectType))
      this.actualPage = "listPage"
    }
  }

  edit(wantedObject: CardItem) {
    let deviceCategoryName: string | undefined = this.actualDetails()!.deviceCategory

    this.selectedObject = wantedObject.object
    this.actualDetails.set(new Details(wantedObject.name, "saveChanges", wantedObject.objectType, deviceCategoryName))
    this.actualPage = "editPage"
  }

  delete(wantedObject: CardItem) {
    this.actualDetails.set(new Details("", "deleteEntity", wantedObject.objectType))
    this.actualPage = "deletePage"
  }

  sendPutRequest() {
    console.log("saveUpdates")
  }

  sendPostRequest() {
    console.log("saveEntity")
  }

  sendDeleteRequest() {
    console.log("deleteEntity")
  }
}

