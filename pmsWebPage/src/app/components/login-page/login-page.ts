import { ChangeDetectionStrategy, Component, inject, OnInit, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckbox } from '@angular/material/checkbox';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatTooltipModule } from '@angular/material/tooltip';
import { Router, RouterModule } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { Role } from '../../models/role.model';
import { Users } from '../../models/user.model';
import { UserService } from '../../services/user-service';

@Component({
  selector: 'app-login-page',
  imports: [RouterModule, MatTooltipModule, MatInputModule, MatFormFieldModule, MatButtonModule, MatCheckbox, MatIconModule, ReactiveFormsModule],
  templateUrl: './login-page.html',
  styleUrl: './login-page.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class LoginPage implements OnInit {
  private userService = inject(UserService)
  private cookieService = inject(CookieService)
  private router = inject(Router)
  private token: string = ""

  isShowPassword = signal<boolean>(false)
  isError = signal<boolean>(false)
  isRemember = signal<boolean>(false)

  loginForm!: FormGroup

  ngOnInit(): void {
    this.loginForm = new FormGroup({
      username: new FormControl("", [Validators.required]),
      password: new FormControl("", [Validators.required])
    })
  }

  login() {
    this.userService.login(this.loginForm.controls["username"].value!.trim()!, this.loginForm.controls["password"].value!.trim()!).subscribe({
      next: response => {
        let user: Users = Object.assign(new Users(), response.body)
        user.setRole = Object.assign(new Role(), user.getRole)
        this.userService.user.set(user)
        this.token = response.headers.headers.get("authorization")[0]
        console.log(this.token)
      },
      error: error => { this.isError.set(true) },
      complete: () => {
        this.checkIsRemember()
        this.router.navigate([""])
      }
    })
  }

  showPassword(event: MouseEvent) {
    this.isShowPassword.update(old => !old)
    event.stopPropagation();
  }

  checkIsRemember() {
    if (this.isRemember()) {
      this.cookieService.set("pmsJwtToken", this.token, { expires: 30 })
      this.cookieService.set("pmsUserD", JSON.stringify(this.userService.user()), 30)
    } else {
      sessionStorage.setItem("pmsJwtToken", this.token)
      sessionStorage.setItem("pmsUserD", JSON.stringify(this.userService.user()))
    }
  }
}
