import { Routes } from "@angular/router";
import { RuleReader } from "./components/rule-reader/rule-reader";
import { ReservationFinalize } from "./components/reservation-finalize/reservation-finalize";
import { ReservationForm } from "./components/reservation-form/reservation-form";
import { AppointmentSelector } from "./components/appointment-selector/appointment-selector";

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
