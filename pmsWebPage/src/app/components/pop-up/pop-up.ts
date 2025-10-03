import { Component, computed, inject, input, OnInit, output, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { Device } from '../../models/device.model';
import { DevicesCategory } from '../../models/deviceCategory.model';
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
import { News } from '../../models/newsDetails.model';
import { Gallery } from '../../models/galleryImage.model';


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
  }

  close() {
    this.closePopUp.emit()
  }

  buttonEvent() {

  }

  showDevices(deviceCategory: DevicesCategory) {

  }

  backToListPage() {

  }

  edit(wantedObject: CardItem) {

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

