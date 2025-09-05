import { Routes } from "@angular/router";
import { AppointmentSelector } from "./components/reservation-maker-page/appointment-selector/appointment-selector";
import { ReservationForm } from "./components/reservation-maker-page/reservation-form/reservation-form";
import { ReservationFinalize } from "./components/reservation-maker-page/reservation-finalize/reservation-finalize";
import { RuleReader } from "./components/reservation-maker-page/rule-reader/rule-reader";


export const reservationRoutes: Routes = [
  {
    path: "appointmentSelect",
    component: AppointmentSelector
  },
  {
    path: "",
    pathMatch: "full",
    redirectTo: "appointmentSelect"
  },
  {
    path: "reservationForm",
    component: ReservationForm
  },
  {
    path: "reservationFinalize",
    component: ReservationFinalize
  },
  {
    path: "rule",
    component: RuleReader
  }
]
