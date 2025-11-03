import { Component, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { Review } from '../../models/reviewDetails.model';
import { Users } from '../../models/user.model';
import { ReviewService } from '../../services/review-service';
import { UserService } from '../../services/user-service';
import { ReviewCard } from './review-card/review-card';
import { ReviewHistory } from '../../models/reviewHistory.model';

@Component({
  selector: 'app-review',
  imports: [MatInputModule, MatFormFieldModule, ReviewCard, MatCheckboxModule, MatButtonModule, ReactiveFormsModule],
  templateUrl: './review.html',
  styleUrl: './review.scss'
})
export class ReviewPage implements OnInit {
  private reviewService = inject(ReviewService)
  private userService = inject(UserService)
  private destroyRef = inject(DestroyRef)

  reviewForm = new FormGroup({
    reviewText: new FormControl("", [Validators.required]),
    rating: new FormControl(2.5, [Validators.required])
  })

  reviewDetails = signal<Review[]>([])
  isAnonymus = signal<boolean>(false)
  user = signal<Users | null>(null);

  ngOnInit(): void {
    this.user = this.userService.user

    const subscription = this.reviewService.getAllReviews().subscribe({
      next: responseList => {
        responseList.forEach(response => {
          let review: Review = Object.assign(new Review(), response)
          review.setAuthor = Object.assign(new Users(), review.getAuthor)
          review.setLikeHistory = review.getLikeHistories.map(element => Object.assign(new ReviewHistory(), element))
          review.setLikeCount = review.getLikeHistories.filter(element => element.getLikeType == "like").length
          review.setDislikeCount = review.getLikeHistories.filter(element => element.getLikeType == "dislike").length
          this.reviewDetails.update(old => [...old, review])
        })
      },
      complete: () => console.log(this.reviewDetails())
    })

    this.destroyRef.onDestroy(() => {
      subscription.unsubscribe()
    })
  }

  addReview() {
    if (this.userService.user() == null) {
      alert("A vélemény íráshoz, kérem jelentkezzen be!")
    } else {
      const newReview = new Review(null, this.reviewForm.controls["reviewText"].value!, this.reviewForm.controls["rating"].value!, this.userService.user()!, this.isAnonymus())
      this.reviewService.addReview(newReview).subscribe({
        error: error => console.log(error),
        complete: () => {
          this.reviewDetails.update(old => [...old, newReview])
        }
      })
    }
  }

  setVisibility() {
    this.isAnonymus.update(old => !old)
  }
}
