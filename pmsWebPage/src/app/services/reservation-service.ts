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

  getReservedDatesOfActualMonth(startDate: string, endDate: string): Observable<any[]> {
    return this.http.get<ReservedDates[]>(`${this.baseURL()}/reservation/reservedDates?startDate=${startDate}&endDate=${endDate}`)
  }

  getReservationByDate(wantedDate: string): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(`${this.baseURL()}/reservation/date/${wantedDate}`)
  }

  makeReservation() {
    return this.http.post(`${this.baseURL()}/reservation/makeReservation`, this.baseReservation())
  }

  //
  getReservationTypes(): Observable<any[]> {
    return this.http.get<ReservationType[]>(`${this.baseURL()}/reservation/getReservationType`)
  }

  getPaymentMethods(): Observable<PaymentMethod[]> {
    return this.http.get<PaymentMethod[]>(`${this.baseURL()}/reservation/paymentMethods`)
  }

  getPhoneCodes(): Observable<{id: number, countryCode: number, countryName: string}[]>{
    return this.http.get<{id: number, countryCode: number, countryName: string}[]>(`${this.baseURL()}/reservation/phoneCodes`)
  }
  //
}
