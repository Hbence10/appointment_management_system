import { ChangeDetectionStrategy, Component, inject, OnInit, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckbox } from '@angular/material/checkbox';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { Router, RouterModule } from '@angular/router';
import { UserService } from '../../services/user-service';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-login-page',
  imports: [RouterModule, MatInputModule, MatFormFieldModule, MatButtonModule, MatCheckbox, MatIconModule, ReactiveFormsModule],
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
      username: new FormControl("securityTest2", [Validators.required]),
      password: new FormControl("test5.Asd", [Validators.required])
    })
  }

  login() {
    console.log(this.loginForm.controls["username"].value!.trim()!)
    this.userService.login(this.loginForm.controls["username"].value!.trim()!, this.loginForm.controls["password"].value!.trim()!).subscribe({
      next: response => {
        this.userService.user.set(response.body)
        console.log(response)
        this.token = response.headers.headers.get("authorization")[0]
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
      this.cookieService.set("pmsJwtToken", this.token)
      this.cookieService.set("pmsUserD", JSON.stringify(this.userService.user()))
    } else {
      sessionStorage.setItem("pmsJwtToken", this.token)
      sessionStorage.setItem("pmsUserD", JSON.stringify(this.userService.user()))
    }
  }
}
