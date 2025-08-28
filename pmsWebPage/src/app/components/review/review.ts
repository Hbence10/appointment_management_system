import { Component, DestroyRef, inject, OnInit, signal } from '@angular/core';
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

@Component({
  selector: 'app-review',
  imports: [MatInputModule, MatFormFieldModule, ReviewCard, MatCheckboxModule, MatButtonModule, ReactiveFormsModule],
  templateUrl: './review.html',
  styleUrl: './review.scss'
})
export class Review implements OnInit {
  private otherService = inject(OtherService)
  private userService = inject(UserService)
  private router = inject(Router)
  private destroyRef = inject(DestroyRef)

  reviewForm = new FormGroup({
    reviewText: new FormControl("", [Validators.required])
  })

  reviewDetails = signal<ReviewDetails[]>([])

  ngOnInit(): void {
    const subscription = this.otherService.getAllReviews().subscribe({
      next: response => this.reviewDetails.set(response),
      complete: () => console.log(this.reviewDetails())
    })

    this.destroyRef.onDestroy(() => {
      console.log("destroyed!!!! - reviewComponent")
      subscription.unsubscribe()
    })
  }


  addReview() {
    if (this.userService.user() == null) {
      alert("A vélemény íráshoz, kérem jelentkezzen be!")
    } else {
      this.otherService.addReview({
        userId: this.userService.user()!.id,
        reviewText: this.reviewForm.controls["reviewText"].value!,
        rating: 2
      })
    }
  }


}
