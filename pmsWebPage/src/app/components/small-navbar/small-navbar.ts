import { Component, inject, OnInit, signal } from '@angular/core';
import { UserService } from '../../services/user-service';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-small-navbar',
  imports: [RouterModule],
  templateUrl: './small-navbar.html',
  styleUrl: './small-navbar.scss'
})
export class SmallNavbar{
  userService = inject(UserService)
  router = inject(Router)
  showNavBar = signal<boolean>(false)
}
