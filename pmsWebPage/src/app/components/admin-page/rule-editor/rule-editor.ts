import { ChangeDetectionStrategy, Component, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { Rule } from '../../../models/rule.model';
import { OtherService } from '../../../services/other-service';
<<<<<<< HEAD
import { AngularEditorConfig, AngularEditorModule } from '@kolkov/angular-editor';
import { FormsModule } from '@angular/forms';
import { QuillModule } from 'ngx-quill'

@Component({
  selector: 'app-rule-editor',
  imports: [FormsModule, QuillModule],
=======

@Component({
  selector: 'app-rule-editor',
  imports: [],
>>>>>>> parent of 650db57 (szövegszerkesztő hozzáadása)
  templateUrl: './rule-editor.html',
  styleUrl: './rule-editor.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})

export class RuleEditor implements OnInit {
  private destroyRef = inject(DestroyRef)
  otherStuffService = inject(OtherService)
  rule = signal<Rule | null>(null)
<<<<<<< HEAD
  // config: AngularEditorConfig = {
  //   editable: true,
  //   spellcheck: true,
  //   minHeight: '20rem',
  //   maxHeight: '20rem',
  //   placeholder: 'Enter text here...',
  //   translate: 'no',
  //   sanitize: true,
  //   toolbarPosition: 'top',
  //   defaultFontName: 'Arial',

  //   customClasses: [
  //     {
  //       name: 'quote',
  //       class: 'quote',
  //     },
  //     {
  //       name: 'redText',
  //       class: 'redText'
  //     },
  //     {
  //       name: 'titleText',
  //       class: 'titleText',
  //       tag: 'h1',
  //     },
  //   ]
  // }
  text: string = "<div><span><b>Szab&#225;lyzat</b></span></div><div><br></div><div>dasdsa<b>da</b></div>"
=======
  ruleText: string = ""
  selectedHeadlineType: string = ""
>>>>>>> parent of 650db57 (szövegszerkesztő hozzáadása)

  ngOnInit(): void {
    const subscription = this.otherStuffService.getRule().subscribe({
      next: response => {
<<<<<<< HEAD
        this.otherStuffService.rule = Object.assign(new Rule(), response)
      },
      error: error => console.log(error),
      complete: () => {

=======
        this.rule.set(Object.assign(new Rule(), response)),
          this.ruleText = this.rule()?.getText!
>>>>>>> parent of 650db57 (szövegszerkesztő hozzáadása)
      }
    })

    this.destroyRef.onDestroy(() => {
      subscription.unsubscribe()
    })
  }
}
