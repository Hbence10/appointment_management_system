import { STEPPER_GLOBAL_OPTIONS } from '@angular/cdk/stepper';
import { AfterViewInit, Component, ContentChildren, ElementRef, HostListener, inject, OnInit, ViewChild, viewChildren, ViewChildren } from '@angular/core';
import { MatStepHeader, MatStepper, MatStepperModule } from '@angular/material/stepper';
import { Router, RouterModule } from '@angular/router';
import { Reservation } from '../../models/reservation.model';
import { ReservationService } from '../../services/reservation-service';
import { AppointmentSelector } from './appointment-selector/appointment-selector';
import { ReservationFinalize } from './reservation-finalize/reservation-finalize';
import { ReservationForm } from './reservation-form/reservation-form';
import { RuleReader } from './rule-reader/rule-reader';

@Component({
  selector: 'app-reservation-maker-page',
  imports: [RouterModule, MatStepperModule, AppointmentSelector, ReservationForm, RuleReader, ReservationFinalize],
  templateUrl: './reservation-maker-page.html',
  styleUrl: './reservation-maker-page.scss',
  providers: [
    {
      provide: STEPPER_GLOBAL_OPTIONS,
      useValue: { displayDefaultIndicatorType: false },
    },
  ],
})
export class ReservationMakerPage implements OnInit, AfterViewInit {
  private router = inject(Router)
  private reservationService = inject(ReservationService)
  stepperOrientation: "vertical" | "horizontal" = "horizontal"

  checkList = [true, false, false, false]
  actualSteps = this.router.url

  ngOnInit(): void {
    this.reservationService.baseReservation.set(new Reservation())
  }

  //reszponzivitas
  @ViewChild("stepper") step!: MatStepper;
  // stepHeaders = viewChildren(MatStepHeader)
  @ContentChildren(MatStepHeader) asd!: ElementRef
  ngAfterViewInit(): void {
    console.log(this.asd)

    const startScreenWidth = window.innerWidth;
    if (startScreenWidth > 992) {
      this.stepperOrientation = "horizontal"
    } else if (startScreenWidth <= 992) {
      this.stepperOrientation = "vertical"
    }

    // console.log(this.stepHeaders())
  }

  @HostListener('window:resize', ['$event.target.innerWidth'!])
  onResize(width: number) {
    if (width > 992) {
      this.stepperOrientation = "horizontal"
    } else if (width <= 992) {
      this.stepperOrientation = "vertical"
    }
  }

  nextStep(index: number){
    this.checkList[index] = true
    this.step.next()
    // console.log(this.step.nativeElement)
    // this.step.nativeElement.next()
  }
}
