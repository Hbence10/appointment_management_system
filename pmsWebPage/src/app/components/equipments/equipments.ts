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
  deviceCategoryListByRows = signal<DeviceCategory[][]>([])

  ngOnInit(): void {
    const subscription = this.deviceService.getAllDevicesByCategories().subscribe({
      next: response => {
        this.deviceCategoryList.set(response)
        console.log(response)
      },
      complete: () => {
        this.splitCategoryList()
      }
    })

    this.destroyRef.onDestroy(() => {
      subscription.unsubscribe()
    })
  }

  splitCategoryList() {
    for(let i: number = 0; i < this.deviceCategoryList().length; i+=5){
      const rowList: DeviceCategory[] = []
      for(let j: number = i; j < i+5; j++){
        if(this.deviceCategoryList()[j] != undefined){
          rowList.push(this.deviceCategoryList()[j])
        }
      }
      this.deviceCategoryListByRows.update(old => [...old, rowList])
    }

    console.log(this.deviceCategoryListByRows())
  }
}
