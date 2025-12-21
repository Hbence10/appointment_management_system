import { CommonModule } from '@angular/common';
import { Component, input, output } from '@angular/core';
import { DevicesCategory } from '../../models/deviceCategory.model';
import { CardItem } from '../../models/notEntityModels/card.model';
import { Gallery } from '../../models/galleryImage.model';

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
  viewImage = output<Gallery>()

  showDevices() {
    if (this.cardItem().objectType != "deviceCategory") {
      return
    }
    this.changeList.emit(this.cardItem().object! as DevicesCategory)
  }

  viewImg(){
    console.log("Asd")
    this.viewImage.emit(this.cardItem().object as Gallery)
  }
}
