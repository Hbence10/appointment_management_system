import { Component, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { UserService } from '../../services/user-service';
import { User } from '../../models/user.model';
import { MatButtonModule } from '@angular/material/button';
import { ReservationService } from '../../services/reservation-service';
import { Reservation } from '../../models/reservation.model';
import { ReservationCard } from '../reservation-card/reservation-card';
import { Router, RouterModule } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { PopUp } from '../pop-up/pop-up';
import { Details } from '../../models/notEntityModels/details.model';
import { PaymentMethod } from '../../models/paymentMethod.model';
import { ReservationType } from '../../models/reservationType.model';
import { ReservedHours } from '../../models/reservedHours.model';
import { ReservedDates } from '../../models/reservedDates.model';
import { Status } from '../../models/status.model';

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

  ngOnInit(): void {
    this.user = this.userService.user()!

    const subscription = this.reservationService.getReservationByUserId(this.user.getId!).subscribe({
      next: responseList => {

        responseList.forEach(response => {
          let reservation = Object.assign(new Reservation(), response)
          reservation.setPaymentMethod = Object.assign(new PaymentMethod(), reservation.getPaymentMethod)
          reservation.setReservationTypeId = Object.assign(new ReservationType(), reservation.getReservationTypeId)
          let reservedHour = Object.assign(new ReservedHours(), reservation.getReservedHours)
          reservedHour.setDate = Object.assign(new ReservedDates(), reservedHour.getDate)
          reservation.setReservedHours = reservedHour
          reservation.setStatus = Object.assign(new Status(), reservation.getStatus)
          this.reservations.update(old => [...old, reservation])
        })

      },
      complete: () => console.log(this.reservations())
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
}
