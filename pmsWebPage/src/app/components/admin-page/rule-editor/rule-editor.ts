import { ChangeDetectionStrategy, Component, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { Rule } from '../../../models/rule.model';
import { OtherService } from '../../../services/other-service';
import { AngularEditorConfig, AngularEditorModule } from '@kolkov/angular-editor';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-rule-editor',
  imports: [AngularEditorModule, FormsModule],
  templateUrl: './rule-editor.html',
  styleUrl: './rule-editor.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})

export class RuleEditor implements OnInit {
  private destroyRef = inject(DestroyRef)
  private otherStuffService = inject(OtherService)
  rule = signal<Rule | null>(null)
  config: AngularEditorConfig = {
    editable: true,
    spellcheck: true,
    minHeight: '20rem',
    maxHeight: '20rem',
    placeholder: 'Enter text here...',
    translate: 'no',
    sanitize: true,
    toolbarPosition: 'top',
    defaultFontName: 'Arial',

    customClasses: [
      {
        name: 'quote',
        class: 'quote',
      },
      {
        name: 'redText',
        class: 'redText'
      },
      {
        name: 'titleText',
        class: 'titleText',
        tag: 'h1',
      },
    ]
  }
  text: string = "<div><span><b>Szab&#225;lyzat</b></span></div><div><br></div><div>dasdsa<b>da</b></div>"

  ngOnInit(): void {
    const subscription = this.otherStuffService.getRule().subscribe({
      next: response => {
        this.rule.set(Object.assign(new Rule(), response))
      },
      error: error => console.log(error),
      complete: () => {

      }
    })

    this.destroyRef.onDestroy(() => {
      subscription.unsubscribe()
    })
  }
}
