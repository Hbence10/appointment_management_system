import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Gallery } from '../models/galleryImage.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class GalleryService {
  private http = inject(HttpClient)
  private baseURL = "http://localhost:8080"
  selectedImgForCarousel = signal<null | Gallery>(null)
  galleryImages: Gallery[] = []
  selectedImageForEdit: File | null = null

  getAllGalleryImages(): Observable<Gallery[]> {
    return this.http.get<Gallery[]>(`${this.baseURL}/gallery`)
  }

  updateGalleryImage(id: number, newImg: FormData) {

  }

  deleteImage(id: number) {

  }

  addImage(placement: number): Observable<Gallery> {
    return this.http.post<Gallery>("", {})
  }

  updatePlacement(): Observable<Gallery[]> {
    return this.http.put<Gallery[]>("", {})
  }
}
