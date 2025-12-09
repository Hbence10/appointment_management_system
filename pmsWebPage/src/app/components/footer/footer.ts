import { Component, DestroyRef, inject, OnInit } from '@angular/core';
import { OtherService } from '../../services/other-service';
import { Details } from '../../models/details.model';
import { OpeningDetails } from '../../models/openingDetails.model';

@Component({
  selector: 'app-footer',
  imports: [],
  templateUrl: './footer.html',
  styleUrl: './footer.scss'
})
export class Footer implements OnInit {
  private otherService = inject(OtherService)
  private destroyRef = inject(DestroyRef)
  details!: Details
  openingDetails: OpeningDetails[] = []

  ngOnInit(): void {
    const subscription = this.otherService.getDetails().subscribe({
      next: response => console.log(response),
      error: error => console.log(error),
      complete: () => {
        this.otherService.getOpeningDetails().subscribe({
          next: response => console.log(response),
          error: error => console.log(error),
        })
      }
    })

    this.destroyRef.onDestroy(() => {
      subscription.unsubscribe()
    })
  }


}
