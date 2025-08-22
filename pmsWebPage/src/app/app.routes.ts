import { HomePage } from './.components/home-page/home-page';
import { Routes } from '@angular/router';

export const routes: Routes = [
  {path: "homePage", component: HomePage, title: "Pécs Music Society - Főoldal"},
  {path: "", pathMatch: "full", redirectTo: "homePage"}
];
