import { Component, inject, input, OnInit, output } from '@angular/core';
import { CardItem } from '../../models/card.model';
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
  eventsTypeList: string[] = []
  changeList = output<DeviceCategory>()

  private deviceService = inject(DeviceService)
  private newsService = inject(NewsService)
  private reservationService = inject(ReservationService)
  private otherService = inject(OtherService)

  ngOnInit(): void {

  }

  button1Event(){
    if(this.cardItem().button1Event == "delete"){

      if(this.cardItem().objectType == "deviceCategory"){

      } else if(this.cardItem().objectType == "device"){

      } else if(this.cardItem().objectType == "reservationType"){

      } else if(this.cardItem().objectType == "news"){

      }

    }
  }

  editObject(){
    console.log("editObject")
  }

  showDevices(){
    if(this.cardItem().objectType != "deviceCategory"){
      return
    }
    this.changeList.emit(this.cardItem().object!)
  }
}
