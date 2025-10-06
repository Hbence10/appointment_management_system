import { ChangeDetectionStrategy, Component, inject, OnInit, signal } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule, MatLabel } from '@angular/material/input';
import { Router, RouterModule } from '@angular/router';
import { UserService } from '../../services/user-service';
import { RuleReader } from '../reservation-maker-page/rule-reader/rule-reader';
import { Users } from '../../models/user.model';

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
  selector: 'app-registration-page',
  imports: [MatFormFieldModule, MatCheckboxModule, MatInputModule, MatLabel, ReactiveFormsModule, MatButtonModule, RouterModule, FormsModule, RuleReader],
  templateUrl: './registration-page.html',
  styleUrl: './registration-page.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})

export class RegistrationPage implements OnInit {
  private userService = inject(UserService)
  private router = inject(Router)

  isShowPassword = signal<Boolean>(false)
  isShowPasswordAgain = signal<Boolean>(false)

  emailErrorMsg = signal<string>("")
  usernameErrorMsg = signal<string>("")
  isRuleShow = signal<boolean>(false)
  ruleAccepted = signal<boolean>(false)

  form!: FormGroup;

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
      username: new FormControl("", [Validators.required]),
      email: new FormControl("", [Validators.required, Validators.email]),
      password: new FormControl("", [Validators.required, Validators.minLength(8), Validators.maxLength(16), validatePassword]),
      passwordAgain: new FormControl("", [Validators.required, validatePassword])
    })

    this.form.controls["passwordAgain"].addValidators(this.samePasswordValidator)
  }

  register() {
    let newUser: Users = new Users(null,this.form.controls["username"].value?.trim()!, this.form.controls["password"].value?.trim()!, this.form.controls["email"].value?.trim()!)
    this.userService.register(newUser).subscribe({
      next: response => console.log(response),
      error: error => {
        console.log(error.error)
        if (error.error == "duplicateEmail") {
          this.emailErrorMsg.set("Ezzel az email címmel már létezik profil.")
        } else if (error.error == "duplicateUsername") {
          this.usernameErrorMsg.set("Ezzel a felhasználónévvel már létezik profil.")
        }
      },
      complete: () => this.router.navigate(["login"])
    })
  }

  showPassword(event: MouseEvent) {
    this.isShowPassword.update(old => !old)
    event.stopPropagation();

    console.log(this.form.invalid)
  }

  showPasswordAgain(event: MouseEvent) {
    this.isShowPasswordAgain.update(old => !old)
    event.stopPropagation();
  }

  showRule() {
    this.isRuleShow.set(true)
  }
}
