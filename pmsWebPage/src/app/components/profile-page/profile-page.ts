import { Component, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { UserService } from '../../services/user-service';
import { User } from '../../models/user.model';
import { MatButtonModule } from '@angular/material/button';
import { ReservationService } from '../../services/reservation-service';
import { Reservation } from '../../models/reservation.model';
import { ReservationCard } from '../reservation-card/reservation-card';
import { Router, RouterModule } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-profile-page',
  imports: [MatButtonModule, ReservationCard, RouterModule],
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

  ngOnInit(): void {
    this.user = this.userService.user()!

    const subscription = this.reservationService.getReservationByUserId(this.user.id!).subscribe({
      next: response => this.reservations.set(response),
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
}
