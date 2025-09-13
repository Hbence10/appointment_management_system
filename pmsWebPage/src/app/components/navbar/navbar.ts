import { UserService } from '../../services/user-service';
import { Component, inject, OnInit } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-navbar',
  imports: [RouterModule],
  templateUrl: './navbar.html',
  styleUrl: './navbar.scss',
  animations: [

  ]
})
export class Navbar{
  userService = inject(UserService)
  router = inject(Router)

  userNavigation() {
    if (this.userService.user() == null) {
      this.router.navigateByUrl("login")
    } else {
      this.router.navigateByUrl("profilePage")
    }
  }
}
