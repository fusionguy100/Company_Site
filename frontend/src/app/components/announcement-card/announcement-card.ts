import { Component, Input } from '@angular/core';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-announcement-card',
  imports: [DatePipe],
  templateUrl: './announcement-card.html',
  styleUrl: './announcement-card.css'
})
export class AnnouncementCard {

  @Input({ required: true }) announcement!: {
    id: number,
    date: string,
    title: string,
    content: string,
    company: { id: number, name: string, description: string },
    author: { id: number, username: string, firstName: string, lastName: string, admin: boolean, active: boolean }
  };
}


