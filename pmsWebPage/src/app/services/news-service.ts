import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Observable } from 'rxjs';
import { News } from '../models/newsDetails.model';

@Injectable({
  providedIn: 'root'
})
export class NewsService {
  private http = inject(HttpClient)
  baseURL = signal<string>("http://localhost:8080")

  getAllNews(): Observable<News[]> {
    return this.http.get<News[]>("http://localhost:8080/news/getAll",)
  }
}
