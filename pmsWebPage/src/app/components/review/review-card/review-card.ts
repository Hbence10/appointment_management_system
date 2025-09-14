import { Component, inject, input, OnInit, signal } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { ReviewDetails } from '../../../models/reviewDetails.model';
import { OtherService } from '../../../services/other-service';
import { UserService } from '../../../services/user-service';

@Component({
  selector: 'app-review-card',
  imports: [MatIconModule],
  templateUrl: './review-card.html',
  styleUrl: './review-card.scss'
})
export class ReviewCard implements OnInit {
  private otherService = inject(OtherService)
  private userService = inject(UserService)

  reviewDetail = input.required<ReviewDetails>()
  startList = signal<number[]>([])
  selectedLikeType: "like" | "dislike" | "" = ""

  ngOnInit(): void {
    console.log(this.reviewDetail())
    if (this.userService.user()) {
      let usersHistory = this.reviewDetail().likeHistories.find(history => history.likerUser.id == this.userService.user()!.id)

      if (usersHistory) {
        this.selectedLikeType = usersHistory.likeType
        console.log(this.selectedLikeType)
      }
    }
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
