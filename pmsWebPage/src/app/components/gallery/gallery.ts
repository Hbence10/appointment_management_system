import { Component, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { OtherService } from '../../services/other-service';
import { GalleryImage } from '../../models/galleryImage.model';

@Component({
  selector: 'app-gallery',
  imports: [],
  templateUrl: './gallery.html',
  styleUrl: './gallery.scss'
})
export class Gallery implements OnInit {
  private otherService = inject(OtherService)
  private destroyRef = inject(DestroyRef)

  galleryImages = signal<GalleryImage[]>([])

  ngOnInit(): void {
    const subscription = this.otherService.getAllGalleryImages().subscribe({
      next: response => this.galleryImages.set(response),
      complete: () => console.log(this.galleryImages())
    })

    this.destroyRef.onDestroy(() => {
      console.log("destroyed!!! - galleryComponent")
      subscription.unsubscribe()
    })
  }
}
