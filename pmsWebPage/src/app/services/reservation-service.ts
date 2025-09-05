import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Observable } from 'rxjs';
import { Reservation } from '../models/reservation.model';
import { ReservationType } from '../models/reservationType.model';
import { ReservedDates } from '../models/reservedDates.model';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {
  private http = inject(HttpClient)
  baseURL = signal<string>("http://localhost:8080")
  baseReservation = signal<Reservation>(new Reservation())
  ifRegistrationWithReservation = signal<boolean>(false)

  getReservationByUserId(userId: number): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(`${this.baseURL()}/reservation/user/${userId}`)
  }

  getReservedDatesOfActualMonth(actualDateString: string): Observable<ReservedDates[]> {
    return this.http.get<ReservedDates[]>(`${this.baseURL()}/reservation/reservedDates?actualDate=${actualDateString}`)
  }

  getReservationTypes(): Observable<ReservationType[]> {
    return this.http.get<ReservationType[]>(`${this.baseURL()}/reservation/reservationType`)
  }

  getPaymentMethods(): Observable<{ id: number, name: string }[]> {
    return this.http.get<{ id: number, name: string }[]>(`${this.baseURL()}/reservation/paymentMethods`)
  }

  getReservationByDate(wantedDate: string): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(`${this.baseURL()}/date/${wantedDate}`)
  }
}
