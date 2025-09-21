import { Component, DestroyRef, inject, input, output, signal } from '@angular/core';
import { GalleryImage } from '../../../models/galleryImage.model';
import { OtherService } from '../../../services/other-service';

@Component({
  selector: 'app-carousel',
  imports: [],
  templateUrl: './carousel.html',
  styleUrl: './carousel.scss'
})
export class Carousel {
  private otherService = inject(OtherService)
  private destroyRef = inject(DestroyRef)

  galleryImages = signal<GalleryImage[]>([])
  closeCarousel = output()
  selectedImg = signal<null | GalleryImage>(null)


  ngOnInit(): void {
    this.selectedImg.set(this.otherService.selectedImgForCarousel());
    console.log(this.selectedImg())

    const subscription = this.otherService.getAllGalleryImages().subscribe({
      next: response => this.galleryImages.set(response),
      complete: () => console.log(this.galleryImages())
    })

    this.destroyRef.onDestroy(() => {
      subscription.unsubscribe()
    })
  }

  switchImage(nextIndex: 1 | -1){
    let actualIndex = this.galleryImages().indexOf(this.selectedImg()!)
    if(actualIndex + nextIndex == this.galleryImages().length){
      this.selectedImg.set(this.galleryImages()[0])
    } else if(actualIndex + nextIndex == -1){
      this.selectedImg.set(this.galleryImages()[7])
    } else {
      this.selectedImg.set(this.galleryImages()[actualIndex + nextIndex])
    }
  }

  close(){
    this.closeCarousel.emit()
  }
}
