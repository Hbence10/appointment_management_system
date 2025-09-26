import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { DeviceCategory } from '../models/deviceCategory.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DeviceService {
  private http = inject(HttpClient)
  baseURL = signal<string>("http://localhost:8080")

  getAllDevicesByCategories(): Observable<any[]>{
    return this.http.get<any[]>(`${this.baseURL()}/devices/getAllCategory`)
  }
}
