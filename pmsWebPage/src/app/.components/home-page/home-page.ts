import { NewsDetails } from './../../.models/newsDetails.model';
import { NewsCard } from './../news-card/news-card';
import { Component, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';


@Component({
  selector: 'app-home-page',
  imports: [MatButtonModule, NewsCard],
  templateUrl: './home-page.html',
  styleUrl: './home-page.scss'
})

export class HomePage {
  news = signal<NewsDetails[]>([])
}
