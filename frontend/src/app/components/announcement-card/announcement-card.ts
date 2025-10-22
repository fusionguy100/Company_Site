import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-announcement-card',
  imports: [],
  templateUrl: './announcement-card.html',
  styleUrl: './announcement-card.css'
})
export class AnnouncementCard {
  @Input() announcement!: {
    id: number,
    date: string,
    title: string,
    message: string,
    company: number,
    author: number
  };

}
