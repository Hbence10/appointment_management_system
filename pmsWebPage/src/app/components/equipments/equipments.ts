import { Component, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { DeviceService } from '../../services/device-service';
import { DevicesCategory } from '../../models/deviceCategory.model';
import { CommonModule } from '@angular/common';
import { Device } from '../../models/device.model';

@Component({
  selector: 'app-equipments',
  imports: [CommonModule],
  templateUrl: './equipments.html',
  styleUrl: './equipments.scss'
})
export class Equipments implements OnInit {
  private deviceService = inject(DeviceService)
  private destroyRef = inject(DestroyRef)

  deviceCategoryList = signal<DevicesCategory[]>([])
  deviceCategoryListByRows = signal<DevicesCategory[][]>([])

  ngOnInit(): void {
    const subscription = this.deviceService.getAllDevicesByCategories().subscribe({
      next: response => {
        response.forEach(element => {
          let deviceCategory = Object.assign(new DevicesCategory(), element)
          let deviceList: Device[] = []
          deviceCategory.getDevicesList.forEach(device => {
            deviceList.push(Object.assign(new Device(), device))
          })
          deviceCategory.setDevicesList = deviceList
          this.deviceCategoryList.update(old => [...old, deviceCategory])
        })
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
      const rowList: DevicesCategory[] = []
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
