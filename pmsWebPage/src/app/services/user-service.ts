import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { User } from '../models/user.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private http = inject(HttpClient)
  user = signal<null | User>(null)
  baseURL = signal<string>("http://localhost:8080")

  login(username: string, password: string): Observable<any>{
    return this.http.get(`${this.baseURL()}/users/login?username=${username}&password=${password}`)
  }

  register(requestedBody: {username: string,email: string, password: string, pfpPath: string}){
    return this.http.post(`${this.baseURL()}/users/register`, requestedBody)
  }

}
