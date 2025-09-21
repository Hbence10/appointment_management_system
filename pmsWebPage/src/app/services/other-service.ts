import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Observable } from 'rxjs';
import { GalleryImage } from '../models/galleryImage.model';

@Injectable({
  providedIn: 'root'
})
export class OtherService {
  private http = inject(HttpClient)
  baseURL = signal<string>("http://localhost:8080")
  selectedImgForCarousel = signal<null | GalleryImage>(null)

  //Galleria
  getAllGalleryImages(): Observable<GalleryImage[]> {
    return this.http.get<GalleryImage[]>(`${this.baseURL()}/gallery`)
  }

  //Szabalyzat
  getRule(): Observable<{ id: number, text: string, lastEditAt: Date }> {
    return this.http.get<{ id: number, text: string, lastEditAt: Date }>(`${this.baseURL()}/rule`)
  }
}
