import { Navbar } from './components/navbar/navbar';
import { Footer } from './components/footer/footer';
import { Component, inject, OnInit, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { UserService } from './services/user-service';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Footer, Navbar],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App implements OnInit {
  private userService = inject(UserService)
  private cookieService = inject(CookieService)

  ngOnInit(): void {
    if (sessionStorage.getItem("pmsJwtToken")) {
      this.userService.user.set(JSON.parse(sessionStorage.getItem("pmsUserD")!))
      this.userService.token = sessionStorage.getItem("pmsJwtToken")!
    } else if (this.cookieService.get("pmsJwtToken")) {
      this.userService.user.set(JSON.parse(this.cookieService.get("pmsUserD")!))
      this.userService.token = this.cookieService.get("pmsJwtToken")!
    }

    console.log(this.userService.user())
  }
}
