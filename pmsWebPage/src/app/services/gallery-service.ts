import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Gallery } from '../models/galleryImage.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class GalleryService {
  private http = inject(HttpClient)
  private baseURL = "http://localhost:8080/gallery"
  selectedImgForCarousel = signal<null | Gallery>(null)
  galleryImages: Gallery[] = []
  selectedImageForEdit: File | null = null

  getAllGalleryImages(): Observable<Gallery[]> {
    return this.http.get<Gallery[]>(`${this.baseURL}`)
  }

  updateGalleryImage(id: number, formData: FormData): Observable<Gallery> {
    return this.http.put<Gallery>(`${this.baseURL}/update/${id}`, formData)
  }

  deleteImage(id: number) {
    return this.http.delete(`${this.baseURL}/delete/${id}`)
  }

  addImage(formData: FormData): Observable<Gallery> {
    return this.http.post<Gallery>(`${this.baseURL}/addImage`, formData)
  }

  updatePlacement(): Observable<Gallery[]> {
    return this.http.put<Gallery[]>(`${this.baseURL}/updateOrder`, this.galleryImages)
  }
}
