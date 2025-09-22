import { Component, input } from '@angular/core';

@Component({
  selector: 'app-object-editor',
  imports: [],
  templateUrl: './object-editor.html',
  styleUrl: './object-editor.scss'
})
export class ObjectEditor {
  selectedObject = input()
  objectType = input.required<string>()
}
