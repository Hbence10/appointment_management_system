import { ChangeDetectionStrategy, Component, computed, DestroyRef, inject, input, OnInit, signal } from '@angular/core';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
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
import { ReservationService } from '../../../services/reservation-service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-object-editor',
  imports: [MatFormFieldModule, MatInputModule, ReactiveFormsModule, MatSelectModule, CommonModule],
  templateUrl: './object-editor.html',
  styleUrl: './object-editor.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})

export class ObjectEditor implements OnInit {
  private deviceService = inject(DeviceService)
  private destroyRef = inject(DestroyRef)
  private reservationService = inject(ReservationService)
  objectType = input.required<Details>()

  selectedObject = input.required<DevicesCategory | Device | News | ReservationType | Gallery | null>()

  details = signal<Details | null>(null)
  placeholderText: string[] = []
  labelText: string[] = []
  form!: FormGroup
  deviceCategoryList = signal<DevicesCategory[]>([])

  isFirstRowFull = computed<boolean>(() =>
    this.details()!.objectType == 'deviceCategory' || this.details()!.objectType == 'news' || this.details()!.objectType == 'gallery'
  )

  selectedDeviceCategory?: number;

  ngOnInit(): void {
    this.details.set(this.objectType())
    this.form = this.reservationService.form

    if (this.selectedObject() instanceof Device) {
      const subscription = this.deviceService.getAllDevicesByCategories().subscribe({
        next: response => {
          this.deviceCategoryList.set(response.map(element => Object.assign(new DevicesCategory(), element)))
          console.log(this.deviceCategoryList())
        },
        complete: () => {
          this.selectedDeviceCategory = this.details()?.deviceCategory.getId!
          console.log(this.selectedDeviceCategory)
        }
      })

      this.destroyRef.onDestroy(() => {
        subscription.unsubscribe()
      })
    }

    this.placeholderText = this.selectedObject()!.getPlaceholdersText
    this.labelText = this.selectedObject()!.getLabelText
  }
}
