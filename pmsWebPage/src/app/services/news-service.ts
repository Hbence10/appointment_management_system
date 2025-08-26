import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { NewsDetails } from '../models/newsDetails.model';

@Injectable({
  providedIn: 'root'
})
export class NewsService {
  private http = inject(HttpClient)

  getAllNews(): Observable<NewsDetails[]> {
    return this.http.get<NewsDetails[]>("http://localhost:8080/news/")
  }
}
