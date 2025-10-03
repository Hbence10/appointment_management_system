import { Component, computed, DestroyRef, inject, input, OnInit, output, signal } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { DevicesCategory } from '../../models/deviceCategory.model';
import { CardItem } from '../../models/notEntityModels/card.model';
import { Details } from '../../models/notEntityModels/details.model';
import { Reservation } from '../../models/reservation.model';
import { DeviceService } from '../../services/device-service';
import { NewsService } from '../../services/news-service';
import { OtherService } from '../../services/other-service';
import { ReservationService } from '../../services/reservation-service';
import { ObjectEditor } from '../admin-page/object-editor/object-editor';
import { RuleEditor } from '../admin-page/rule-editor/rule-editor';
import { ListCard } from '../list-card/list-card';
import { ReservationDetail } from '../reservation-detail/reservation-detail';
import { Gallery } from '../../models/galleryImage.model';
import { ReservationType } from '../../models/reservationType.model';
import { News } from '../../models/newsDetails.model';
import { Device } from '../../models/device.model';


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
  selectedObject: DevicesCategory | Device | News | ReservationType | Gallery | null = null
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
  private destroyRef = inject(DestroyRef)

  ngOnInit() {
    this.actualDetails.set(new Details(this.baseDetails().title, this.baseDetails().buttonText, this.baseDetails().objectType))
    let subscription;
    if (this.actualDetails()?.objectType == 'deviceCategory') {
      subscription = this.deviceService.getAllDevicesByCategories().subscribe({
        next: responseList => this.setCardList(responseList.map(element => Object.assign(new DevicesCategory(), element)), "deviceCategory"),
        error: error => this.setErrors(error)
      })

    } else if (this.actualDetails()?.objectType == 'news') {
      subscription = this.newsService.getAllNews().subscribe({
        next: responseList => this.setCardList(responseList.map(element => Object.assign(new News(), element)), "news"),
        error: error => this.setErrors(error)
      })

    } else if (this.actualDetails()?.objectType == 'gallery') {
      subscription = this.otherService.getAllGalleryImages().subscribe({
        next: responseList => this.setCardList(responseList.map(element => Object.assign(new Gallery(), element)), "gallery"),
        error: error => this.setErrors(error)
      })
    } else if (this.actualDetails()?.objectType == 'reservationType') {
      subscription = this.reservationService.getReservationTypes().subscribe({
        next: responseList => this.setCardList(responseList.map(element => Object.assign(new ReservationType(), element)), "reservationType"),
        error: error => this.setErrors(error)
      })
    }

    this.destroyRef.onDestroy(() => {
      subscription!.unsubscribe()
    })
  }

  close() {
    this.closePopUp.emit()
  }

  setCardList(responseList: (DevicesCategory | Device | News | ReservationType | Gallery)[], objectType: "deviceCategory" | "device" | "news" | "reservationType" | "gallery") {
    responseList.forEach(element => {
      this.cardList.update(old => [...old, new CardItem(element.getName, objectType, element, element instanceof Gallery ? "viewImage" : "delete")])
    })
  }

  setErrors(errorResponse: any) {
  }

  buttonEvent() {
    if (this.actualDetails()?.buttonText == "newEntity") {
      this.actualPage = "editPage"
      this.actualDetails.set(new Details("", "saveChanges", this.actualDetails()!.objectType, this.actualDetails()?.deviceCategory))
      if (this.actualDetails()!.objectType == "device") {
        this.selectedObject = new Device()
      } else if (this.actualDetails()!.objectType == "deviceCategory") {
        this.selectedObject = new DevicesCategory()
      } else if (this.actualDetails()!.objectType == "reservationType") {
        this.selectedObject = new ReservationType()
      } else if (this.actualDetails()!.objectType == "news") {
        this.selectedObject = new News()
      } else if (this.actualDetails()!.objectType == "gallery") {
        this.selectedObject = new Gallery()
      }
      this.setForm()
    } else if (this.actualDetails()?.buttonText == "saveChanges") {
      if (this.selectedObject?.getId == null) {
        this.sendPostRequest()
      } else if (this.selectedObject.getId != null) {
        this.sendPutRequest()
      }
    } else if (this.actualDetails()?.buttonText == "deleteEntity") {
      this.sendDeleteRequest()
    }
  }

  showDevices(deviceCategory: DevicesCategory) {
    this.cardList.set([])
    deviceCategory.setDevicesList = deviceCategory.getDevicesList.map(element => Object.assign(new Device(), element))

    deviceCategory.getDevicesList.forEach(device => {
      this.cardList.update(old => [...old, new CardItem(device.getName, "device", device, "delete")])
    })
    this.actualDetails.set(new Details(deviceCategory.getName, "newEntity", "device", deviceCategory))
  }

  backToListPage() {
    if (this.actualPage == "listPage") {
      this.cardList.set([])
      this.deviceService.getAllDevicesByCategories().subscribe({
        next: responseList => this.setCardList(responseList.map(element => Object.assign(new DevicesCategory(), element)), "deviceCategory"),
        error: error => this.setErrors(error),
        complete: () => { this.actualDetails.set(new Details(this.baseDetails().title, this.baseDetails().buttonText, this.baseDetails().objectType, this.actualDetails()?.deviceCategory)) }
      })
    } else if (this.actualPage == "deletePage") {
      if (this.actualDetails()!.objectType == "device") {
        this.actualDetails.set(new Details(this.actualDetails()!.deviceCategory.getName, "newEntity", "device", this.actualDetails()?.deviceCategory))
      } else {
        this.actualDetails.set(new Details(this.baseDetails().title, this.baseDetails().buttonText, this.baseDetails().objectType, this.actualDetails()?.deviceCategory))
      }

      this.actualPage = "listPage"
    } else if (this.actualPage == "editPage") {
      if (this.actualDetails()?.objectType == "device") {
        this.actualDetails.set(new Details(this.actualDetails()!.deviceCategory.getName, "newEntity", "device", this.actualDetails()?.deviceCategory))
      } else {
      this.actualDetails.set(new Details(this.baseDetails().title, this.baseDetails().buttonText, this.baseDetails().objectType, this.actualDetails()?.deviceCategory))
      }

      this.actualPage = "listPage"
    }
  }

  edit(wantedObject: CardItem) {
    this.selectedObject = wantedObject.object
    this.form.reset()
    this.setForm()


    this.actualPage = "editPage"
    this.actualDetails.set(new Details(wantedObject.name, "saveChanges", wantedObject.objectType, this.actualDetails()?.deviceCategory))
  }

  delete(wantedObject: CardItem) {
    this.actualDetails.set(new Details("", "deleteEntity", wantedObject.objectType, this.actualDetails()?.deviceCategory))
    this.actualPage = "deletePage"
  }

  //
  sendPutRequest() {
    console.log("saveUpdates")
  }

  sendPostRequest() {
    console.log("saveEntity")
  }

  sendDeleteRequest() {
    console.log("deleteEntity")
  }

  setForm() {
    this.form.controls["property1"].setValue(this.selectedObject!.getName)
    if (this.selectedObject instanceof Device) {
      this.form.controls["property2"].setValue((this.selectedObject as Device).getAmount)
      // this.form.controls["property3"].setValue("")
    } else if (this.selectedObject instanceof News) {
      this.form.controls["property2"].setValue((this.selectedObject as News).getText)
      // this.form.controls["property3"].setValue("")
    } else if (this.selectedObject instanceof ReservationType) {
      this.form.controls["property2"].setValue((this.selectedObject as ReservationType).getPrice)
    }

    if (this.selectedObject instanceof Device || this.selectedObject instanceof News) {
      this.form.controls["property2"].addValidators(Validators.required)
      this.form.controls["property3"].addValidators(Validators.required)
    } else if (this.selectedObject instanceof ReservationType) {
      this.form.controls["property2"].addValidators(Validators.required)
    }
  }
}

