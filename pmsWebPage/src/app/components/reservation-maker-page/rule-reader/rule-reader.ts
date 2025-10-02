import { Component, DestroyRef, inject, input, OnInit, output, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { Rule } from '../../../models/rule.model';
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
  private reservationService = inject(ReservationService)

  rule = signal<Rule | null>(null)
  continueAble = signal<boolean>(false)
  parentComponentInput = input<"reservationMaker" | "registerPage">("reservationMaker")
  ruleIsAccepted = output()
  nextStep = output()


  ngOnInit(): void {
    const subscription = this.otherService.getRule().subscribe({
      next: response => this.rule.set(Object.assign(new Rule(), response)),
    });

    this.destroyRef.onDestroy(() => {
      subscription.unsubscribe()
    });
  }

  acceptRule() {
    if (this.parentComponentInput() == "registerPage") {
      this.ruleIsAccepted.emit()
    } else {
      this.reservationService.progressBarSteps[3] = true
      this.nextStep.emit()
    }
  }

  readRule(event: any) {
    if (event.target.offsetHeight + Math.round(event.target.scrollTop) == event.target.scrollHeight) {
      this.continueAble.set(true)
    }
  }
}
