import { MainService } from './../../.services/main-service';
import { Component, inject } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
// import { RouterLink } from "../../../../node_modules/@angular/router/router_module.d";

@Component({
  selector: 'app-navbar',
  imports: [RouterModule],
  templateUrl: './navbar.html',
  styleUrl: './navbar.scss'
})
export class Navbar {
  mainService = inject(MainService)
  router = inject(Router)

  userNavigation(){
    if(this.mainService.user() == null){
      this.router.navigateByUrl("login")
    } else {
      this.router.navigateByUrl("profilePage")
    }
  }
}
