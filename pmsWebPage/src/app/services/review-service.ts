import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Observable } from 'rxjs';
import { Review } from '../models/reviewDetails.model';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {
  private http = inject(HttpClient)
  baseURL = signal<string>("http://localhost:8080")

  getAllReviews(): Observable<Review[]> {
    return this.http.get<Review[]>(`${this.baseURL()}/reviews/getAll`)
  }

  addReview(newReview: Review) {
    return this.http.post(`${(this.baseURL())}/reviews/addReview`, newReview)
  }

  addLikeToReview(reviewId: number, likeBody: { likeCount: number, dislikeCount: number }): Observable<Review> {
    return this.http.patch<Review>(`${this.baseURL()}/reviews/${reviewId}`, likeBody)
  }
}
