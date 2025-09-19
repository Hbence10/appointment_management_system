import { Component, DestroyRef, inject, input, OnInit, Signal, signal } from '@angular/core';

import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule, MatLabel } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

import { Router } from '@angular/router';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatButtonModule } from '@angular/material/button';
import { ReservationService } from '../../../services/reservation-service';
import { UserService } from '../../../services/user-service';
import { ReservationType } from '../../../models/reservationType.model';
import { User } from '../../../models/user.model';
import { Reservation } from '../../../models/reservation.model';


@Component({
  selector: 'app-reservation-form',
  imports: [ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatLabel, MatCheckboxModule, MatButtonModule],
  templateUrl: './reservation-form.html',
  styleUrl: './reservation-form.scss'
})
export class ReservationForm implements OnInit {
  private reservationService = inject(ReservationService)
  private destroyRef = inject(DestroyRef)
  private userService = inject(UserService)
  private router = inject(Router)


  reservationTypes = signal<ReservationType[]>([])
  user: null | User = null
  selectedReservationType = signal<ReservationType | null>(null)
  baseReservation!: Signal<Reservation>;
  form!: FormGroup;


  ngOnInit(): void {
    this.user = this.userService.user()
    this.baseReservation = signal(this.reservationService.baseReservation())

    if(this.baseReservation().reservationTypeId){
      this.selectedReservationType.set(this.baseReservation().reservationTypeId)
    }

    const subscription = this.reservationService.getReservationTypes().subscribe({
      next: response => this.reservationTypes.set(response),
      complete: () => console.log(this.reservationTypes())
    })

    this.destroyRef.onDestroy(() => {
      subscription.unsubscribe()
    })

    this.form = new FormGroup({
      firstName: new FormControl(this.baseReservation().firstName, [Validators.required]),
      lastName: new FormControl(this.baseReservation().lastName, [Validators.required]),
      email: new FormControl(this.baseReservation().email, [Validators.required, Validators.email]),
      phone: new FormControl(this.baseReservation().phone, [Validators.required]),
      comment: new FormControl(this.baseReservation().comment, []),
      reservationType: new FormControl(!this.baseReservation().reservationTypeId ? "" : this.baseReservation().reservationTypeId.name, [Validators.required])
    })
  }

  selectReservationType(reservationType: ReservationType) {
    this.selectedReservationType.set(reservationType)
    this.baseReservation().reservationTypeId = reservationType
    this.form.controls["reservationType"].setValue(this.selectedReservationType()!.name)
  }

  continueReservation() {
    this.baseReservation().firstName = this.form.controls["firstName"].value
    this.baseReservation().lastName = this.form.controls["lastName"].value
    this.baseReservation().email = this.form.controls["email"].value
    this.baseReservation().phone = this.form.controls["phone"].value
    this.baseReservation().comment = this.form.controls["comment"].value

    this.reservationService.progressBarSteps[2] = true
    this.router.navigate(["/makeReservation/rule"])
  }


}
