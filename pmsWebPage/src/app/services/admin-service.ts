import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { ReservedHours } from '../models/reservedHours.model';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private http = inject(HttpClient)
  baseURL = signal<string>("http://localhost:8080/reservation")

  //foglalasok:
  makeAdminReservation(selectedHour: ReservedHours, adminId: number) {
    return this.http.post(`${this.baseURL()}/adminReservation`, {

    })
  }

  makeReservationByRepetitiveDates(startDate: String, endDate: String, selectedDay: String, selectedHour: ReservedHours) {
    return this.http.post(`${this.baseURL()}/makeReservationByRepetitiveDates`, {

    })
  }

  makeReservationAlwaysBetweenTwoDates(startDate: String, endDate: String, selectedHour: ReservedHours) {
    return this.http.post(`${this.baseURL()}/makeReservationAlwaysBetweenTwoDates`, {

    })
  }

  //terem bezaras
  closeRoomForADay(date: Date, closeType: "holiday" | "full" | "other") {
    return this.http.post(`${this.baseURL()}/closeRoomForADay`, {

    })
  }

  closeRoomBetweenPeriod(startDate: Date, endDate: Date, closeType: "holiday" | "full" | "other") {
    return this.http.post(`${this.baseURL()}/closeRoomBetweenPeriod`, {

    })
  }

  closeByRepetitiveDates(startDate: String, endDate: String, closeType: "holiday" | "full" | "other", selectedDay: string) {
    return this.http.post(`${this.baseURL()}/closeByRepetitiveDates`, {

    })
  }
}
