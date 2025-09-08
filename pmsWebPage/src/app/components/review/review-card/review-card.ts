import { MatIconModule } from '@angular/material/icon';
import { ReviewDetails } from '../../../models/reviewDetails.model';
import { Component, input, output } from '@angular/core';

@Component({
  selector: 'app-review-card',
  imports: [MatIconModule],
  templateUrl: './review-card.html',
  styleUrl: './review-card.scss'
})
export class ReviewCard {
  reviewDetail = input.required<ReviewDetails>()
  addDislikeOutput = output()
  addLikeOutput = output()

  addDislike(){
    this.addDislikeOutput.emit()
  }

  addLike(){
    this.addLikeOutput.emit()
  }
}
