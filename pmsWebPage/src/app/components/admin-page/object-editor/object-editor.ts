import { ChangeDetectionStrategy, Component, computed, DestroyRef, inject, input, OnInit, signal } from '@angular/core';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatError, MatFormFieldModule } from '@angular/material/form-field';
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

import { MatAnchor } from "@angular/material/button";
import { Users } from '../../../models/user.model';
import { AdminService } from '../../../services/admin-service';
import { NewsService } from '../../../services/news-service';
import { GalleryService } from '../../../services/gallery-service';

@Component({
  selector: 'app-object-editor',
  imports: [MatError, MatFormFieldModule, MatInputModule, ReactiveFormsModule, MatSelectModule, MatAnchor],
  templateUrl: './object-editor.html',
  styleUrl: './object-editor.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})

export class ObjectEditor implements OnInit {
  private deviceService = inject(DeviceService)
  adminService = inject(AdminService)
  private destroyRef = inject(DestroyRef)
  private newsService = inject(NewsService)
  reservationService = inject(ReservationService)
  galleryService = inject(GalleryService)
  objectType = input.required<Details>()

  selectedObject = input.required<DevicesCategory | Device | News | ReservationType | Gallery | Users | null>()

  details = signal<Details | null>(null)
  placeholderText: string[] = []
  labelText: string[] = []
  form!: FormGroup
  deviceCategoryList = signal<DevicesCategory[]>([])
  shorterUserList = signal<{ id: number, username: string }[]>([])


  selectedPlacement: number = 0

  isFirstRowFull = computed<boolean>(() =>
    this.details()!.objectType == 'deviceCategory' || this.details()!.objectType == 'news' || this.details()!.objectType == 'gallery'
  )

  selectedUserId: number = 1
  selectedDeviceCategoryId: number = 0;

  ngOnInit(): void {
    this.details.set(this.objectType())
    this.form = this.adminService.form

    if (this.selectedObject() instanceof Device) {
      const subscription = this.deviceService.getAllDevicesByCategories().subscribe({
        next: response => {
          this.deviceCategoryList.set(response.map(element => Object.assign(new DevicesCategory(), element)))
        },
        complete: () => {
          this.deviceService.selectedCategory = this.deviceCategoryList()[+this.form.controls["property3"].value - 1]
          this.selectedDeviceCategoryId = +this.form.controls["property3"].value - 1
        }
      })

      this.destroyRef.onDestroy(() => {
        subscription.unsubscribe()
      })
    } else if (this.selectedObject() instanceof Users) {
      this.adminService.getShortUsersList().subscribe({
        next: responseList => this.shorterUserList.set(responseList)
      })
    } else if (this.selectedObject() instanceof Gallery) {
      if (this.galleryService.galleryImages.length == 0) {
        this.galleryService.getAllGalleryImages().subscribe({
          next: response => {
            this.galleryService.galleryImages = response.map(galleryImage => Object.assign(new Gallery(), galleryImage))
            this.checkGalleryObjectPlacement()
          }
        })
      } else {
        this.checkGalleryObjectPlacement()
      }
    }

    this.placeholderText = this.selectedObject()!.getPlaceholdersText
    this.labelText = this.selectedObject()!.getLabelText
  }

  selectCategory() {
    this.deviceService.selectedCategory = this.deviceCategoryList()[this.selectedDeviceCategoryId]
  }

  selectFile(event: any, typeOfFileObject: "news" | "gallery") {
    const selectedFile: File = event.target.files[0];
    if (typeOfFileObject == "news") {
      this.newsService.selectedBannerImg = selectedFile
    } else if (typeOfFileObject == "gallery") {
      this.galleryService.selectedImageForEdit = selectedFile
      this.adminService.form.controls["property2"].setValue("fileSelected")
    }

  }

  checkGalleryObjectPlacement() {
    if (this.selectedObject()?.getId == null) {
      this.selectedPlacement = this.galleryService.galleryImages.length + 1
    } else {
      this.selectedPlacement = (this.selectedObject() as Gallery).getPlacement
    }
  }
}
