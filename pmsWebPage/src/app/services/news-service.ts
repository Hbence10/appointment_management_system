import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Observable } from 'rxjs';
import { News } from '../models/newsDetails.model';

@Injectable({
  providedIn: 'root'
})
export class NewsService {
  private http = inject(HttpClient)
  private baseURL = "http://localhost:8080/news"
  selectedBannerImg: File | null = null

  getAllNews(): Observable<News[]> {
    return this.http.get<News[]>(`${this.baseURL}/getAll`,)
  }

  uploadBannerImg(newsId: number): Observable<News> {
    const formData: FormData = new FormData()
    formData.append("coverImg", this.selectedBannerImg!)

    return this.http.patch<News>(`${this.baseURL}/addCoverImg/${newsId}`, formData)
  }

  updateNews(updatedNews: News): Observable<News> {
    return this.http.put<News>(`${this.baseURL}/update`, updatedNews)
  }

  createNews(newNews: News): Observable<News> {
    return this.http.post<News>(`${this.baseURL}/addNews`, newNews)
  }

  deleteNews(id: number) {
    return this.http.delete(`${this.baseURL}/delete/${id}`)
  }
}
