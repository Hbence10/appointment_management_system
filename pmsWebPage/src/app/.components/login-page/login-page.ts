import { UserService } from './../../.services/user-service';
import { ChangeDetectionStrategy, Component, inject, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButton, MatButtonModule } from '@angular/material/button';
import { MatCheckbox } from '@angular/material/checkbox';
import { Router, RouterModule } from '@angular/router';
import { MatIcon, MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-login-page',
  imports: [RouterModule, ReactiveFormsModule, MatInputModule, MatFormFieldModule, MatButtonModule, MatCheckbox, MatIconModule],
  templateUrl: './login-page.html',
  styleUrl: './login-page.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class LoginPage {
  private useService = inject(UserService)
  private router = inject(Router)
  isShowPassword = signal<boolean>(true)

  loginForm = new FormGroup({
    username: new FormControl("username", [Validators.required]),
    password: new FormControl("password", [Validators.required])
  })

  login() {

  }

  navigateToRegister() {
    this.router.navigate(["register"])
  }

  showPassword(event: MouseEvent){
    this.isShowPassword.update(old => !old)
    event.stopPropagation();
  }
}
