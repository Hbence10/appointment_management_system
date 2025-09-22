import { Component, inject, input, OnInit, output } from '@angular/core';
import { CardItem } from '../../models/notEntityModels/card.model';
import { Device } from '../../models/device.model';
import { DeviceCategory } from '../../models/deviceCategory.model';
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
export class ListCard implements OnInit{
  cardItem = input.required<CardItem>()
  edit = output<CardItem>()
  changeList = output<DeviceCategory>()
  delete = output<any>()

  eventsTypeList: string[] = []

  ngOnInit(): void {

  }

  button1Event(){
    if(this.cardItem().button1Event == "delete"){
      this.delete.emit(this.cardItem())
    } else {

    }
  }

  editObject(){
    this.edit.emit(this.cardItem())
  }

  showDevices(){
    if(this.cardItem().objectType != "deviceCategory"){
      return
    }
    this.changeList.emit(this.cardItem().object!)
  }
}
