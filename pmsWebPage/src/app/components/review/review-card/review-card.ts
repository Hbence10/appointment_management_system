import { Component, inject, input, OnInit, signal } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { ReviewDetails } from '../../../models/reviewDetails.model';
import { ReviewService } from '../../../services/review-service';
import { UserService } from '../../../services/user-service';

@Component({
  selector: 'app-review-card',
  imports: [MatIconModule],
  templateUrl: './review-card.html',
  styleUrl: './review-card.scss'
})
export class ReviewCard implements OnInit {
  private reviewService = inject(ReviewService)
  private userService = inject(UserService)

  reviewDetail = input.required<ReviewDetails>()
  startList = signal<number[]>([])
  selectedLikeType: "like" | "dislike" | "" = ""

  ngOnInit(): void {
    let counter = this.reviewDetail().rating

    while (this.startList().length != 5) {
      if (counter % 1 == 0 && counter > 0) {
        this.startList.update(old => [1, ...old])
        counter -= 1
      } else if (counter % 0.5 == 0 && counter > 0) {
        this.startList.update(old => [0.5, ...old])
        counter -= 0.5
      } else if (counter == 0) {
        this.startList.update(old => [...old, 0])
      }
    }
    console.log(this.startList())
  }

  addLike(likeType: "like" | "dislike") {
    if (!this.userService.user()) {
      alert("Jelentkezzél be!")
    }

    let userLike = this.reviewDetail().likeHistories.find(history => history.likerUser.id == this.userService.user()!.id)

    if (!userLike) {
      console.log("Uj history")
    } else {
      console.log("history update")
    }

    console.log(userLike)
  }

}
