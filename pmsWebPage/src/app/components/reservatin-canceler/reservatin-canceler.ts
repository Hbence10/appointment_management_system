import { Component, inject, OnInit, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { RouterModule } from '@angular/router';
import { Reservation } from '../../models/reservation.model';
import { ReservationService } from '../../services/reservation-service';
import { ReservationDetail } from '../reservation-detail/reservation-detail';

@Component({
  selector: 'app-reservatin-canceler',
  imports: [MatInputModule, MatFormFieldModule, MatButtonModule, RouterModule, ReactiveFormsModule, ReservationDetail],
  templateUrl: './reservatin-canceler.html',
  styleUrl: './reservatin-canceler.scss'
})
export class ReservatinCanceler implements OnInit{
  private reservationService = inject(ReservationService)
  wantedReservation: Reservation | null = null

  form!: FormGroup;
  emailErrorMsg = signal<string>("")
  vCodeErrorMsg = signal<string>("")
  flexClassList = "d-flex justify-content-center align-items-center"

  ngOnInit(): void {
    this.form = new FormGroup({
      email: new FormControl("bzhalmai@gmail.com", [Validators.required, Validators.email]),
      vCode: new FormControl("c0ezvscsmr", [Validators.required, Validators.minLength(10), Validators.maxLength(10)])
    })
  }

  getReservation(){
    this.reservationService.getReservationByEmailAndVCode(this.form.controls["email"].value, this.form.controls["vCode"].value).subscribe({
      next: response => this.wantedReservation = response,
      error: error => console.log(error),
      complete: () => {
        this.wantedReservation = this.reservationService.setObject([this.wantedReservation])[0]
        this.flexClassList = ""
      }
    })
  }

  cancelReservation(){
    this.reservationService.cancelReservation(this.wantedReservation?.getId!, null).subscribe({
      next: response => this.wantedReservation = response,
      error: error => console.log(error),
      complete: () => {}

    })
  }

  backToForm(){
    this.wantedReservation = null;
  }
}
