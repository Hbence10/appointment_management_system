import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
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
  token: string = ""

  login(username: string, password: string): Observable<any> {
    return this.http.post<User>(`${this.baseURL()}/users/login`, { username: username, password: password }, { observe: "response" })
  }

  register(requestedBody: { username: string, email: string, password: string, pfpPath: string }) {
    return this.http.post(`${this.baseURL()}/users/register`, requestedBody)
  }

  //password reset
  getVerificationCode(email: string) {
    return this.http.get(`${this.baseURL()}/users/getVerificationCode`, { params: new HttpParams().set("email", email) })
  }

  checkVerificationCode(userVCode: string): Observable<boolean> {
    return this.http.post<boolean>(`${this.baseURL()}/users/checkVerificationCode`, {vCode: userVCode})
  }

  passwordReset(email: string, newPassword: string, vCode: string) {
    return this.http.patch(`${this.baseURL()}/users/passwordReset`, {email: email, newPassword: newPassword, vCode: vCode})
  }
}
