import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Observable } from 'rxjs';
import { ReviewDetails } from '../models/reviewDetails.model';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {
  private http = inject(HttpClient)
  baseURL = signal<string>("http://localhost:8080")

  getAllReviews(): Observable<ReviewDetails[]> {
    return this.http.get<ReviewDetails[]>(`${this.baseURL()}/reviews`)
  }

  addReview(newReview: ReviewDetails) {
    return this.http.post(`${(this.baseURL())}/reviews`, newReview)
  }

  addLikeToReview(reviewId: number, likeBody: { likeCount: number, dislikeCount: number }): Observable<ReviewDetails> {
    return this.http.patch<ReviewDetails>(`${this.baseURL()}/reviews/${reviewId}`, likeBody)
  }
}
