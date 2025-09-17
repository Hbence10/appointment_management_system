import { Component, input } from '@angular/core';
import { CardItem } from '../../models/card.model';

@Component({
  selector: 'app-list-card',
  imports: [],
  templateUrl: './list-card.html',
  styleUrl: './list-card.scss'
})
export class ListCard {
  cardItem = input.required<CardItem>()
}
