import { Component, DestroyRef, inject, input, OnInit, signal } from '@angular/core';
import { ReservationService } from '../../services/reservation-service';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule, MatLabel } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ReservationType } from '../../models/reservationType.model';
import { Router } from '@angular/router';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatButtonModule } from '@angular/material/button';
import { UserService } from '../../services/user-service';
import { User } from '../../models/user.model';
import { Reservation } from '../../models/reservation.model';

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
  selectedReservationType = signal<ReservationType>(new ReservationType(-1))

  form = new FormGroup({
    firstName: new FormControl("", [Validators.required]),
    lastName: new FormControl("", [Validators.required]),
    email: new FormControl("", [Validators.required, Validators.email]),
    phone: new FormControl("", [Validators.required]),
    comment: new FormControl("", []),
    reservationType: new FormControl("", [Validators.required])
  })

  ngOnInit(): void {
    this.user = this.userService.user()

    const subscription = this.reservationService.getReservationTypes().subscribe({
      next: response => this.reservationTypes.set(response),
      complete: () => console.log(this.reservationTypes())
    })

    this.destroyRef.onDestroy(() => {
      subscription.unsubscribe()
    })
  }

  selectReservationType(selectedReservationType: ReservationType, id: number) {
    this.selectedReservationType.set(selectedReservationType)
    this.form.controls["reservationType"].setValue(id.toString())
  }

  continueReservation(){
    this.registerWithReservation()

    this.router.navigate([""])

  }

  registerWithReservation(){

  }
}
