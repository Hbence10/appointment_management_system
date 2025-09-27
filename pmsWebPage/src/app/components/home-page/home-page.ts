import { ChangeDetectionStrategy, Component, DestroyRef, inject, Injectable, OnInit, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatExpansionModule, MatExpansionPanel } from '@angular/material/expansion';
import { RouterModule } from '@angular/router';
import { NewsDetails } from '../../models/newsDetails.model';
import { NewsService } from '../../services/news-service';


@Component({
  selector: 'app-home-page',
  imports: [MatButtonModule, RouterModule, MatExpansionModule],
  templateUrl: './home-page.html',
  styleUrl: './home-page.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
  viewProviders: [MatExpansionPanel]
})

export class HomePage implements OnInit {
  news = signal<NewsDetails[]>([])
  private newsService = inject(NewsService)
  private destroyRef = inject(DestroyRef)
  openingCheckerList: boolean[] = []

  ngOnInit(): void {
    const subscription = this.newsService.getAllNews().subscribe({
      next: response => this.news.set(response),
      complete: () => {
        this.news().forEach(news => {
          news.isExpand = news.placement == 1 ? true : false
          this.openingCheckerList.push(false)
        })
      }
    })

    this.destroyRef.onDestroy(() => {
      subscription.unsubscribe()
    })
  }

  setNewsCardExpand(selectedNewsDetail: NewsDetails) {
    console.log("sad")
    this.news().forEach(element => {
      element.isExpand = false
    })
    selectedNewsDetail.isExpand = !selectedNewsDetail.isExpand
  }

}
