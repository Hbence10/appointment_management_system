import { Component, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { Rule } from '../../../models/rule.model';
import { OtherService } from '../../../services/other-service';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { CommonModule } from '@angular/common';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-rule-editor',
  imports: [MatFormFieldModule, MatInputModule, MatSelectModule, CommonModule, FormsModule],
  templateUrl: './rule-editor.html',
  styleUrl: './rule-editor.scss'
})

export class RuleEditor implements OnInit {
  private destroyRef = inject(DestroyRef)
  private otherStuffService = inject(OtherService)
  rule = signal<Rule | null>(null)
  ruleText: string = ""

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

  asd(){
    console.log(this.ruleText)
  }
}
