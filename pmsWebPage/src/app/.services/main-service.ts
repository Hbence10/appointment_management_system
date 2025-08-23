import { User } from './../.models/user.model';
import { Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class MainService {
  user = signal<User | null>(null)
}
