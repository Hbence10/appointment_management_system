import { Component, inject, input, OnInit, signal } from '@angular/core';
import { Review } from '../../../models/reviewDetails.model';
import { ReviewHistory } from '../../../models/reviewHistory.model';
import { Users } from '../../../models/user.model';
import { ReviewService } from '../../../services/review-service';
import { UserService } from '../../../services/user-service';

@Component({
  selector: 'app-review-card',
  imports: [],
  templateUrl: './review-card.html',
  styleUrl: './review-card.scss'
})
export class ReviewCard implements OnInit {
  private reviewService = inject(ReviewService)
  private userService = inject(UserService)

  reviewDetail = input.required<Review>()
  starList = signal<number[]>([])
  usersReviewLike = signal<ReviewHistory | null>(null);

  ngOnInit(): void {
    this.reviewDetail().getLikeHistories.forEach(reviewLike => {
      reviewLike.setLikerUser = Object.assign(new Users(), reviewLike.getLikerUser)

      if (reviewLike.getLikerUser.getId == this.userService.user()?.getId) {
        this.usersReviewLike.set(reviewLike)
      }
    })
  }

  setLike(likeType: "like" | "dislike") {
    if (this.usersReviewLike() == null) {
      this.addLike(likeType)
    } else if (this.usersReviewLike()?.getLikeType == likeType && this.usersReviewLike() != null) {
      this.deleteLike()
    } else if (this.usersReviewLike()?.getLikeType != likeType && this.usersReviewLike != null) {
      this.updateLike()
    }
  }

  addLike(likeType: "like" | "dislike") {
    const reviewLike: ReviewHistory = new ReviewHistory(null, likeType, this.userService.user()!, this.reviewDetail())
    this.reviewService.addLike(reviewLike).subscribe({
      next: response => {
        this.reviewDetail().getLikeHistories.push(Object.assign(new ReviewHistory(), response))
        this.usersReviewLike.set(Object.assign(new ReviewHistory(), response))

        if (likeType == 'like') {
          this.reviewDetail().setLikeCount = this.reviewDetail().getLikeCount + 1
        } else {
          this.reviewDetail().setDislikeCount = this.reviewDetail().getDislikeCount + 1
        }
      },
      error: error => console.log(error)
    })
  }

  updateLike() {
    this.reviewService.updateLike(this.usersReviewLike()?.getId!).subscribe({
      next: response => {
        this.usersReviewLike.set(Object.assign(new ReviewHistory(), response))
        if (this.usersReviewLike()?.getLikeType == 'like') {
          this.reviewDetail().setLikeCount = this.reviewDetail().getLikeCount + 1
          this.reviewDetail().setDislikeCount = this.reviewDetail().getDislikeCount - 1
        } else {
          this.reviewDetail().setLikeCount = this.reviewDetail().getLikeCount - 1
          this.reviewDetail().setDislikeCount = this.reviewDetail().getDislikeCount + 1
        }
      }
    })
  }

  deleteLike() {
    this.reviewService.deleteLike(this.usersReviewLike()?.getId!).subscribe({
      next: response => {
        this.reviewDetail().setLikeHistory = this.reviewDetail().getLikeHistories.splice(
          this.reviewDetail().getLikeHistories.indexOf(this.usersReviewLike()!)
        )
        if (this.usersReviewLike()?.getLikeType == 'like') {
          this.reviewDetail().setLikeCount = this.reviewDetail().getLikeCount - 1
        } else {
          this.reviewDetail().setDislikeCount = this.reviewDetail().getDislikeCount - 1
        }
        this.usersReviewLike.set(null)
      }
    })
  }
}
