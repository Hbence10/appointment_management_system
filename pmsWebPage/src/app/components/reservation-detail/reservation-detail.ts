import { Component, inject, input, OnInit } from '@angular/core';
import { Reservation } from '../../models/reservation.model';
import { ReservationService } from '../../services/reservation-service';

@Component({
  selector: 'app-reservation-detail',
  imports: [],
  templateUrl: './reservation-detail.html',
  styleUrl: './reservation-detail.scss'
})
export class ReservationDetail implements OnInit{
  formattedPhoneNumber: string = ""

  reservation = input.required<Reservation>()

  ngOnInit(): void {
    this.formattedPhoneNumber = "+" + this.reservation().getPhoneCode?.countryCode + " " + this.reservation().getPhone
  }
}
