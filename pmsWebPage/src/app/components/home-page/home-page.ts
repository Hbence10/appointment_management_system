import { ChangeDetectionStrategy, Component, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatExpansionModule, MatExpansionPanel } from '@angular/material/expansion';
import { RouterModule } from '@angular/router';
import { News } from '../../models/newsDetails.model';
import { NewsService } from '../../services/news-service';
import { MatBadge } from "@angular/material/badge";


@Component({
  selector: 'app-home-page',
  imports: [MatButtonModule, RouterModule, MatExpansionModule, MatBadge],
  templateUrl: './home-page.html',
  styleUrl: './home-page.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
  viewProviders: [MatExpansionPanel]
})

export class HomePage implements OnInit {
  news = signal<News[]>([])
  private newsService = inject(NewsService)
  private destroyRef = inject(DestroyRef)

  ngOnInit(): void {
    const subscription = this.newsService.getAllNews().subscribe({
      next: responseList => {
        responseList.forEach(response => {
          this.news.update(old => [...old, Object.assign(new News(), response)])
        })

      },
    })

    this.destroyRef.onDestroy(() => {
      subscription.unsubscribe()
    })
  }

}
