import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButton } from '@angular/material/button';
import { MatCheckbox } from '@angular/material/checkbox';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-login-page',
  imports: [ReactiveFormsModule, MatInputModule, MatFormFieldModule, MatButton, MatCheckbox, RouterModule],
  templateUrl: './login-page.html',
  styleUrl: './login-page.scss'
})
export class LoginPage {
  loginForm = new FormGroup({
    username: new FormControl("username", [Validators.required]),
    password: new FormControl("password", [Validators.required])
  })

  login(){

  }
}
