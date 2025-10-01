import { Component, inject } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { UserService } from '../../services/user-service';

@Component({
  selector: 'app-navbar',
  imports: [RouterModule],
  templateUrl: './navbar.html',
  styleUrl: './navbar.scss',
  animations: [

  ]
})
export class Navbar {
  userService = inject(UserService)
  router = inject(Router)
}
