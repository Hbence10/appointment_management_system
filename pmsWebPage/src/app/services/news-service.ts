import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Observable } from 'rxjs';
import { News } from '../models/newsDetails.model';

@Injectable({
  providedIn: 'root'
})
export class NewsService {
  private http = inject(HttpClient)
  baseURL = signal<string>("http://localhost:8080/news")

  getAllNews(): Observable<News[]> {
    return this.http.get<News[]>(`${this.baseURL()}/getAll`,)
  }

  updateNews(updatedNews: News) {
    return this.http.put(`${this.baseURL()}/update`, updatedNews)
  }

  createNews(newNews: News) {
    return this.http.post(`${this.baseURL()}/addNews`, newNews, { observe: "response" })
  }

  deleteNews(id: number) {
    return this.http.delete(`${this.baseURL()}/delete/${id}`)
  }
}
