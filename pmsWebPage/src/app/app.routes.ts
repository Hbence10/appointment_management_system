import { CanMatch, CanMatchFn, Routes } from '@angular/router';
import { HomePage } from './components/home-page/home-page';
import { LoginPage } from './components/login-page/login-page';
import { PasswordResetPage } from './components/password-reset-page/password-reset-page';
import { RegistrationPage } from './components/registration-page/registration-page';
import { adminAuthGuard } from './routerGuards/adminAuthGuard';
import { Unauthorized } from './components/unauthorized/unauthorized';
import { userAuthGuard } from './routerGuards/userAuthGuard';
import { NotFound } from './components/not-found/not-found';
import { ReservatinCanceler } from './components/reservatin-canceler/reservatin-canceler';
import { reservationTrackAuthGuard } from './routerGuards/reservationTrackAuthGuard';

export const routes: Routes = [
  { path: "homePage", component: HomePage, title: "Pécs Music Society - Főoldal" },
  { path: "", pathMatch: "full", redirectTo: "homePage" },
  { path: "login", component: LoginPage, title: "Pécs Music Society - Bejelentkezés" },
  { path: "register", component: RegistrationPage, title: "Pécs Music Society - Regisztráció" },
  { path: "passwordReset", component: PasswordResetPage, title: "Pécs Music Society - Jelszó emlékesztető" },
  { path: "unauthorized", component: Unauthorized, title: "Pécs Music Society - Nincs hozzá jogosultságod" },
  { path: "reservationCancel", component: ReservatinCanceler, title: "Pécs Music Society - Foglalás lemondása"},

  //Lazy loadinggal betoltott componentek: Idopont foglalo/adminPage, Arlista, Felszereles, Velemenyek, Galleria
  {
    path: "makeReservation",
    loadComponent: () => import("./components/reservation-maker-page/reservation-maker-page").then(mod => mod.ReservationMakerPage),
    title: "Pécs Music Society - Időpont foglalás",
  },

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
    loadComponent: () => import("./components/review/review").then(mod => mod.ReviewPage),
    title: "Pécs Music Society - Vélemények"
  },
  {
    path: "gallery",
    loadComponent: () => import("./components/gallery/gallery").then(mod => mod.GalleryPage),
    title: "Pécs Music Society - Galléria"
  },
  {
    path: "profilePage",
    loadComponent: () => import("./components/profile-page/profile-page").then(mod => mod.ProfilePage),
    title: "Pécs Music Society - Fiókom",
    canMatch: [userAuthGuard]
  },
  {
    path: "adminPage",
    loadComponent: () => import("./components/admin-page/admin-page").then(mod => mod.AdminPage),
    title: "Pécs Music Society - Admin Page",
    canMatch: [adminAuthGuard]
  },

  { path: "**", component: NotFound }
];
