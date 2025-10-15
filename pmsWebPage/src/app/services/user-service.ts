import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Observable } from 'rxjs';
import { Users } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private http = inject(HttpClient)
  private baseURL = signal<string>("http://localhost:8080/users")
  user = signal<null | Users>(null)
  token: string = ""

  login(username: string, password: string): Observable<any> {
    return this.http.post<Users>(`${this.baseURL()}/login`, { username: username, password: password }, { observe: "response" })
  }

  register(newUser: Users) {
    return this.http.post(`${this.baseURL()}/register`, newUser)
  }

  updateUser(newEmail: string, newUsername: string, userId: number) {
    return this.http.patch(`${this.baseURL()}/updateUser/${userId}`, { email: newEmail, username: newUsername })
  }

  deleteUser(userId: number) {
    return this.http.delete(`${this.baseURL()}/deleteUser/${userId}`)
  }

  //adminPage:
  getAllAdmin(){
    return this.http.get(``)
  }

  makeAdmin(){
    return this.http.post(``, {})
  }

  //password reset
  getVerificationCode(email: string) {
    return this.http.get(`${this.baseURL()}/getVerificationCode`, { params: new HttpParams().set("email", email) })
  }

  checkVerificationCode(userVCode: string): Observable<boolean> {
    return this.http.post<boolean>(`${this.baseURL()}/checkVerificationCode`, { vCode: userVCode })
  }

  passwordReset(email: string, newPassword: string, vCode: string) {
    return this.http.patch(`${this.baseURL()}/passwordReset`, { email: email, newPassword: newPassword, vCode: vCode })
  }
}
