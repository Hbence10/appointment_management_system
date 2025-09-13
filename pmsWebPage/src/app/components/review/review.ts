import { Component, DestroyRef, inject, OnInit, Signal, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { Router } from '@angular/router';
import { ReviewDetails } from '../../models/reviewDetails.model';
import { OtherService } from '../../services/other-service';
import { UserService } from '../../services/user-service';
import { ReviewCard } from './review-card/review-card';
import { User } from '../../models/user.model';

@Component({
  selector: 'app-review',
  imports: [MatInputModule, MatFormFieldModule, ReviewCard, MatCheckboxModule, MatButtonModule, ReactiveFormsModule],
  templateUrl: './review.html',
  styleUrl: './review.scss'
})
export class Review implements OnInit {
  private otherService = inject(OtherService)
  private userService = inject(UserService)
  private destroyRef = inject(DestroyRef)

  reviewForm = new FormGroup({
    reviewText: new FormControl("", [Validators.required]),
    rating: new FormControl(2.5, [Validators.required])
  })

  reviewDetails = signal<ReviewDetails[]>([])
  isAnonymus = signal<boolean>(false)
  user = signal<User | null>(null);

  ngOnInit(): void {
    this.user = this.userService.user

    const subscription = this.otherService.getAllReviews().subscribe({
      next: response => this.reviewDetails.set(response),
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
      const newReview = new ReviewDetails(null, this.reviewForm.controls["reviewText"].value!, 2.5, this.user()!, this.isAnonymus())
      this.otherService.addReview(newReview).subscribe({
        next: response => console.log(response),
        complete: () => this.reviewDetails().push(newReview)
      })
    }
  }

  setVisibility() {
    this.isAnonymus.update(old => !old)
  }

  addLike(){

  }
}
