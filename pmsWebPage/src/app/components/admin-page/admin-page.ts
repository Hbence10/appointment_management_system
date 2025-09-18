import { ChangeDetectionStrategy, Component, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { provideNativeDateAdapter } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { Reservation } from '../../models/reservation.model';
import { PopUp } from '../pop-up/pop-up';

@Component({
  selector: 'app-admin-page',
  imports: [MatCardModule, MatDatepickerModule, PopUp],
  templateUrl: './admin-page.html',
  styleUrl: './admin-page.scss',
  providers: [provideNativeDateAdapter()],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class AdminPage implements OnInit {
  private destroyRef = inject(DestroyRef)


  todaysReservation = signal<Reservation[]>([])
  reservationsOfSelectedDate = signal<Reservation[]>([])
  selectedReservation = signal<null | Reservation>(null)

  isShowPupUp = signal<boolean>(false)
  popUpTitle = signal<string>("")
  popUpButtonText = signal<string>("")
  popUpObjectType = signal<string>("")

  ngOnInit(): void {
  }

  selectObjectList(title: string, buttonText: string, objectType: "deviceCategory" | "news" | "device" | "gallery" | "reservationType" | "rule" | "other") {
    this.popUpTitle.set(title)
    this.popUpButtonText.set(buttonText)
    this.popUpObjectType.set(objectType)
    this.isShowPupUp.set(true)
  }

  closePopUp() {
    this.isShowPupUp.set(false)
  }

  //Ez majd az output altal fog ervenyesulni
  changeObjectList(eventParam: any) {
    console.log("asd")
  }
}
