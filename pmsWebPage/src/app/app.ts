import { Navbar } from './components/navbar/navbar';
import { Footer } from './components/footer/footer';
import { Component, HostListener, inject, OnInit, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { UserService } from './services/user-service';
import { CookieService } from 'ngx-cookie-service';
import { Users } from './models/user.model';
import { Role } from './models/role.model';
import { SmallNavbar } from './components/small-navbar/small-navbar';
import { AdminDetails } from './models/adminDetails.model';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Footer, Navbar, SmallNavbar],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App implements OnInit {
  private userService = inject(UserService)
  private cookieService = inject(CookieService)

  ngOnInit(): void {
    let user: Users = new Users()

    if (sessionStorage.getItem("pmsJwtToken")) {
      user = Object.assign(new Users(), JSON.parse(sessionStorage.getItem("pmsUserD")!))
      this.userService.token = sessionStorage.getItem("pmsJwtToken")!
    } else if (this.cookieService.get("pmsJwtToken")) {
      user = Object.assign(new Users(), JSON.parse(this.cookieService.get("pmsUserD")!))
      this.userService.token = this.cookieService.get("pmsJwtToken")!
    }

    user.setRole = Object.assign(new Role(), user.getRole)
    user.setAdminDetails = Object.assign(new AdminDetails(), user.getAdminDetails)
    if(user.getId != null){
      this.userService.user.set(user)
    }
  }
}
