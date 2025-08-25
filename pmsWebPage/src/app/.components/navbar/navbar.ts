import { UserService } from './../../.services/user-service';
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
  userService = inject(UserService)
  router = inject(Router)

  userNavigation(){
    if(this.userService.user() == null){
      this.router.navigateByUrl("login")
    } else {
      this.router.navigateByUrl("profilePage")
    }
  }
}
