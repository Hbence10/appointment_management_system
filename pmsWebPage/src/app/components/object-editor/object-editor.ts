import { Component, input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-object-editor',
  imports: [],
  templateUrl: './object-editor.html',
  styleUrl: './object-editor.scss'
})
export class ObjectEditor implements OnInit{
  form!: FormGroup;

  selectedObject = input()
  objectType = input.required<string>()

  ngOnInit(): void {
    this.form = new FormGroup({})
  }
}
