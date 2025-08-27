import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { ReviewDetails } from '../models/reviewDetails.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OtherService {
  private http = inject(HttpClient)

  getAllReviews(): Observable<ReviewDetails[]> {
    return this.http.get<ReviewDetails[]>("http://localhost:8080/reviews")
  }

  addReview(requestBody: {userId: number, reviewText: string, rating: number}){
    return this.http.post("", {})
  }
}
