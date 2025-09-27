import { Component, input, output, signal } from '@angular/core';
import { NewsDetails } from '../../models/newsDetails.model';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-news-card',
  imports: [MatButtonModule, MatIconModule],
  templateUrl: './news-card.html',
  styleUrl: './news-card.scss'
})
export class NewsCard {
  newsDetails = input.required<NewsDetails>()
  expand = output()

  expandContent(){
    this.expand.emit()
  }

}
