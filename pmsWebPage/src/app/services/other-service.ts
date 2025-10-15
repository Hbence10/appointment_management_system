import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Observable } from 'rxjs';
import { Gallery } from '../models/galleryImage.model';
import { Rule } from '../models/rule.model';
import { History } from '../models/history.model';

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

  saveRuleChanges(newRule: Rule) {
    return this.http.post(`${this.baseURL}/rule/update`, { updatedRule: newRule })
  }

  //History
  getHistory(): Observable<History[]>{
    return this.http.get<History[]>(`${this.baseURL()}/history`)
  }
}
