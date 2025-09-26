import { Component, inject, OnInit } from '@angular/core';
import { Router, RouterModule, RouterOutlet } from '@angular/router';
import { Reservation } from '../../models/reservation.model';
import { ReservationService } from '../../services/reservation-service';
import { MatStepperModule } from '@angular/material/stepper';
import { AppointmentSelector } from './appointment-selector/appointment-selector';
import { ReservationForm } from './reservation-form/reservation-form';
import { RuleReader } from './rule-reader/rule-reader';
import { ReservationFinalize } from './reservation-finalize/reservation-finalize';

@Component({
  selector: 'app-reservation-maker-page',
  imports: [RouterModule, MatStepperModule, AppointmentSelector, ReservationForm, RuleReader, ReservationFinalize],
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
}
