import { ChangeDetectionStrategy, Component, DestroyRef, inject, OnInit, output, Signal, signal } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatFormFieldModule, MatLabel } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { Reservation } from '../../../models/reservation.model';
import { ReservationType } from '../../../models/reservationType.model';
import { Users } from '../../../models/user.model';
import { ReservationService } from '../../../services/reservation-service';
import { UserService } from '../../../services/user-service';
import { ReservationStuff } from '../../../services/reservation-stuff';

function validatePhone(control: AbstractControl): { [key: string]: any } | null {
  const phoneServiceCodes: number[] = [30, 20, 70, 50, 31]
  let inputValue: string = control.value + ""

  if (!phoneServiceCodes.includes(Number(inputValue.slice(0, 2)))) {
    return { invalid: true }
  }

  return null
}

@Component({
  selector: 'app-reservation-form',
  imports: [ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatLabel, MatCheckboxModule, MatButtonModule, MatSelectModule],
  templateUrl: './reservation-form.html',
  styleUrl: './reservation-form.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})

export class ReservationForm implements OnInit {
  private reservationService = inject(ReservationService)
  private reservationStuffService = inject(ReservationStuff)
  private destroyRef = inject(DestroyRef)
  private userService = inject(UserService)
  nextStep = output()

  reservationTypes = signal<ReservationType[]>([])
  phoneCodes = signal<{ id: number, countryCode: number, countryName: string }[]>([])
  user: null | Users = null
  selectedReservationType = signal<ReservationType | null>(null)
  baseReservation!: Signal<Reservation>;
  form!: FormGroup;
  selectedPhoneCode = 102

  ngOnInit(): void {
    this.user = this.userService.user()
    this.baseReservation = signal(this.reservationService.baseReservation())

    if (this.baseReservation().getReservationTypeId) {
      this.selectedReservationType.set(this.baseReservation().getReservationTypeId)
    }

    const typeSubscription = this.reservationStuffService.getReservationTypes().subscribe({
      next: responseList => {
        responseList.forEach(response => {
          this.reservationTypes.update(old => [...old, Object.assign(new ReservationType(), response)])
        })
      }
    })

    const phoneSubscription = this.reservationStuffService.getPhoneCodes().subscribe({
      next: response => this.phoneCodes.set(response)
    })

    this.destroyRef.onDestroy(() => {
      typeSubscription.unsubscribe()
      phoneSubscription.unsubscribe()
    })

    this.form = new FormGroup({
      firstName: new FormControl(this.baseReservation().getFirstName, [Validators.required]),
      lastName: new FormControl(this.baseReservation().getLastName, [Validators.required]),
      email: new FormControl(this.baseReservation().getEmail, [Validators.required, Validators.email]),
      phone: new FormControl(this.baseReservation().getPhone, [Validators.required, Validators.minLength(9), Validators.maxLength(9), validatePhone]),
      comment: new FormControl(this.baseReservation().getComment, []),
      reservationType: new FormControl(!this.baseReservation().getReservationTypeId ? "" : this.baseReservation().getReservationTypeId.getName, [Validators.required])
    })
  }

  selectReservationType(reservationType: ReservationType) {
    this.selectedReservationType.set(reservationType)
    this.baseReservation().setReservationTypeId = reservationType
    this.form.controls["reservationType"].setValue(this.selectedReservationType()!.getName)
  }

  continueReservation() {
    this.baseReservation().setFirstName = this.form.controls["firstName"].value
    this.baseReservation().setLastName = this.form.controls["lastName"].value
    this.baseReservation().setEmail = this.form.controls["email"].value
    this.baseReservation().setPhoneCode = this.phoneCodes()[this.selectedPhoneCode - 1]
    this.baseReservation().setPhone = this.form.controls["phone"].value
    this.baseReservation().setComment = this.form.controls["comment"].value

    this.reservationService.progressBarSteps[2] = true
    this.nextStep.emit()
  }


}
