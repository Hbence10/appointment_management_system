import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { DevicesCategory } from '../models/deviceCategory.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DeviceService {
  private http = inject(HttpClient)
  baseURL = signal<string>("http://localhost:8080")

  getAllDevicesByCategories(): Observable<DevicesCategory[]> {
    return this.http.get<any[]>(`${this.baseURL()}/devices/getAllCategory`)
  }

  addDeviceCategory(newDevicesCategory: DevicesCategory) {
    return this.http.post(`${this.baseURL()}/devices/addCategory`, { id: newDevicesCategory.getId, name: newDevicesCategory.getName, devicesList: newDevicesCategory.getDevicesList })
  }
}
