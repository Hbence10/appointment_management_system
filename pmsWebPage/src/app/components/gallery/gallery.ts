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
  columns: Gallery[][] = []

  ngOnInit(): void {
    this.columns = []
    const subscription = this.galleryService.getAllGalleryImages().subscribe({
      next: responseList => {
        this.galleryService.galleryImages = responseList.map(response => Object.assign(new Gallery(), response))
      },
      error: error => console.log(error),
      complete: () => {
        for (let i: number = 0; i < this.galleryService.galleryImages.length; i+=3){
          const column: Gallery[] = []
          for (let j: number = i; j < i+3; j++){
            if (this.galleryService.galleryImages[j] != undefined) {
              column.push(this.galleryService.galleryImages[j])
            }
          }
          this.columns.push(column)
        }

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
}
