import { ChangeDetectionStrategy, Component, DestroyRef, inject, input, OnInit, output, signal } from '@angular/core';
import { Rule } from '../../../models/rule.model';
import { OtherService } from '../../../services/other-service';
import { FormsModule } from '@angular/forms';
import { QuillModule } from 'ngx-quill'
import { NgxEditorComponent, NgxEditorMenuComponent, Editor } from 'ngx-editor';
import { toHTML } from 'ngx-editor';
@Component({
  selector: 'app-rule-editor',
  imports: [NgxEditorComponent, NgxEditorMenuComponent, FormsModule],
  templateUrl: './rule-editor.html',
  styleUrl: './rule-editor.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})

export class RuleEditor implements OnInit {
  private destroyRef = inject(DestroyRef)
  otherStuffService = inject(OtherService)
  rule = input.required<Rule>()
  editor!: Editor
  typing = output()

  ngOnInit(): void {
    this.editor = new Editor();
    console.log(this.rule)
  }

  typeRule() {
    console.log(this.editor)
  }
}
