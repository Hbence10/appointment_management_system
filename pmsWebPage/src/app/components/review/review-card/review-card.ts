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
  startList = signal<number[]>([])
  selectedLikeType: "like" | "dislike" | "" = ""
  isUserLikedIt = signal(false)
  likeTypeOfUser: "like" | "dislike" | "" = ""
  usersReviewLike?: ReviewHistory;

  ngOnInit(): void {
    this.reviewDetail().getLikeHistories.forEach(reviewLike => {
      reviewLike.setLikerUser = Object.assign(new Users(), reviewLike.getLikerUser)
      if (reviewLike.getLikerUser.getId == this.userService.user()?.getId) {
        this.isUserLikedIt.set(true)
        this.likeTypeOfUser = reviewLike.getLikeType
        this.usersReviewLike = reviewLike
      }
    })
  }

  setLike(likeType: "like" | "dislike") {
    if (!this.isUserLikedIt()) {
      this.addLike(likeType)
    } else if (this.likeTypeOfUser == likeType && this.isUserLikedIt()) {
      this.deleteLike()
    } else if (this.likeTypeOfUser != likeType && this.isUserLikedIt()){
      this.updateLike()
    }
  }

  addLike(likeType: "like" | "dislike") {
    const reviewLike: ReviewHistory = new ReviewHistory(null, likeType, this.userService.user()!, this.reviewDetail())
    this.reviewService.addLike(reviewLike).subscribe({
      next: response => console.log(response),
      error: error => console.log(error),
      complete: () => {

      }
    })
  }

  updateLike() {
    this.reviewService.updateLike(this.usersReviewLike?.getId!).subscribe({
      next: response => console.log(response)
    })
  }

  deleteLike() {
    this.reviewService.deleteLike(this.usersReviewLike?.getId!).subscribe({
      next: response => console.log(response)
    })
  }
}
