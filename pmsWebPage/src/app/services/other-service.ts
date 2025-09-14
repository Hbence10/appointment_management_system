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

  //Velemenyek
  getAllReviews(): Observable<ReviewDetails[]> {
    return this.http.get<ReviewDetails[]>(`${this.baseURL()}/reviews`)
  }

  addReview(newReview: ReviewDetails){
    return this.http.post(`${(this.baseURL())}/reviews`, newReview)
  }

  addLikeToReview(){
    return this.http.patch(`${this.baseURL}/reviews?id=1&addedLikeType=like`, {})
  }

  //Galleria
  getAllGalleryImages(): Observable<GalleryImage[]>{
    return this.http.get<GalleryImage[]>(`${this.baseURL()}/gallery`)
  }

  //Szabalyzat
  getRule(): Observable<{id: number, text: string, lastEditAt: Date}>{
    return this.http.get<{id: number, text: string, lastEditAt: Date}>(`${this.baseURL()}/rule`)
  }
}
