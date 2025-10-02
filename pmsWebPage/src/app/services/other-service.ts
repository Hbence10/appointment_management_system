import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Observable } from 'rxjs';
import { Gallery } from '../models/galleryImage.model';
import { Rule } from '../models/rule.model';

@Injectable({
  providedIn: 'root'
})
export class OtherService {
  private http = inject(HttpClient)
  baseURL = signal<string>("http://localhost:8080")
  selectedImgForCarousel = signal<null | Gallery>(null)

  //Galleria
  getAllGalleryImages(): Observable<Gallery[]> {
    return this.http.get<Gallery[]>(`${this.baseURL()}/gallery`)
  }

  //Szabalyzat
  getRule(): Observable<Rule> {
    return this.http.get<Rule>(`${this.baseURL()}/rule`)
  }
}
