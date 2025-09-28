import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-unauthorized',
  imports: [MatButtonModule, RouterModule],
  templateUrl: './unauthorized.html',
  styleUrl: './unauthorized.scss'
})
export class Unauthorized {

}
