import { response } from 'express';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ChangeDetectionStrategy, Component, inject, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckbox } from '@angular/material/checkbox';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { Router, RouterModule } from '@angular/router';
import { UserService } from '../../services/user-service';

@Component({
  selector: 'app-login-page',
  imports: [RouterModule, MatInputModule, MatFormFieldModule, MatButtonModule, MatCheckbox, MatIconModule, ReactiveFormsModule],
  templateUrl: './login-page.html',
  styleUrl: './login-page.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class LoginPage {
  private userService = inject(UserService)
  private router = inject(Router)

  isShowPassword = signal<Boolean>(true)
  isError = signal<Boolean>(false)
  isRemember = signal<Boolean>(false)

  loginForm = new FormGroup({
    username: new FormControl("", [Validators.required]),
    password: new FormControl("", [Validators.required])
  })

  login() {
    this.userService.login("testAdmin", "testAdmin").subscribe({
      next: response => {
        console.log(response)
        this.userService.user.set(response.result)
      },

      complete: () => {
        if (this.isRemember()) {
          this.setUserToService()
        }
        this.router.navigate([""] )
      }
    })
  }

  showPassword(event: MouseEvent) {
    this.isShowPassword.update(old => !old)
    event.stopPropagation();
  }

  setUserToService(){
  }
}
