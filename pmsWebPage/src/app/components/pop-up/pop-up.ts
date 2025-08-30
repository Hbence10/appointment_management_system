import { Component, input, output } from '@angular/core';
import { DeviceCategory } from '../../models/deviceCategory.model';
import { NewsDetails } from '../../models/newsDetails.model';

@Component({
  selector: 'app-pop-up',
  imports: [],
  templateUrl: './pop-up.html',
  styleUrl: './pop-up.scss'
})
export class PopUp {
  inputValue = input<DeviceCategory | NewsDetails>()

  closePopUp = output()

  close(){
    this.closePopUp.emit()
  }
}
