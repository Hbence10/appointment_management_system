import { Component, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { Gallery } from '../../models/galleryImage.model';
import { OtherService } from '../../services/other-service';
import { Carousel } from './carousel/carousel';

@Component({
  selector: 'app-gallery',
  imports: [Carousel],
  templateUrl: './gallery.html',
  styleUrl: './gallery.scss'
})
export class GalleryPage implements OnInit {
  private otherService = inject(OtherService)
  private destroyRef = inject(DestroyRef)

  galleryImages: Gallery[] = []
  showCarousel = signal<boolean>(false)

  ngOnInit(): void {
    const subscription = this.otherService.getAllGalleryImages().subscribe({
      next: responseList => {
        responseList.forEach(response => {
          this.otherService.galleryImages = responseList.map(response => Object.assign(new Gallery(), response))
        })
      },
      complete: () => this.galleryImages = this.otherService.galleryImages
    })

    this.destroyRef.onDestroy(() => {
      subscription.unsubscribe()
    })
  }

  openCarousel(selectedImg: Gallery) {
    this.otherService.selectedImgForCarousel.set(selectedImg)
    this.showCarousel.set(true)
  }

  closeCarousel() {
    this.showCarousel.set(false)
  }
}
