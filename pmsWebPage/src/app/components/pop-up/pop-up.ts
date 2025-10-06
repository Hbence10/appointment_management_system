import { ChangeDetectionStrategy, Component, computed, DestroyRef, inject, input, OnInit, output, signal } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { Device } from '../../models/device.model';
import { DevicesCategory } from '../../models/deviceCategory.model';
import { Gallery } from '../../models/galleryImage.model';
import { News } from '../../models/newsDetails.model';
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
import { MatSelectModule } from '@angular/material/select';
import { UserService } from '../../services/user-service';
import { Router } from '@angular/router';
import { response } from 'express';


@Component({
  selector: 'app-pop-up',
  imports: [MatButtonModule, ListCard, RuleEditor, ReservationDetail, MatFormFieldModule, ObjectEditor, MatSelectModule],
  templateUrl: './pop-up.html',
  styleUrl: './pop-up.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class PopUp implements OnInit {
  reservation = input.required<Reservation>()
  closePopUp = output()
  cardList = signal<CardItem[]>([])

  baseDetails = input.required<Details>()
  actualDetails = signal<Details | null>(null);
  selectedObject: DevicesCategory | Device | News | ReservationType | Gallery | null = null
  editForm!: FormGroup
  form!: FormGroup;

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
  private userService = inject(UserService)
  private destroyRef = inject(DestroyRef)
  private router = inject(Router)

  ngOnInit() {
    this.reservationService.form.reset()
    this.form = this.reservationService.form

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
    console.log(errorResponse)
  }

  buttonEvent() {
    if (this.actualDetails()?.buttonText == "newEntity") {
      const objectTypes: string[] = ["deviceCategory", "device", "news", "reservationType", "gallery"]
      const hunObjectNames: string[] = ["Eszköz kategória", "Eszköz", "Hír", "Foglalás tipus", "Fénykép"]

      this.actualPage = "editPage"
      this.actualDetails.set(new Details(`${hunObjectNames[objectTypes.indexOf(this.actualDetails()!.objectType)]} hozzáadása`, "saveChanges", this.actualDetails()!.objectType, this.actualDetails()?.deviceCategory))
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
    this.reservationService.form.reset()
    this.setForm()

    this.actualPage = "editPage"
    this.actualDetails.set(new Details(`${wantedObject.name} szerkesztése`, "saveChanges", wantedObject.objectType, this.actualDetails()?.deviceCategory))
  }

  delete(wantedObject: CardItem) {
    this.selectedObject = wantedObject.object
    this.actualDetails.set(new Details("", "deleteEntity", wantedObject.objectType, this.actualDetails()?.deviceCategory))
    this.actualPage = "deletePage"
  }

  //
  sendPutRequest() {
    console.log("saveUpdates")
    if (this.selectedObject instanceof News) {
      this.selectedObject = new News(this.selectedObject.getId, this.form.controls["property1"].value, this.form.controls["property2"].value, this.form.controls["property3"].value, this.userService.user()!)
      this.newsService.updateNews(this.selectedObject).subscribe({
        next: response => console.log(response),
        error: error => console.log(error),
        complete: () => this.actualPage = "listPage"
      })
    } else if (this.selectedObject instanceof ReservationType) {
      this.selectedObject = new ReservationType(this.selectedObject.getId, this.form.controls["property1"].value, Number(this.form.controls["property2"].value));
      this.reservationService.updateReservationType(this.selectedObject).subscribe({
        next: response => console.log(response)
      })
    } else if (this.selectedObject instanceof DevicesCategory) {
      this.selectedObject = new DevicesCategory(this.selectedObject.getId, this.form.controls["property1"].value!)
      this.deviceService.updateDeviceCategory(this.selectedObject).subscribe({
        next: response => console.log(response)
      })
    } else if (this.selectedObject instanceof Device) {
      // this.selectedObject = new Device(this.selectedObject.getId, this.form.controls["property1"].value, this.form.controls["property2"].value, acta)
      console.log(this.deviceService.selectedCategory)
    }
  }

  sendPostRequest() {
    console.log("saveEntity")
    if (this.selectedObject instanceof News) {
      this.selectedObject = new News(null, this.form.controls["property1"].value, this.form.controls["property2"].value, this.form.controls["property3"].value, this.userService.user()!)
      this.newsService.createNews(this.selectedObject).subscribe({
        next: response => console.log(response),
        error: error => console.log(error),
        complete: () => this.actualPage = "listPage"
      })
    } else if (this.selectedObject instanceof ReservationType) {
      this.selectedObject = new ReservationType(null, this.form.controls["property1"].value, Number(this.form.controls["property2"].value));
      console.log(this.selectedObject)
      this.reservationService.createReservationType(this.selectedObject).subscribe({
        next: response => console.log(response)
      })
    } else if (this.selectedObject instanceof DevicesCategory) {
      this.selectedObject = new DevicesCategory(null, this.form.controls["property1"].value)
      this.deviceService.addDeviceCategory(this.selectedObject).subscribe({
        next: response => console.log(response)
      })
    } else if (this.selectedObject instanceof Device) {

    }
  }

  sendDeleteRequest() {
    console.log("deleteEntity")
    if (this.selectedObject instanceof News) {
      this.newsService.deleteNews(this.selectedObject.getId!).subscribe({
        next: response => console.log(response),
        error: error => console.log(error),
        complete: () => this.actualPage = "listPage"
      })
    } else if (this.selectedObject instanceof ReservationType) {
      this.reservationService.deleteReservationType(this.selectedObject.getId!).subscribe({
        next: response => console.log(response),
      })
    } else if (this.selectedObject instanceof DevicesCategory) {
      this.deviceService.deleteDeviceCategory(this.selectedObject.getId!).subscribe({
        next: response => console.log(response)
      })
    } else if (this.selectedObject instanceof Device) {

    }
  }

  setForm() {
    this.reservationService.form.controls["property1"].setValue(this.selectedObject!.getName)
    if (this.selectedObject instanceof Device) {
      this.reservationService.form.controls["property2"].setValue((this.selectedObject as Device).getAmount)
      this.reservationService.form.controls["property3"].setValue(this.actualDetails()?.deviceCategory.getName)
    } else if (this.selectedObject instanceof News) {
      this.reservationService.form.controls["property2"].setValue((this.selectedObject as News).getText)
      this.reservationService.form.controls["property3"].setValue((this.selectedObject as News).getName)
    } else if (this.selectedObject instanceof ReservationType) {
      this.reservationService.form.controls["property2"].setValue((this.selectedObject as ReservationType).getPrice)
    }

    if (this.selectedObject instanceof Device) {
      this.reservationService.form.controls["property3"].addValidators(Validators.required)
    } else if (this.selectedObject instanceof ReservationType || this.selectedObject instanceof News) {
      this.reservationService.form.controls["property2"].addValidators(Validators.required)
      this.reservationService.form.controls["property2"].addValidators(Validators.required)
    }
  }
}

