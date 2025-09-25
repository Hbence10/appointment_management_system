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

  login(username: string, password: string): Observable<User> {
    return this.http.get<User>(`${this.baseURL()}/users/login?username=${username}&password=${password}`)
  }

  register(requestedBody: { username: string, email: string, password: string, pfpPath: string }) {
    return this.http.post(`${this.baseURL()}/users/register`, requestedBody)
  }

  getVerificationCode(email: string): Observable<{vCode: string}> {
    return this.http.get<{vCode: string}>(`${this.baseURL()}/users/verificationCode?email=${email}`)
  }

  changePassword(email: string, newPassword: string): Observable<{result: string}>{
    return this.http.patch<{result: string}>(`${this.baseURL()}/users/passwordReset`, {email: email, newPassword: newPassword})
  }
}
