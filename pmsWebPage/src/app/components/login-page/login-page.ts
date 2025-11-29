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
import { UserService } from '../../services/user-service';

@Component({
  selector: 'app-login-page',
  imports: [RouterModule, MatTooltipModule, MatInputModule, MatFormFieldModule, MatButtonModule, MatCheckbox, MatIconModule, ReactiveFormsModule],
  templateUrl: './login-page.html',
  styleUrls: ['./login-page.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class LoginPage implements OnInit {
  private userService = inject(UserService)
  private cookieService = inject(CookieService)
  private router = inject(Router)

  isShowPassword = signal<boolean>(false)
  isError = signal<boolean>(false)
  isRemember = signal<boolean>(false)

  loginForm!: FormGroup

  ngOnInit(): void {
    this.loginForm = new FormGroup({
      username: new FormControl("securityTest3", [Validators.required]),
      password: new FormControl("test5.Asd", [Validators.required])
    })
  }

  login() {
    this.userService.login(this.loginForm.controls["username"].value!.trim()!, this.loginForm.controls["password"].value!.trim()!).subscribe({
      next: response => {
        this.userService.setObject(response.body)
        this.cookieService.set("pmsToken", response.headers.headers.get("authorization")[0])

        console.log(this.userService.user()?.getAdminDetails.getId)
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
      this.cookieService.set("pmsJwtToken", this.cookieService.get("pmsToken"))
      this.cookieService.set("pmsUserD", JSON.stringify(this.userService.user()))
    } else {
      sessionStorage.setItem("pmsJwtToken", this.cookieService.get("pmsToken"))
      sessionStorage.setItem("pmsUserD", JSON.stringify(this.userService.user()))
    }
  }
}
