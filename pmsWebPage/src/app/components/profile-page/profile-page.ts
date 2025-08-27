import { Component, inject, OnInit, signal } from '@angular/core';
import { UserService } from '../../services/user-service';
import { User } from '../../models/user.model';
import { MatButtonModule } from '@angular/material/button';
import { ReservationService } from '../../services/reservation-service';
import { Reservation } from '../../models/reservation.model';
import { ReservationCard } from '../reservation-card/reservation-card';

@Component({
  selector: 'app-profile-page',
  imports: [MatButtonModule, ReservationCard],
  templateUrl: './profile-page.html',
  styleUrl: './profile-page.scss'
})
export class ProfilePage implements OnInit{
  private userService = inject(UserService)
  private reservationService = inject(ReservationService)

  user!: User;
  reservations = signal<Reservation[]>([])

  ngOnInit(): void {
    this.user = this.userService.user()!

    this.reservationService.getReservationByUserId(this.user.id).subscribe({
      next: response => this.reservations.set(response),
      complete: () => console.log(this.reservations())
    })
  }


  logOut(){

  }
}
