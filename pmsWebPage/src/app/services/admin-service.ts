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
  makeAdminReservation(adminId: number, startHour: number, endHour: number, dateText: string) {
    return this.http.post(`${this.baseURL()}/reservation`, {
      adminId: adminId,
      startHour: startHour,
      endHour: endHour,
      dateText: dateText
    })
  }

  makeReservationBetweenPeriod(startDateText: string, endDateText: string, startHour: number, endHour: number, adminId: number) {
    return this.http.post(`${this.baseURL()}/reservationBetweenPeriod`, {
      startDateText: startDateText,
      endDateText: endDateText,
      startHour: startHour,
      endHour: endHour,
      adminId: adminId
    })
  }

  makeReservationByRepetitiveDates(startDateText: string, endDateText: string, selectedDay: any, startHour: number, endHour: number, adminId: number) {
    return this.http.post(`${this.baseURL()}/reservationRepetitive`, {
      startDateText: startDateText,
      endDateText: endDateText,
      selectedDay: selectedDay,
      startHour: startHour,
      endHour: endHour,
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

  closeByRepetitiveDates(startDate: string, endDate: string, closeType: "holiday" | "full" | "other", selectedDay: string[]) {
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
    return this.http.get<Reservation[]>(`${this.baseURL()}/intervallumCheck?startDateText=${startDateText}&endDateText=${endDateText}&startHour=${startHour}&endHour=${endHour}`)
  }

  checkReservationForRepetitive(startDateText: string, endDateText: string, selectedDays: string, startHour: number, endHour: number): Observable<Reservation[]> {
    console.log(selectedDays)
    return this.http.get<Reservation[]>(`${this.baseURL()}/repetitiveCheck?startDateText=${startDateText}&endDateText=${endDateText}&selectedDays=${selectedDays}&startHour=${startHour}&endHour=${endHour}`)
  }

  //Foglalasok visszaszerzese zarashoz
  fullDayCheck(dateText: string): Observable<Reservation[]>{
    return this.http.get<Reservation[]>(`http://localhost:8080/reservation/date/${dateText}`)
  }

  intervallumCheck(startDateText: string, endDateText: string): Observable<Reservation[]>{
    return this.http.get<Reservation[]>(`${this.baseURL()}/intervallumCloseCheck?startDateText=${startDateText}&endDateText=${endDateText}`)
  }

  repetitiveCloseCheck(startDateText: string, endDateText: string, selectedDays: string): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(`${this.baseURL()}/repetitiveCloseCheck?startDateText=${startDateText}&endDateText=${endDateText}&selectedDays=${selectedDays}`)
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
