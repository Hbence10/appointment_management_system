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
  wantedReservation?: Reservation

  form!: FormGroup;
  emailErrorMsg = signal<string>("")
  vCodeErrorMsg = signal<string>("")

  ngOnInit(): void {
    this.form = new FormGroup({
      email: new FormControl("", [Validators.required, Validators.email]),
      vCode: new FormControl("", [Validators.required, Validators.minLength(10), Validators.maxLength(10)])
    })
  }

  getReservation(){
    this.reservationService.getReservationByEmailAndVCode(this.form.controls["email"].value, this.form.controls["vCode"].value).subscribe({
      next: response => this.wantedReservation = response,
      error: error => console.log(error),
      complete: () => {
        this.wantedReservation = this.reservationService.setObject([this.wantedReservation])[0]
        console.log(this.wantedReservation)
      }
    })
  }

  cancelReservation(){

  }
}
