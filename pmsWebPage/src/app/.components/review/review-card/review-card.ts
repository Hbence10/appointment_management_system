import { ReviewDetails } from './../../../.models/reviewDetails.model';
import { Component, input } from '@angular/core';

@Component({
  selector: 'app-review-card',
  imports: [],
  templateUrl: './review-card.html',
  styleUrl: './review-card.scss'
})
export class ReviewCard {
  reviewDetail = input.required<ReviewDetails>()
}
