import { MatFormFieldModule } from '@angular/material/form-field';
import { Component, inject, OnInit } from '@angular/core';
import { MatInputModule } from '@angular/material/input';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { AbstractControl, FormControl, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../../services/user-service';
import { RouterModule } from '@angular/router';

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
  imports: [MatFormFieldModule, MatInputModule, CommonModule, MatButtonModule, RouterModule],
  templateUrl: './password-reset-page.html',
  styleUrl: './password-reset-page.scss'
})
export class PasswordResetPage implements OnInit {
  private userService = inject(UserService)
  vCode: string = "asd"
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
      emailAddress: new FormControl("", [Validators.required, Validators.email]),
      verificationCode: new FormControl("", [Validators.required]),
      password: new FormControl("", [Validators.required, validatePassword]),
      passwordAgain: new FormControl("", [Validators.required])
    })

    this.form.controls["passwordAgain"].addValidators(this.samePasswordValidator)
  }
}
