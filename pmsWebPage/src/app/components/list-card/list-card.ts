import { Component, inject, input, OnInit, output } from '@angular/core';
import { CardItem } from '../../models/notEntityModels/card.model';
import { Device } from '../../models/device.model';
import { DevicesCategory } from '../../models/deviceCategory.model';
import { CommonModule } from '@angular/common';
import { DeviceService } from '../../services/device-service';
import { NewsService } from '../../services/news-service';
import { ReservationService } from '../../services/reservation-service';
import { OtherService } from '../../services/other-service';

@Component({
  selector: 'app-list-card',
  imports: [CommonModule],
  templateUrl: './list-card.html',
  styleUrl: './list-card.scss'
})
export class ListCard {
  cardItem = input.required<CardItem>()
  edit = output<CardItem>()
  changeList = output<DevicesCategory>()
  delete = output<any>()

  eventsTypeList: string[] = []

  button1Event() {
    if (this.cardItem().button1Event == "delete") {
      this.delete.emit(this.cardItem())
    } else {

    }
  }

  editObject() {
    this.edit.emit(this.cardItem())
  }

  showDevices() {
    if (this.cardItem().objectType != "deviceCategory") {
      return
    }
    this.changeList.emit(this.cardItem().object! as DevicesCategory)
  }

}
