import { Component } from '@angular/core';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
@Component({
  selector: 'app-review',
  imports: [MatInputModule, MatFormFieldModule],
  templateUrl: './review.html',
  styleUrl: './review.scss'
})
export class Review {

}
