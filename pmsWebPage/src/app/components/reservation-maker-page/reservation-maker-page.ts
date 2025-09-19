import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { Router, RouterModule, RouterOutlet } from '@angular/router';
import { ReservationService } from '../../services/reservation-service';
import { Reservation } from '../../models/reservation.model';

@Component({
  selector: 'app-reservation-maker-page',
  imports: [RouterModule, RouterOutlet, MatIconModule],
  templateUrl: './reservation-maker-page.html',
  styleUrl: './reservation-maker-page.scss'
})
export class ReservationMakerPage implements OnInit {
  private router = inject(Router)
  private reservationService = inject(ReservationService)

  checkList = this.reservationService.progressBarSteps
  actualSteps = this.router.url

  ngOnInit(): void {
    this.reservationService.baseReservation.set(new Reservation())
  }

  //Az elso lepes mindig elerheto lesz
  isStep1 = signal<boolean>(true)

  //
  isStep2 = computed<boolean>(() => {
    return this.reservationService.baseReservation().reservedHours.start == undefined && this.reservationService.baseReservation().reservedHours.end == undefined;
  })

}
