import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { ReviewDetails } from '../models/reviewDetails.model';
import { Observable } from 'rxjs';
import { GalleryImage } from '../models/galleryImage.model';

@Injectable({
  providedIn: 'root'
})
export class OtherService {
  private http = inject(HttpClient)
  baseURL = signal<string>("http://localhost:8080")

  getAllReviews(): Observable<ReviewDetails[]> {
    return this.http.get<ReviewDetails[]>(`${this.baseURL()}/reviews`)
  }

  addReview(requestBody: {userId: number, reviewText: string, rating: number, isAnonymus: boolean}){
    return this.http.post(`${(this.baseURL())}/reviews`, requestBody)
  }

  getAllGalleryImages(): Observable<GalleryImage[]>{
    return this.http.get<GalleryImage[]>(`${this.baseURL()}/gallery`)
  }

  getRule(): Observable<{id: number, text: string, lastEditAt: Date}>{
    return this.http.get<{id: number, text: string, lastEditAt: Date}>(`${this.baseURL()}/rule`)
  }
}
