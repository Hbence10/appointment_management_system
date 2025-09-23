import { Component, computed, input, OnChanges, OnInit, output, signal, SimpleChanges } from '@angular/core';
import { FormControl, FormGroup, Validators, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { Details } from '../../../models/notEntityModels/details.model';
import { MatInputModule } from '@angular/material/input';

@Component({
  selector: 'app-object-editor',
  imports: [MatFormFieldModule, MatInputModule, FormsModule, ReactiveFormsModule],
  templateUrl: './object-editor.html',
  styleUrl: './object-editor.scss'
})
export class ObjectEditor implements OnChanges {
  readonly objectType = input.required<Details>()
  selectedObject = input()
  details = signal<Details | null>(null)
  outputFormForPopUpContainer = output<FormGroup>()

  isFirstRowFull = computed<boolean>(() =>
    this.details()!.objectType == 'deviceCategory' || this.details()!.objectType == 'news' || this.details()!.objectType == 'gallery'
  )

  form!: FormGroup;

  ngOnChanges(changes: SimpleChanges): void {
    this.details.set(this.objectType())
    this.form = new FormGroup({
      property1: new FormControl("", [Validators.required]),
      porperty2: new FormControl("", []),
      property3: new FormControl("", [])
    })
  }

  showFormGrou(){
    this.outputFormForPopUpContainer.emit(this.form)
  }
}
