import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { ReservedHours } from '../models/reservedHours.model';
import { Observable } from 'rxjs';
import { Users } from '../models/user.model';
import { AdminDetails } from '../models/adminDetails.model';
import { Reservation } from '../models/reservation.model';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private http = inject(HttpClient)
  baseURL = signal<string>("http://localhost:8080/admin")
  selectedUserIdForAdmin: number | null = 0;

  //foglalasok:
  makeAdminReservation(selectedHour: ReservedHours, adminId: number) {
    return this.http.post(`${this.baseURL()}/reservation`, {
      selectedHour: selectedHour,
      adminId: adminId
    })
  }

  makeReservationBetweenPeriod(startDate: string, endDate: string, selectedHour: ReservedHours, adminId: number) {
    return this.http.post(`${this.baseURL()}/reservationBetweenPeriod`, {
      startDate: startDate,
      endDate: endDate,
      selectedHour: selectedHour,
      adminId: adminId
    })
  }

  makeReservationByRepetitiveDates(startDate: string, endDate: string, selectedDay: "MONDAY" | "TUESDAY" | "WEDNESDAY" | "THURSDAY" | "FRIDAY" | "SATURDAY" | "SUNDAY", selectedHour: ReservedHours, adminId: number) {
    return this.http.post(`${this.baseURL()}/reservationRepetitive`, {
      startDate: startDate,
      endDate: endDate,
      selectedDay: selectedDay,
      repetitiveHour: selectedHour,
      adminId: adminId
    })
  }

  //terem bezaras
  closeRoomForADay(dateText: string, closeType: "holiday" | "full" | "other") {
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

  getReservedDateByDate(selectedDateText: string) {
    return this.http.get(`${this.baseURL()}/reservedDate?selectedDate=${selectedDateText}`)
  }

  //Foglalasok visszaszerzese az admin foglalashoz
  checkReservationForSimple(dateText: string, startHour: number, endHour: number): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(`${this.baseURL()}/reservationCheck?dateText=${dateText}&startHour=${startHour}&endHour=${endHour}`)
  }

  getReservationsForAdminIntervallum(startDateText: string, endDateText: string, startHour: number, endHour: number): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(`${this.baseURL()}/intervallumCheck`)
  }

  checkReservationForRepetitive(startDateText: string, endDateText: string, selectedDays: any, startHour: number, endHour: number): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(`${this.baseURL()}/repetitiveCheck`)
  }
  //Foglalasok visszaszerzese repetitive zarashoz
  repetitiveCloseCheck(startDateText: string, endDateText: string, selectedDays: string[]): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(`${this.baseURL()}/repetitiveCloseCheck`)
  }

  //Adminok kezelese:
  makeAdmin(adminDetails: AdminDetails) {
    return this.http.post(`${this.baseURL()}/makeAdmin/${this.selectedUserIdForAdmin}`, adminDetails)
  }

  getAllAdmin(): Observable<Users[]> {
    return this.http.get<Users[]>(`${this.baseURL()}`)
  }

  updateAdmin(updatedDetails: AdminDetails) {
    return this.http.put(`${this.baseURL()}/updateAdmin`, updatedDetails)
  }

  deleteAdmin(adminId: number) {
    return this.http.delete(`${this.baseURL()}/deleteAdmin/${adminId}`)
  }
}
