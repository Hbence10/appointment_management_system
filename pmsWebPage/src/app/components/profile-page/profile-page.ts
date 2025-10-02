import { Component, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { Router, RouterModule } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { Details } from '../../models/notEntityModels/details.model';
import { Reservation } from '../../models/reservation.model';
import { User } from '../../models/user.model';
import { ReservationService } from '../../services/reservation-service';
import { UserService } from '../../services/user-service';
import { PopUp } from '../pop-up/pop-up';
import { ReservationCard } from '../reservation-card/reservation-card';

@Component({
  selector: 'app-profile-page',
  imports: [MatButtonModule, ReservationCard, RouterModule, PopUp],
  templateUrl: './profile-page.html',
  styleUrl: './profile-page.scss'
})
export class ProfilePage implements OnInit {
  private userService = inject(UserService)
  private cookieService = inject(CookieService)
  private reservationService = inject(ReservationService)
  private destroyRef = inject(DestroyRef)
  private router = inject(Router)

  user!: User;
  reservations = signal<Reservation[]>([])
  showPopUp = signal<boolean>(false)
  selectedReservation = signal<Reservation>(new Reservation())
  popUpDetails: Details = new Details("", "cancelReservation", "reservation")

  isEditUsername = signal<boolean>(false)
  isEditEmail = signal<boolean>(false)

  ngOnInit(): void {
    this.user = this.userService.user()!

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

  showDetailsOfReservation(wantedReservation: Reservation){
    this.popUpDetails.title = `#${wantedReservation.getId}`
    this.selectedReservation.set(wantedReservation)
    this.showPopUp.set(true)
  }

  deleteProfile(){

  }

  updateUser(){

  }
}
