import { Component, computed, DestroyRef, inject, input, OnChanges, output, signal, SimpleChanges } from '@angular/core';
import { FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { Device } from '../../../models/device.model';
import { DevicesCategory } from '../../../models/deviceCategory.model';
import { Gallery } from '../../../models/galleryImage.model';
import { News } from '../../../models/newsDetails.model';
import { Details } from '../../../models/notEntityModels/details.model';
import { ReservationType } from '../../../models/reservationType.model';
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
  selectedObject = input.required<DevicesCategory | Device | News | ReservationType | Gallery | null>()

  details = signal<Details | null>(null)
  placeholderText: string[] = []
  labelText: string[] = []
  form = input.required<FormGroup>();
  deviceCategoryList = signal<DevicesCategory[]>([])

  isFirstRowFull = computed<boolean>(() =>
    this.details()!.objectType == 'deviceCategory' || this.details()!.objectType == 'news' || this.details()!.objectType == 'gallery'
  )

  ngOnChanges(changes: SimpleChanges): void {
    this.details.set(this.objectType())

    if (this.details()?.objectType == "device") {
      const subscription = this.deviceService.getAllDevicesByCategories().subscribe({
        next: response => this.deviceCategoryList.set(response.map(element => Object.assign(new DevicesCategory(), element)))
      })

      this.destroyRef.onDestroy(() => {
        subscription.unsubscribe()
      })
    }

    this.placeholderText = this.selectedObject()!.getPlaceholdersText
    this.labelText = this.selectedObject()!.getLabelText


  }

  showFormGroup() {
    this.outputFormForPopUpContainer.emit(this.form())
  }

  uploadFile() {

  }
}
