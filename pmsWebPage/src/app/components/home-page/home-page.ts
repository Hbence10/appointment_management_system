import { Component, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { NewsDetails } from '../../models/newsDetails.model';
import { NewsService } from '../../services/news-service';
import { NewsCard } from '../news-card/news-card';


@Component({
  selector: 'app-home-page',
  imports: [MatButtonModule, NewsCard],
  templateUrl: './home-page.html',
  styleUrl: './home-page.scss'
})

export class HomePage implements OnInit {
  news = signal<NewsDetails[]>([])
  private newsService = inject(NewsService)
  private destroyRef = inject(DestroyRef)

  ngOnInit(): void {
    const subscription = this.newsService.getAllNews().subscribe({
      next: response => this.news.set(response),
      complete: () => console.log(this.news())
    })

    this.destroyRef.onDestroy(() => {
      subscription.unsubscribe()
      console.log("destoryed!!! - homePage Component")
    })
  }

}
