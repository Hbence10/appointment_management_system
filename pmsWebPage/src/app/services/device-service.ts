import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DeviceService {
  private http = inject(HttpClient)
  baseURL = signal<string>("http://localhost:8080")
}
