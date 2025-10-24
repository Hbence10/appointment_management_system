import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { ReservedHours } from '../models/reservedHours.model';
import { ReservedDates } from '../models/reservedDates.model';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private http = inject(HttpClient)
  baseURL = signal<string>("http://localhost:8080/reservation")

  //foglalasok:
  makeAdminReservation(selectedHour: ReservedHours, adminId: number) {
    return this.http.post(`${this.baseURL()}/adminReservation`, {
      selectedHour: selectedHour,
      adminId: adminId
    })
  }

  makeReservationAlwaysBetweenTwoDates(startDate: string, endDate: string, selectedHour: ReservedHours, adminId: number) {
    return this.http.post(`${this.baseURL()}/makeReservationAlwaysBetweenTwoDates`, {
      startDate: startDate,
      endDate: endDate,
      selectedHour: selectedHour,
      adminId: adminId
    })
  }

  makeReservationByRepetitiveDates(startDate: string, endDate: string, selectedDay: "MONDAY" | "TUESDAY" | "WEDNESDAY" | "THURSDAY" | "FRIDAY" | "SATURDAY" | "SUNDAY", selectedHour: ReservedHours) {
    return this.http.post(`${this.baseURL()}/makeReservationByRepetitiveDates`, {
      
    })
  }

  //terem bezaras
  closeRoomForADay(dateText: string, closeType: "holiday" | "full" | "other") {
    console.log(dateText)
    return this.http.post(`${this.baseURL()}/closeRoomForADay`, {
      date: dateText,
      closeType: closeType
    })
  }

  closeRoomBetweenPeriod(startDate: string, endDate: string, closeType: "holiday" | "full" | "other") {
    return this.http.post(`${this.baseURL()}/closeRoomBetweenPeriod`, {
      startDate: startDate,
      endDate: endDate,
      closeType: closeType
    })
  }

  closeByRepetitiveDates(startDate: string, endDate: string, closeType: "holiday" | "full" | "other", selectedDay: "MONDAY" | "TUESDAY" | "WEDNESDAY" | "THURSDAY" | "FRIDAY" | "SATURDAY" | "SUNDAY") {
    return this.http.post(`${this.baseURL()}/closeByRepetitiveDates`, {
      startDate: startDate,
      endDate: endDate,
      closeType: closeType,
      selectedDay: selectedDay
    })
  }

  //egyeb:

}
