import { Component, DestroyRef, inject, OnInit } from '@angular/core';
import { OtherService } from '../../services/other-service';
import { Details } from '../../models/details.model';
import { OpeningDetails } from '../../models/openingDetails.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-footer',
  imports: [CommonModule],
  templateUrl: './footer.html',
  styleUrl: './footer.scss'
})
export class Footer implements OnInit {
  otherService = inject(OtherService)
  private destroyRef = inject(DestroyRef)
  openingDetails: OpeningDetails[] = []

  ngOnInit(): void {
    this.otherService.getDetails().subscribe({
      next: response => {
        this.otherService.detailsForFooter = Object.assign(new Details(), response)
      },
      error: error => console.log(error)
    })

    this.otherService.getOpeningDetails().subscribe({
      next: responseList => {
        this.otherService.openingDetails = responseList.map(response => Object.assign(new OpeningDetails(), response))
      },
      error: error => console.log(error),
      complete: () => {
        // this.otherService.openingDetails.map(detail => detail.setStartTime = new Date("2025-01-01 " + detail.getStartTime))
        // this.otherService.openingDetails.map(detail => detail.setEndTime = new Date("2025-01-01 " + detail.getEndTime))
      }
    })
  }
}
