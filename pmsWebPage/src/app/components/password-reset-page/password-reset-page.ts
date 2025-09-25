import { MatFormFieldModule } from '@angular/material/form-field';
import { Component, inject, OnInit } from '@angular/core';
import { MatInputModule } from '@angular/material/input';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { AbstractControl, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { UserService } from '../../services/user-service';
import { Router, RouterModule } from '@angular/router';

//
function validatePassword(control: AbstractControl): { [key: string]: any } | null {
  const password: string = control.value

  const specialCharacters: string = "!@#$%^&*()-_=+[]{};:,.?/"
  const numberTexts: string = "1234567890"
  const checkerList: boolean[] = [false, false, false, false]

  for (let i: number = 0; i < password.length; i++) {
    if (specialCharacters.includes(password[i])) {
      checkerList[0] = true
    } else if (numberTexts.includes(password[i])) {
      checkerList[1] = true
    } else if (password[i] === password[i].toUpperCase()) {
      checkerList[2] = true
    } else if (password[i] === password[i].toLowerCase()) {
      checkerList[3] = true
    }
  }

  if (!checkerList.includes(false)) {
    return null
  } else {
    return { invalid: false }
  }
}
@Component({
  selector: 'app-password-reset-page',
  imports: [MatFormFieldModule, MatInputModule, CommonModule, MatButtonModule, RouterModule, ReactiveFormsModule],
  templateUrl: './password-reset-page.html',
  styleUrl: './password-reset-page.scss'
})
export class PasswordResetPage implements OnInit {
  private userService = inject(UserService)
  private router = inject(Router)
  vCode: string | null = null
  form!: FormGroup

  samePasswordValidator = (control: AbstractControl): { [key: string]: any } | null => {
    let originalPassword = this.form.controls["password"].value
    if (control.value === originalPassword) {
      return null
    } else {
      return { invalid: false }
    }
  }

  ngOnInit(): void {
    this.form = new FormGroup({
      email: new FormControl("", [Validators.required, Validators.email]),
      verificationCode: new FormControl("", [Validators.required]),
      password: new FormControl("", [Validators.required, validatePassword]),
      passwordAgain: new FormControl("", [Validators.required])
    })

    this.form.controls["passwordAgain"].addValidators(this.samePasswordValidator)
  }

  sendCode() {
    this.userService.getVerificationCode(this.form.controls["email"].value.trim()).subscribe({
      next: response => {
        console.log(response)
        this.vCode = response.vCode
      },
      error: error => {
        console.log(error)
      },
      complete: () => {
        setTimeout(() => {
          this.vCode = ""
        }, 300000)
      }
    })
  }

  sendReset() {
    this.userService.changePassword(this.form.controls["email"].value, this.form.controls["password"].value).subscribe({
      next: response => {
        console.log(response)
      },
      error: error => {
        console.log(error)
      },
      complete: () => {
        this.router.navigate(["/login"])
      }
    })
  }
}
