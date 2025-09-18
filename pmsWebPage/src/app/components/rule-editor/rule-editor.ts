import { Component, inject } from '@angular/core';
import { OtherService } from '../../services/other-service';

@Component({
  selector: 'app-rule-editor',
  imports: [],
  templateUrl: './rule-editor.html',
  styleUrl: './rule-editor.scss'
})
export class RuleEditor {
  private otherStuffService = inject(OtherService)


  saveChanges(){

  }
}
