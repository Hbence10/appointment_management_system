import { Component, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { Gallery } from '../../models/galleryImage.model';
import { OtherService } from '../../services/other-service';
import { Carousel } from './carousel/carousel';
import { GalleryService } from '../../services/gallery-service';

@Component({
  selector: 'app-gallery',
  imports: [Carousel],
  templateUrl: './gallery.html',
  styleUrl: './gallery.scss'
})
export class GalleryPage implements OnInit {
  galleryService = inject(GalleryService)
  private destroyRef = inject(DestroyRef)
  showCarousel = signal<boolean>(false)

  ngOnInit(): void {
    const subscription = this.galleryService.getAllGalleryImages().subscribe({
      next: responseList => {
        console.log(responseList)
        this.galleryService.galleryImages = responseList.map(response => Object.assign(new Gallery(), response))
        console.log(this.galleryService.galleryImages)
      }
    })

    this.destroyRef.onDestroy(() => {
      subscription.unsubscribe()
    })
  }

  openCarousel(selectedImg: Gallery) {
    this.galleryService.selectedImgForCarousel.set(selectedImg)
    this.showCarousel.set(true)
  }

  closeCarousel() {
    this.showCarousel.set(false)
  }
}
