import { Component, DestroyRef, inject, OnInit } from '@angular/core';
import { ReservationService } from '../../services/reservation-service';

@Component({
  selector: 'app-reservatin-canceler',
  imports: [],
  templateUrl: './reservatin-canceler.html',
  styleUrl: './reservatin-canceler.scss'
})
export class ReservatinCanceler implements OnInit{
  private reservationService = inject(ReservationService)
  private destroyRef = inject(DestroyRef)

  ngOnInit(): void {

  }
}
