import { Component, inject, OnInit, signal } from '@angular/core';
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
export class ReservationMakerPage implements OnInit{
  private router = inject(Router)
  private reservationService = inject(ReservationService)

  ngOnInit(): void {
    this.reservationService.baseReservation.set(new Reservation)
  }
}
