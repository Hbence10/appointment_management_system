import { Component, input, OnInit } from '@angular/core';
import { CardItem } from '../../models/card.model';

@Component({
  selector: 'app-list-card',
  imports: [],
  templateUrl: './list-card.html',
  styleUrl: './list-card.scss'
})
export class ListCard implements OnInit{
  cardItem = input.required<CardItem>()
  eventsTypeList: string[] = []

  ngOnInit(): void {

  }
}
