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
  selectedTheme!: string;

  ngOnInit(): void {
    this.selectedTheme = localStorage.getItem("theme") != null ? localStorage.getItem("theme")! : 'light'
    console.log(this.selectedTheme)
  }

  selectTheme(newTheme: 'dark' | 'light') {
    this.selectedTheme = newTheme
  }
}
