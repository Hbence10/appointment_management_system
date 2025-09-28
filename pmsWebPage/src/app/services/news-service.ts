import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NewsService {
  private http = inject(HttpClient)
  baseURL = signal<string>("http://localhost:8080")

  getAllNews(): Observable<any[]> {
    return this.http.get<any[]>("http://localhost:8080/news/getAll",)
  }
}
