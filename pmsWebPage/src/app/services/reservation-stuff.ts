import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Observable } from 'rxjs';
import { PaymentMethod } from '../models/paymentMethod.model';
import { ReservationType } from '../models/reservationType.model';

@Injectable({
  providedIn: 'root'
})
export class ReservationStuff {
  private http = inject(HttpClient)
  baseURL = signal<string>("http://localhost:8080/reservationStuff")

  //egyeb
  getPaymentMethods(): Observable<PaymentMethod[]> {
    return this.http.get<PaymentMethod[]>(`${this.baseURL()}/paymentMethods`)
  }

  getPhoneCodes(): Observable<{ id: number, countryCode: number, countryName: string }[]> {
    return this.http.get<{ id: number, countryCode: number, countryName: string }[]>(`${this.baseURL()}/phoneCodes`)
  }

  //Reservation Types:
  getReservationTypes(): Observable<ReservationType[]> {
    return this.http.get<ReservationType[]>(`${this.baseURL()}/getReservationType`)
  }

  updateReservationType(updatedReservationType: ReservationType){
    return this.http.put(`${this.baseURL()}/updateReservationType`, updatedReservationType)
  }

  deleteReservationType(id: number){
    return this.http.delete(`${this.baseURL()}/deleteReservationType/${id}`)
  }

  createReservationType(newReservationType: ReservationType){
    return this.http.post(`${this.baseURL()}/addReservationType`, newReservationType)
  }
}

