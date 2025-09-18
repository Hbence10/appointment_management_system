import { Component, input, output } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { CardItem } from '../../models/card.model';

@Component({
  selector: 'app-pop-up',
  imports: [MatButtonModule],
  templateUrl: './pop-up.html',
  styleUrl: './pop-up.scss'
})
export class PopUp {
  title = input.required<string>()
  buttonText = input.required<string>()

  closePopUp = output()

  close(){
    this.closePopUp.emit()
  }

  buttonEvent(){
    console.log(this.buttonText())
  }
}
