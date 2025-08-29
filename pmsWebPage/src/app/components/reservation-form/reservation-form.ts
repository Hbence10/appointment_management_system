import { Component, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { ReservationService } from '../../services/reservation-service';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule, MatLabel } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ReservationType } from '../../models/reservationType.model';
import { Router } from '@angular/router';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-reservation-form',
  imports: [ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatLabel, MatCheckboxModule, MatButtonModule],
  templateUrl: './reservation-form.html',
  styleUrl: './reservation-form.scss'
})
export class ReservationForm implements OnInit {
  private reservationService = inject(ReservationService)
  private destroyRef = inject(DestroyRef)
  private router = inject(Router)

  reservationTypes = signal<ReservationType[]>([])
  ifRegister = signal<boolean>(false)


  form = new FormGroup({
    firstName: new FormControl("", [Validators.required]),
    lastName: new FormControl("", [Validators.required]),
    email: new FormControl("", [Validators.required, Validators.email]),
    phone: new FormControl("", [Validators.required]),
    comment: new FormControl("", []),
    reservationType: new FormControl("", [Validators.required])
  })

  ngOnInit(): void {
    const subscription = this.reservationService.getReservationTypes().subscribe({
      next: response => this.reservationTypes.set(response)
    })

    this.destroyRef.onDestroy(() => {
      console.log("desroyed!!!! - reservationFormComponent")
      subscription.unsubscribe()
    })
  }

  selectReservationType() {

  }

  continueReservation() {
    if (this.ifRegister()) {

    }

    this.router.navigate([""])
  }
}
