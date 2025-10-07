import { Component, inject, input, OnInit, signal } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { Review } from '../../../models/reviewDetails.model';
import { ReviewService } from '../../../services/review-service';
import { UserService } from '../../../services/user-service';
import { ReviewHistory } from '../../../models/reviewHistory.model';

@Component({
  selector: 'app-review-card',
  imports: [MatIconModule],
  templateUrl: './review-card.html',
  styleUrl: './review-card.scss'
})
export class ReviewCard implements OnInit {
  private reviewService = inject(ReviewService)
  private userService = inject(UserService)

  reviewDetail = input.required<Review>()
  startList = signal<number[]>([])
  selectedLikeType: "like" | "dislike" | "" = ""
  isUserLikedIt = signal(false)

  ngOnInit(): void {

  }

  setLike(likeType: "like" | "dislike") {

    this.addLike(likeType)
  }

  addLike(likeType: "like" | "dislike"){
    const reviewLike: ReviewHistory = new ReviewHistory(null, likeType, this.userService.user()!, this.reviewDetail())
    this.reviewService.addLike(reviewLike).subscribe({
      next: response => console.log(response)
    })
  }

  updateLike(likeType: "like" | "dislike"){

  }

  deleteLike(){

  }
}
