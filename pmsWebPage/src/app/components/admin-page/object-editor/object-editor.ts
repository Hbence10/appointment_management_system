import { Component, computed, input, OnChanges, OnInit, output, signal, SimpleChanges } from '@angular/core';
import { FormControl, FormGroup, Validators, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { Details } from '../../../models/notEntityModels/details.model';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';

@Component({
  selector: 'app-object-editor',
  imports: [MatFormFieldModule, MatInputModule, FormsModule, ReactiveFormsModule, MatSelectModule],
  templateUrl: './object-editor.html',
  styleUrl: './object-editor.scss'
})
export class ObjectEditor implements OnChanges {
  readonly objectType = input.required<Details>()
  selectedObject = input<any>()
  details = signal<Details | null>(null)
  outputFormForPopUpContainer = output<FormGroup>()

  isFirstRowFull = computed<boolean>(() =>
    this.details()!.objectType == 'deviceCategory' || this.details()!.objectType == 'news' || this.details()!.objectType == 'gallery'
  )
  placeholderText: string[] = []

  form!: FormGroup;

  ngOnChanges(changes: SimpleChanges): void {
    this.details.set(this.objectType())
    this.form = new FormGroup({
      property1: new FormControl("", [Validators.required]),
      property2: new FormControl("", []),
      property3: new FormControl("", [])
    })

    this.placeholderText = this.selectedObject()?.placeholdersText

    if (this.details()?.objectType == "news" || this.details()?.objectType == "device") {
      this.form.controls["property2"].addValidators(Validators.required)
      this.form.controls["property3"].addValidators(Validators.required)
    } else if (this.details()?.objectType == "reservationType"){
      this.form.controls["property2"].addValidators(Validators.required)
    }
  }

  showFormGrou() {
    this.outputFormForPopUpContainer.emit(this.form)
  }
}
