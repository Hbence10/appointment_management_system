import { Component, computed, DestroyRef, inject, input, OnChanges, output, signal, SimpleChanges } from '@angular/core';
import { FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { DeviceCategory } from '../../../models/deviceCategory.model';
import { Details } from '../../../models/notEntityModels/details.model';
import { DeviceService } from '../../../services/device-service';

@Component({
  selector: 'app-object-editor',
  imports: [MatFormFieldModule, MatInputModule, FormsModule, ReactiveFormsModule, MatSelectModule],
  templateUrl: './object-editor.html',
  styleUrl: './object-editor.scss'
})
export class ObjectEditor implements OnChanges {
  private deviceService = inject(DeviceService)
  private destroyRef = inject(DestroyRef)
  readonly objectType = input.required<Details>()
  outputFormForPopUpContainer = output<FormGroup>()
  selectedObject = input<any>()

  details = signal<Details | null>(null)
  placeholderText: string[] = []
  labelText: string[] = []
  form = input.required<FormGroup>();
  deviceCategoryList = signal<DeviceCategory[]>([])

  isFirstRowFull = computed<boolean>(() =>
    this.details()!.objectType == 'deviceCategory' || this.details()!.objectType == 'news' || this.details()!.objectType == 'gallery'
  )

  ngOnChanges(changes: SimpleChanges): void {
    this.details.set(this.objectType())
    this.form().reset()
    if (this.details()?.objectType == "device") {
      const subscription = this.deviceService.getAllDevicesByCategories().subscribe({
        next: response => {
          console.log(response)
          response.forEach(element => {
            this.deviceCategoryList.update(old => [...old, new DeviceCategory(element.id, element.name, element.devicesList)])
          })
          console.log(this.deviceCategoryList())
        }
      })

      this.destroyRef.onDestroy(() => {
        subscription.unsubscribe()
      })
    }

    if (this.objectType().objectType == "device") {
      this.form().controls["property1"].setValue(this.selectedObject().name)
      this.form().controls["property2"].setValue(this.selectedObject().amount)
      // this.form.controls["property3"].setValue("")
    } else if (this.objectType().objectType == "deviceCategory") {
      this.form().controls["property1"].setValue(this.selectedObject().name)
    } else if (this.objectType().objectType == "news") {
      this.form().controls["property1"].setValue(this.selectedObject().title)
      this.form().controls["property2"].setValue(this.selectedObject().text)
      // this.form.controls["property3"].setValue("")
    } else if (this.objectType().objectType == "reservationType") {
      this.form().controls["property1"].setValue(this.selectedObject().name)
      this.form().controls["property2"].setValue(this.selectedObject().price)
    } else if (this.objectType().objectType == "gallery") {
      this.form().controls["property1"].setValue(this.selectedObject().name)
    }

    this.placeholderText = this.selectedObject().getPlaceholdersText
    this.labelText = this.selectedObject().getLabelText

    if (this.details()?.objectType == "news" || this.details()?.objectType == "device") {
      this.form().controls["property2"].addValidators(Validators.required)
      this.form().controls["property3"].addValidators(Validators.required)
    } else if (this.details()?.objectType == "reservationType") {
      this.form().controls["property2"].addValidators(Validators.required)
    }
  }

  showFormGroup() {
    this.outputFormForPopUpContainer.emit(this.form())
  }

  uploadFile() {

  }
}
