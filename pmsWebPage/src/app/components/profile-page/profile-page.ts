import { Component, inject, OnInit } from '@angular/core';
import { UserService } from '../../services/user-service';
import { User } from '../../models/user.model';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-profile-page',
  imports: [MatButtonModule],
  templateUrl: './profile-page.html',
  styleUrl: './profile-page.scss'
})
export class ProfilePage implements OnInit{
  private userService = inject(UserService)
  user!: User;

  ngOnInit(): void {
    this.user = this.userService.user()!
  }
}
