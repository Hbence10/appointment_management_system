import { ChangeDetectionStrategy, Component, inject, OnInit, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
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
export class LoginPage implements OnInit {
  private userService = inject(UserService)
  private router = inject(Router)

  isShowPassword = signal<Boolean>(false)
  isError = signal<Boolean>(false)
  isRemember = signal<Boolean>(false)

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
        this.userService.user.set(response.body)
        console.log(response)
      },
      error: error => { this.isError.set(true) },
      complete: () => {
        if (this.isRemember()) {
        }
        this.router.navigate([""])
      }
    })
  }

  showPassword(event: MouseEvent) {
    this.isShowPassword.update(old => !old)
    event.stopPropagation();
  }

}
