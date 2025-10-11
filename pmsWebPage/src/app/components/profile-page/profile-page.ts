import { Component, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { Router, RouterModule } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { Details } from '../../models/notEntityModels/details.model';
import { Reservation } from '../../models/reservation.model';
import { Users } from '../../models/user.model';
import { ReservationService } from '../../services/reservation-service';
import { UserService } from '../../services/user-service';
import { PopUp } from '../pop-up/pop-up';
import { ReservationCard } from '../reservation-card/reservation-card';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-profile-page',
  imports: [MatButtonModule, ReservationCard, RouterModule, PopUp, MatFormFieldModule, MatInputModule, ReactiveFormsModule],
  templateUrl: './profile-page.html',
  styleUrl: './profile-page.scss'
})
export class ProfilePage implements OnInit {
  private userService = inject(UserService)
  private cookieService = inject(CookieService)
  private reservationService = inject(ReservationService)
  private destroyRef = inject(DestroyRef)
  private router = inject(Router)

  user!: Users;
  reservations = signal<Reservation[]>([])
  showPopUp = signal<boolean>(false)
  selectedReservation = signal<Reservation>(new Reservation())
  popUpDetails: Details = new Details("", "cancelReservation", "reservation")

  isShowDeletePopUp = signal<boolean>(false)
  isEdit = signal<boolean>(false)

  form!: FormGroup;

  ngOnInit(): void {
    this.user = this.userService.user()!

    this.form = new FormGroup({
      username: new FormControl(this.user.getUsername, [Validators.required]),
      email: new FormControl(this.user.getEmail, [Validators.required, Validators.email])
    })

    const subscription = this.reservationService.getReservationByUserId(this.user.getId!).subscribe({
      next: responseList => this.reservations.set(this.reservationService.setObject(responseList)),
      error: error => {
      }
    })
    this.destroyRef.onDestroy(() => {
      subscription.unsubscribe()
    })
  }

  logOut() {
    sessionStorage.clear()
    this.cookieService.deleteAll()
    this.userService.user.set(null)
    this.router.navigate(["/homePage"])
  }

  showDetailsOfReservation(wantedReservation: Reservation) {
    this.popUpDetails.title = `#${wantedReservation.getId}`
    this.selectedReservation.set(wantedReservation)
    this.showPopUp.set(true)
  }

  showDeletePopUp(){
    this.isShowDeletePopUp.update(old => !old)
  }

  deleteProfile() {
    this.userService.deleteUser(this.user.getId!).subscribe({
      next: response => console.log(response),
      error: error => console.log(error),
      complete: () => {
        this.router.navigate(["/homePage"])
      }
    })
  }

  updateUser() {
    this.isEdit.update(old => !old)
    if (!this.isEdit()) {
      this.userService.updateUser(this.form.controls["email"].value!, this.form.controls["username"].value!, this.user.getId!).subscribe({
        next: response => console.log(response),
        error: error => console.log(error),
        complete: () => {}
      })
    }
  }
}
