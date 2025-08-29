import { Component, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { DeviceService } from '../../services/device-service';
import { DeviceCategory } from '../../models/deviceCategory.model';

@Component({
  selector: 'app-equipments',
  imports: [],
  templateUrl: './equipments.html',
  styleUrl: './equipments.scss'
})
export class Equipments implements OnInit{
  private deviceService = inject(DeviceService)
  private destroyRef = inject(DestroyRef)

  deviceCategoryList = signal<DeviceCategory[]>([])

  ngOnInit(): void {
    const subscription = this.deviceService.getAllDevicesByCategories().subscribe({
      next: response => this.deviceCategoryList.set(response),
      complete: () => console.log(this.deviceCategoryList())
    })

    this.destroyRef.onDestroy(() => {
      console.log("destroyed!!! equipmentsComponent")
    })
  }
}
