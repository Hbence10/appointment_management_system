import { Component, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { OtherService } from '../../services/other-service';
type rule = { id: number, text: string, lastEditAt: Date }

@Component({
  selector: 'app-rule-editor',
  imports: [],
  templateUrl: './rule-editor.html',
  styleUrl: './rule-editor.scss'
})

export class RuleEditor implements OnInit {
  private destroyRef = inject(DestroyRef)
  private otherStuffService = inject(OtherService)
  rule!: rule;

  ngOnInit(): void {
    const subscription = this.otherStuffService.getRule().subscribe({
      next: response => this.rule = response
    })

    this.destroyRef.onDestroy(()=>{
      subscription.unsubscribe()
    })
  }

  saveChanges() {
  }
}
