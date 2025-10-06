import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { DevicesCategory } from '../models/deviceCategory.model';
import { Observable } from 'rxjs';
import { Device } from '../models/device.model';

@Injectable({
  providedIn: 'root'
})
export class DeviceService {
  private http = inject(HttpClient)
  baseURL = signal<string>("http://localhost:8080/devices")

  //device_category
  getAllDevicesByCategories(): Observable<DevicesCategory[]> {
    return this.http.get<any[]>(`${this.baseURL()}/getAllCategory`)
  }

  addDeviceCategory(newDevicesCategory: DevicesCategory) {
    return this.http.post(`${this.baseURL()}/addCategory`, newDevicesCategory)
  }

  updateDeviceCategory(updatedDeviceCategory: DevicesCategory){
    return this.http.put(`${this.baseURL()}/updateCategory`, updatedDeviceCategory)
  }

  deleteDeviceCategory(id: number){
    return this.http.delete(`${this.baseURL()}/deleteCategory/${id}`)
  }

  //device:
  addDevice(newDevice: Device){
    return this.http.post(`${this.baseURL()}/addDevice`, newDevice)
  }

  updateDevice(updatedDevice: Device){
    return this.http.put(`${this.baseURL()}/update`, updatedDevice)
  }

  deleteDevice(id: number){
    return this.http.delete(`${this.baseURL()}/delete/${id}`)
  }
}
