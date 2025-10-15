import { Component, DestroyRef, inject, OnInit, output } from '@angular/core';
import { OtherService } from '../../services/other-service';

@Component({
  selector: 'app-history-popup',
  imports: [],
  templateUrl: './history-popup.html',
  styleUrl: './history-popup.scss'
})
export class HistoryPopup implements OnInit{
  close = output()
  private otherStuffService = inject(OtherService)
  private destroyRef = inject(DestroyRef)

  ngOnInit(): void {

  }

  closePopUp(){
    this.close.emit()
  }
}
