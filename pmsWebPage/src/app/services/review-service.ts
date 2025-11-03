import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Observable } from 'rxjs';
import { Review } from '../models/reviewDetails.model';
import { ReviewHistory } from '../models/reviewHistory.model';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {
  private http = inject(HttpClient)
  baseURL = signal<string>("http://localhost:8080/reviews")

  //review:
  getAllReviews(): Observable<Review[]> {
    return this.http.get<Review[]>(`${this.baseURL()}/getAll`)
  }

  addReview(newReview: Review){
    return this.http.post(`${this.baseURL()}/addReview`, newReview)
  }

  //reviewLike:
  addLike(reviewLike: ReviewHistory): Observable<ReviewHistory>{
    return this.http.post<ReviewHistory>(`${this.baseURL()}/addLike`, reviewLike)
  }

  updateLike(id: number): Observable<ReviewHistory>{
    return this.http.put<ReviewHistory>(`${this.baseURL()}/changeLikeType/${id}`, {})
  }

  deleteLike(id: number){
    return this.http.delete(`${this.baseURL()}/deleteLike/${id}`)
  }
}
