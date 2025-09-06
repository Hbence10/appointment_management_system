import { Component, inject, signal } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { Router, RouterModule, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-reservation-maker-page',
  imports: [RouterModule, RouterOutlet, MatIconModule],
  templateUrl: './reservation-maker-page.html',
  styleUrl: './reservation-maker-page.scss'
})
export class ReservationMakerPage {
  router = inject(Router)

}
