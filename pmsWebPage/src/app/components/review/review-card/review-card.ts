import { Component, inject, input, OnInit, signal } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { ReviewDetails } from '../../../models/reviewDetails.model';
import { OtherService } from '../../../services/other-service';

@Component({
  selector: 'app-review-card',
  imports: [MatIconModule],
  templateUrl: './review-card.html',
  styleUrl: './review-card.scss'
})
export class ReviewCard implements OnInit {
  private otherService = inject(OtherService)
  reviewDetail = input.required<ReviewDetails>()
  startList = signal<number[]>([])

  ngOnInit(): void {
  }

  addLike(likeType: "like" | "dislike") {
    if (likeType == "like"){
      this.reviewDetail().likeCount += 1
    } else if (likeType == "dislike"){
      this.reviewDetail().dislikeCount += 1
    }


  }

}
