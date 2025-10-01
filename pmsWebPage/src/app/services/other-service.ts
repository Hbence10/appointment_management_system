import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Observable } from 'rxjs';
import { Gallery } from '../models/galleryImage.model';

@Injectable({
  providedIn: 'root'
})
export class OtherService {
  private http = inject(HttpClient)
  baseURL = signal<string>("http://localhost:8080")
  selectedImgForCarousel = signal<null | Gallery>(null)

  //Galleria
  getAllGalleryImages(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseURL()}/gallery`)
  }

  //Szabalyzat
  getRule(): Observable<{ id: number, text: string, lastEditAt: Date }> {
    return this.http.get<{ id: number, text: string, lastEditAt: Date }>(`${this.baseURL()}/rule`)
  }
}
