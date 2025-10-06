import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Observable } from 'rxjs';
import { PaymentMethod } from '../models/paymentMethod.model';
import { Reservation } from '../models/reservation.model';
import { ReservationType } from '../models/reservationType.model';
import { ReservedDates } from '../models/reservedDates.model';
import { ReservedHours } from '../models/reservedHours.model';
import { Status } from '../models/status.model';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {
  private http = inject(HttpClient)
  baseURL = signal<string>("http://localhost:8080/reservation")
  baseReservation = signal<Reservation>(new Reservation())
  ifRegistrationWithReservation = signal<boolean>(false)
  progressBarSteps = [true, false, false, false]

  form: FormGroup = new FormGroup({
    property1: new FormControl("", [Validators.required]),
    property2: new FormControl("", []),
    property3: new FormControl("", [])
  });

  setObject(responseList: any[]): Reservation[] {
    const returnList: Reservation[] = []

    responseList.forEach(response => {
      let reservation = Object.assign(new Reservation(), response)
      reservation.setPaymentMethod = Object.assign(new PaymentMethod(), reservation.getPaymentMethod)
      reservation.setReservationTypeId = Object.assign(new ReservationType(), reservation.getReservationTypeId)
      let reservedHour = Object.assign(new ReservedHours(), reservation.getReservedHours)
      reservedHour.setDate = Object.assign(new ReservedDates(), reservedHour.getDate)
      reservation.setReservedHours = reservedHour
      reservation.setStatus = Object.assign(new Status(), reservation.getStatus)
      returnList.push(reservation)
    })

    return returnList
  }


  //Keresek:
  getReservationByUserId(userId: number): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(`${this.baseURL()}/user/${userId}`)
  }

  getReservedDatesOfActualMonth(startDate: string, endDate: string): Observable<ReservedDates[]> {
    return this.http.get<ReservedDates[]>(`${this.baseURL()}/reservedDates?startDate=${startDate}&endDate=${endDate}`)
  }

  getReservationByDate(wantedDate: string): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(`${this.baseURL()}/date/${wantedDate}`)
  }

  makeReservation() {
    return this.http.post(`${this.baseURL()}/makeReservation`, this.baseReservation())
  }

  //
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
