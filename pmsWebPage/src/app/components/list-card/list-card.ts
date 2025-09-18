import { Component, input, OnInit, output } from '@angular/core';
import { CardItem } from '../../models/card.model';
import { Device } from '../../models/device.model';
import { DeviceCategory } from '../../models/deviceCategory.model';

@Component({
  selector: 'app-list-card',
  imports: [],
  templateUrl: './list-card.html',
  styleUrl: './list-card.scss'
})
export class ListCard implements OnInit{
  cardItem = input.required<CardItem>()
  eventsTypeList: string[] = []
  changeList = output<DeviceCategory>()

  ngOnInit(): void {

  }

  button1Event(){
    console.log("button1Event")
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
