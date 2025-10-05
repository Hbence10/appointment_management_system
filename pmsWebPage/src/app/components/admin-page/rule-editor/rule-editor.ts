import { ChangeDetectionStrategy, Component, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { Rule } from '../../../models/rule.model';
import { OtherService } from '../../../services/other-service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-rule-editor',
  imports: [MatFormFieldModule, MatInputModule, MatSelectModule, FormsModule],
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

  addStyleToSelectedText(wantedStyle: string, container: HTMLDivElement) {
    if (window.getSelection()) {
      let selectedText = window.getSelection()
      let range = selectedText?.getRangeAt(0)
      let selectionContent = range?.extractContents()!

      // container.appendChild(selectionContent)
      console.log(container)
      console.log(selectionContent)
    }
  }

}
