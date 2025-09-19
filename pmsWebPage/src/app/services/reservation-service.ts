import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Observable } from 'rxjs';
import { Reservation } from '../models/reservation.model';
import { ReservationType } from '../models/reservationType.model';
import { ReservedDates } from '../models/reservedDates.model';
import { PaymentMethod } from '../models/paymentMethod.model';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {
  private http = inject(HttpClient)
  baseURL = signal<string>("http://localhost:8080")
  baseReservation = signal<Reservation>(new Reservation())
  ifRegistrationWithReservation = signal<boolean>(false)

  progressBarSteps = [true, false, false, false]

  getReservationByUserId(userId: number): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(`${this.baseURL()}/reservation/user/${userId}`)
  }

  //
  getReservedDatesOfActualMonth(startDate: string, endDate: string): Observable<ReservedDates[]> {
    return this.http.get<ReservedDates[]>(`${this.baseURL()}/reservation/reservedDates?startDate=${startDate}&endDate=${endDate}`)
  }

  getReservationTypes(): Observable<ReservationType[]> {
    return this.http.get<ReservationType[]>(`${this.baseURL()}/reservation/reservationType`)
  }

  getPaymentMethods(): Observable<PaymentMethod[]> {
    return this.http.get<PaymentMethod[]>(`${this.baseURL()}/reservation/paymentMethods`)
  }

  getReservationByDate(wantedDate: string): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(`${this.baseURL()}/date/${wantedDate}`)
  }

  makeReservation(){
    return this.http.post(`${this.baseURL()}/reservation`, this.baseReservation())
  }
}
