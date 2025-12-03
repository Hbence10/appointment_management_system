import { Component, inject, OnInit } from '@angular/core';
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
export class Navbar implements OnInit{
  userService = inject(UserService)
  router = inject(Router)
  selectedTheme!: string;

  ngOnInit(): void {
    this.selectedTheme = localStorage.getItem("theme") != null ? localStorage.getItem("theme")! : 'light'
    console.log(this.selectedTheme)
  }

  selectTheme(newTheme: 'dark' | 'light') {
    this.selectedTheme = newTheme
  }
}
