import { ChangeDetectionStrategy, Component, inject, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule, MatLabel } from '@angular/material/input';
import { Router, RouterModule } from '@angular/router';
import { UserService } from '../../services/user-service';

@Component({
  selector: 'app-registration-page',
  imports: [MatFormFieldModule, MatInputModule, MatLabel, ReactiveFormsModule, MatButtonModule, RouterModule],
  templateUrl: './registration-page.html',
  styleUrl: './registration-page.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class RegistrationPage {
  private userService = inject(UserService)
  private router = inject(Router)

  isShowPassword = signal<Boolean>(false)
  isShowPasswordAgain = signal<Boolean>(false)

  form = new FormGroup({
    username: new FormControl("", [Validators.required]),
    email: new FormControl("", [Validators.required, Validators.email]),
    password: new FormControl("", [Validators.required, Validators.minLength(8), Validators.maxLength(16)]),
    passwordAgain: new FormControl("", [Validators.required])
  })

  register() {
    this.userService.register({ username: this.form.controls["username"].value!, email: this.form.controls["email"].value!, password: this.form.controls["password"].value!, pfpPath: "", }).subscribe({
      next: response => console.log(response),
      error: error => console.log(error.message),
      complete: () => this.router.navigate(["login"])
    })
  }

  showPassword(event: MouseEvent) {
    this.isShowPassword.update(old => !old)
    event.stopPropagation();

    console.log(this.form.invalid)
  }

  showPasswordAgain(event: MouseEvent){
    this.isShowPasswordAgain.update(old => !old)
    event.stopPropagation();
  }
}
