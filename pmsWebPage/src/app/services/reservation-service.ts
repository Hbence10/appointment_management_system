import { inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Reservation } from '../models/reservation.model';
import { HttpClient } from '@angular/common/http';
import { ReservedDates } from '../models/reservedDates.model';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {
  private router = inject(Router)
  private http = inject(HttpClient)

  getReservationByUserId(userId: number): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(`http://localhost:8080/reservation?userId=${userId}`)
  }

  getReservedDateByMonth(actualDateString: string): Observable<ReservedDates[]>{
    return this.http.get<ReservedDates[]>(`http://localhost:8080/reservation/reservedDates?actualDate=${actualDateString}`)
  }
}
