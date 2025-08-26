import { ReviewCard } from './review-card/review-card';
import { Component, signal } from '@angular/core';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { ReviewDetails } from '../../models/reviewDetails.model';
import { CommonModule } from "@angular/common";

@Component({
  selector: 'app-review',
  imports: [MatInputModule, MatFormFieldModule, ReviewCard],
  templateUrl: './review.html',
  styleUrl: './review.scss'
})
export class Review {
  reviewDetails = signal<ReviewDetails[]>([])
}
