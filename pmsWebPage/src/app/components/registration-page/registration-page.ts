import { Component, inject } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule, MatLabel } from '@angular/material/input';
import { Router, RouterModule } from '@angular/router';
import { UserService } from '../../services/user-service';

function passwordValidator(control: AbstractControl){
  const specialCharacters = []


  if(String(control.value).length == 8){

  }

  return {invalidPassword: true}
}

@Component({
  selector: 'app-registration-page',
  imports: [MatFormFieldModule, MatInputModule, MatLabel, ReactiveFormsModule, MatButtonModule, RouterModule],
  templateUrl: './registration-page.html',
  styleUrl: './registration-page.scss'
})
export class RegistrationPage {
  private userService = inject(UserService)
  private router = inject(Router)

  form = new FormGroup({
    username: new FormControl("", [Validators.required]),
    email: new FormControl("", [Validators.required, Validators.email]),
    password: new FormControl("", [Validators.required, Validators.minLength(8), Validators.maxLength(8), passwordValidator]),
    passwordAgain: new FormControl("", [Validators.required])
  })
  //Ide kell majd 2 sajat validator --> jelszo validator, es hogy a passwordAgain egynelo a passworddal


  register() {
    this.userService.register({ username: this.form.controls["username"].value!, email: this.form.controls["email"].value!, password: this.form.controls["password"].value!, pfpPath: "", }).subscribe({
      next: response => console.log(response),
      complete: () => this.router.navigate(["login"])
    })
  }
}
