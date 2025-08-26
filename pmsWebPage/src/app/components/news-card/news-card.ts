import { Component, input, signal } from '@angular/core';
import { NewsDetails } from '../../models/newsDetails.model';

@Component({
  selector: 'app-news-card',
  imports: [],
  templateUrl: './news-card.html',
  styleUrl: './news-card.scss'
})
export class NewsCard {
  newsDetails = input.required<NewsDetails>()
  isExpand = signal<boolean>(false)

  expandContent(){
    this.isExpand.update(old => !old);
  }
}
