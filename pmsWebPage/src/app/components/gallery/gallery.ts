import { Component, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { OtherService } from '../../services/other-service';
import { GalleryImage } from '../../models/galleryImage.model';
import { Carousel } from './carousel/carousel';

@Component({
  selector: 'app-gallery',
  imports: [Carousel],
  templateUrl: './gallery.html',
  styleUrl: './gallery.scss'
})
export class Gallery implements OnInit {
  private otherService = inject(OtherService)
  private destroyRef = inject(DestroyRef)

  galleryImages = signal<GalleryImage[]>([])
  showCarousel = signal<boolean>(false)

  ngOnInit(): void {
    const subscription = this.otherService.getAllGalleryImages().subscribe({
      next: response => this.galleryImages.set(response),
      complete: () => console.log(this.galleryImages())
    })

    this.destroyRef.onDestroy(() => {
      subscription.unsubscribe()
    })
  }

  openCarousel(selectedImg: GalleryImage){
    this.otherService.selectedImgForCarousel.set(selectedImg)
    this.showCarousel.set(true)
  }

  closeCarousel(){
    this.showCarousel.set(false)
  }
}
