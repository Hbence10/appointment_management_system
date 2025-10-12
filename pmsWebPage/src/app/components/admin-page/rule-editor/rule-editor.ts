import { ChangeDetectionStrategy, Component, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { Rule } from '../../../models/rule.model';
import { OtherService } from '../../../services/other-service';

@Component({
  selector: 'app-rule-editor',
  imports: [],
  templateUrl: './rule-editor.html',
  styleUrl: './rule-editor.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})

export class RuleEditor implements OnInit {
  private destroyRef = inject(DestroyRef)
  private otherStuffService = inject(OtherService)
  rule = signal<Rule | null>(null)
  ruleText: string = ""
  selectedHeadlineType: string = ""

  ngOnInit(): void {
    const subscription = this.otherStuffService.getRule().subscribe({
      next: response => {
        this.rule.set(Object.assign(new Rule(), response)),
          this.ruleText = this.rule()?.getText!
      }
    })

    this.destroyRef.onDestroy(() => {
      subscription.unsubscribe()
    })
  }
}
