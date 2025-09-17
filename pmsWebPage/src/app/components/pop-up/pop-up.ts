import { Component, input, output } from '@angular/core';

@Component({
  selector: 'app-pop-up',
  imports: [],
  templateUrl: './pop-up.html',
  styleUrl: './pop-up.scss'
})
export class PopUp {
  title = input<string>()
  buttonText = input<string>()

  closePopUp = output()
  close(){
    this.closePopUp.emit()
  }
}
