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

  galleryImages = signal<Gallery[]>([])
  showCarousel = signal<boolean>(false)

  ngOnInit(): void {
    const subscription = this.otherService.getAllGalleryImages().subscribe({
      next: responseList => {
        responseList.forEach(response => {
          this.galleryImages.update(old => [...old, Object.assign(new Gallery(), response)])
        })
      },
      complete: () => console.log(this.galleryImages())
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
