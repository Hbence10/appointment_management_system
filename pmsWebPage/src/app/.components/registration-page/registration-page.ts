import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule, MatLabel } from '@angular/material/input';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-registration-page',
  imports: [MatFormFieldModule, MatInputModule, MatLabel, ReactiveFormsModule, MatButtonModule, RouterModule],
  templateUrl: './registration-page.html',
  styleUrl: './registration-page.scss'
})
export class RegistrationPage {
  form = new FormGroup({
    username: new FormControl("username", [Validators.required]),
    email: new FormControl("email", [Validators.required, Validators.email]),
    password: new FormControl("password", [Validators.required]),
    passwordAgain: new FormControl("passwordAgain", [Validators.required])
  })
  //Ide kell majd 2 sajat validator --> jelszo validator, es hogy a passwordAgain egynelo a passworddal

}
