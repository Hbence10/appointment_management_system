import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Rule } from '../models/rule.model';
import { History } from '../models/history.model';
import { Details } from '../models/details.model';
import { OpeningDetails } from '../models/openingDetails.model';

@Injectable({
  providedIn: 'root'
})
export class OtherService {
  private http = inject(HttpClient)
  private baseURL = "http://localhost:8080"
  rule!: Rule
  detailsForFooter!: Details
  openingDetails: OpeningDetails[] = []

  //ENDPOINTOK:
  //Szabalyzat
  getRule(): Observable<Rule> {
    return this.http.get<Rule>(`${this.baseURL}/rule`)
  }

  updateRule(updatedRule: Rule) {
    return this.http.put(`${this.baseURL}/rule/update`, updatedRule)
  }

  //History
  getHistory(): Observable<History[]> {
    return this.http.get<History[]>(`${this.baseURL}/history`)
  }

  //Adatok:
  getDetails(): Observable<Details> {
    return this.http.get<Details>(`${this.baseURL}/details`)
  }

  updateDetails(updatedDetails: Details): Observable<Details> {
    return this.http.put<Details>(`${this.baseURL}/details/update`, updatedDetails)
  }

  getOpeningDetails(): Observable<OpeningDetails[]> {
    return this.http.get<OpeningDetails[]>(`${this.baseURL}/openingDetails`)
  }

  updateOpeningDetails(): Observable<OpeningDetails[]> {
    return this.http.put<OpeningDetails[]>(`${this.baseURL}/openingDetails/update`, this.openingDetails)
  }
}
