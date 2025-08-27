import { ReviewCard } from './review-card/review-card';
import { Component, inject, OnInit, signal } from '@angular/core';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { ReviewDetails } from '../../models/reviewDetails.model';
import { CommonModule } from "@angular/common";
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatButtonModule } from '@angular/material/button';
import { OtherService } from '../../services/other-service';

@Component({
  selector: 'app-review',
  imports: [MatInputModule, MatFormFieldModule, ReviewCard, MatCheckboxModule, MatButtonModule],
  templateUrl: './review.html',
  styleUrl: './review.scss'
})
export class Review implements OnInit{
  private otherService = inject(OtherService)
  reviewDetails = signal<ReviewDetails[]>([])

  ngOnInit(): void {
    this.otherService.getAllReviews().subscribe({
      next: response => this.reviewDetails.set(response),
      complete: () => console.log(this.reviewDetails())
    })
  }
}
