import { Component, input } from '@angular/core';
import { NewsDetails } from '../../.models/newsDetails.model';

@Component({
  selector: 'app-news-card',
  imports: [],
  templateUrl: './news-card.html',
  styleUrl: './news-card.scss'
})
export class NewsCard {
  newsDetails = input.required<NewsDetails>()
}
