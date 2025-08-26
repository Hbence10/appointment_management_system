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

  login(username: string, password: string): Observable<any>{
    return this.http.get<User>(`http://localhost:8080/users/login?username=${username}&password=${password}`)
  }

  register(requestedBody: {username: string,email: string, password: string, pfpPath: string}){6
    return this.http.post("http://localhost:8080/users/register", requestedBody)
  }
}
