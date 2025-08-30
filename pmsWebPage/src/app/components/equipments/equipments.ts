import { Component, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { DeviceService } from '../../services/device-service';
import { DeviceCategory } from '../../models/deviceCategory.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-equipments',
  imports: [CommonModule],
  templateUrl: './equipments.html',
  styleUrl: './equipments.scss'
})
export class Equipments implements OnInit {
  private deviceService = inject(DeviceService)
  private destroyRef = inject(DestroyRef)

  deviceCategoryList = signal<DeviceCategory[]>([])
  deviceCategoryListByRows: DeviceCategory[][][] = []

  ngOnInit(): void {
    const subscription = this.deviceService.getAllDevicesByCategories().subscribe({
      next: response => this.deviceCategoryList.set(response),
      complete: () => {
        this.splitCategoryList()
      }
    })

    this.destroyRef.onDestroy(() => {
      subscription.unsubscribe()
    })
  }

  splitCategoryList() {
    for (let i: number = 0; i < this.deviceCategoryList().length; i += 5) {
      const rowList = this.deviceCategoryList().slice(i, i + 5)
      const splittedList: DeviceCategory[][] = []
      for (let j: number = 0; j< rowList.length; j+=2){
        splittedList.push(rowList.slice(j, j+2))
      }
      this.deviceCategoryListByRows.push(splittedList)
    }
  }
}
