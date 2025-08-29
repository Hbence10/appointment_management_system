import { PasswordResetPage } from './components/password-reset-page/password-reset-page';
import { AppointmentSelector } from './components/appointment-selector/appointment-selector';
import { RegistrationPage } from './components/registration-page/registration-page';
import { LoginPage } from './components/login-page/login-page';
import { HomePage } from './components/home-page/home-page';
import { Routes } from '@angular/router';
import { ReservationMakerPage } from './components/reservation-maker-page/reservation-maker-page';
import { ReservationForm } from './components/reservation-form/reservation-form';
import { ReservationFinalize } from './components/reservation-finalize/reservation-finalize';

export const routes: Routes = [
  { path: "homePage", component: HomePage, title: "Pécs Music Society - Főoldal" },
  { path: "", pathMatch: "full", redirectTo: "homePage" },
  { path: "login", component: LoginPage, title: "Pécs Music Society - Bejelentkezés" },
  { path: "register", component: RegistrationPage, title: "Pécs Music Society - Regisztráció" },
  { path: "passwordReset", component: PasswordResetPage, title: "Pécs Music Society - Jelszó emlékesztető" },
  {
    path: "makeReservation",
    component: ReservationMakerPage,
    title: "Pécs Music Society - Időpont foglalás",
    children: [
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
      }
    ]
  },

  //Lazy loadinggal betoltott componentek: Idopont foglalo/adminPage, Arlista, Felszereles, Velemenyek, Galleria
  {
    path: "priceList",
    loadComponent: () => import("./components/price-list/price-list").then(mod => mod.PriceList),
    title: "Pécs Music Society - Árlista"
  },
  {
    path: "equipments",
    loadComponent: () => import("./components/equipments/equipments").then(mod => mod.Equipments),
    title: "Pécs Music Society - Felszerelés"
  },
  {
    path: "reviews",
    loadComponent: () => import("./components/review/review").then(mod => mod.Review),
    title: "Pécs Music Society - Vélemények"
  },
  {
    path: "gallery",
    loadComponent: () => import("./components/gallery/gallery").then(mod => mod.Gallery),
    title: "Pécs Music Society - Galléria"
  },
  {
    path: "profilePage",
    loadComponent: () => import("./components/profile-page/profile-page").then(mod => mod.ProfilePage),
    title: "Pécs Music Society - Fiókom"
  },
  {
    path: "adminPage",
    loadComponent: () => import("./components/admin-page/admin-page").then(mod => mod.AdminPage),
    title: "Pécs Music Society - Admin Page"
  }
];
