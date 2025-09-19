import { AfterContentInit, AfterViewInit, Component, DestroyRef, ElementRef, inject, input, OnInit, signal, ViewChild, viewChild } from '@angular/core';
import { Router } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { OtherService } from '../../../services/other-service';
import { ReservationService } from '../../../services/reservation-service';


@Component({
  selector: 'app-rule-reader',
  imports: [MatButtonModule],
  templateUrl: './rule-reader.html',
  styleUrl: './rule-reader.scss'
})
export class RuleReader implements OnInit {
  private otherService = inject(OtherService)
  private destroyRef = inject(DestroyRef)
  private router = inject(Router)
  private reservationService = inject(ReservationService)

  rule = signal<{ id: number, text: string, lastEditAt: Date }>({ id: -1, text: "", lastEditAt: new Date() })
  continueAble = signal<boolean>(false)

  ngOnInit(): void {
    const subscription = this.otherService.getRule().subscribe({
      next: response => this.rule.set(response),
    });

    this.destroyRef.onDestroy(() => {
      subscription.unsubscribe()
    });
  }

  acceptRule() {
    this.reservationService.progressBarSteps[3] = true
    this.router.navigate(["makeReservation/reservationFinalize"])
  }

  readRule(event: any) {
    if (event.target.offsetHeight + event.target.scrollTop >= event.target.scrollHeight) {
      this.continueAble.set(true)
    }
  }


}
