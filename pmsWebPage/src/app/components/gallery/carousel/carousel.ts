import { Component, inject, input, output, signal } from '@angular/core';
import { Gallery } from '../../../models/galleryImage.model';
import { OtherService } from '../../../services/other-service';
import { GalleryService } from '../../../services/gallery-service';
import { NgClass } from '@angular/common';
// import { NgClass } from "../../../../../node_modules/@angular/common/types/_common_module-chunk";

@Component({
  selector: 'app-carousel',
  imports: [NgClass],
  templateUrl: './carousel.html',
  styleUrl: './carousel.scss'
})
export class Carousel {
  private galleryService = inject(GalleryService)

  galleryImages = signal<Gallery[]>([])
  closeCarousel = output()
  selectedImg = signal<null | Gallery>(null)
  parentComponentName = input.required<"gallery" | "adminPage">()


  ngOnInit(): void {
    this.selectedImg.set(this.galleryService.selectedImgForCarousel());
    this.galleryImages.set(this.galleryService.galleryImages)
  }

  switchImage(nextIndex: 1 | -1) {
    let actualIndex = this.galleryImages().indexOf(this.selectedImg()!)
    if (actualIndex + nextIndex == this.galleryImages().length) {
      this.selectedImg.set(this.galleryImages()[0])
    } else if (actualIndex + nextIndex == -1) {
      this.selectedImg.set(this.galleryImages()[7])
    } else {
      this.selectedImg.set(this.galleryImages()[actualIndex + nextIndex])
    }
  }

  close() {
    this.closeCarousel.emit()
  }
}
