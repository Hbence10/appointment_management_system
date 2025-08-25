import { MainService } from './../../.services/main-service';
import { Component, inject } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-navbar',
  imports: [RouterModule, MatIconModule],
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
