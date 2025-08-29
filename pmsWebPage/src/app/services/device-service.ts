import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { DeviceCategory } from '../models/deviceCategory.model';

@Injectable({
  providedIn: 'root'
})
export class DeviceService {
  private http = inject(HttpClient)
  baseURL = signal<string>("http://localhost:8080")

  getAllDevicesByCategories(){
    return this.http.get<DeviceCategory[]>(`${this.baseURL()}/devices/category`)
  }
}
