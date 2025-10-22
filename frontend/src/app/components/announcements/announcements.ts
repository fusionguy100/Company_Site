import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { CompanyStateService, Company } from '../../services/company-state.service';
import { Navbar } from '../navbar/navbar';
import { AnnouncementCard } from '../announcement-card/announcement-card';

interface Announcement {
  id: number,
  date: string,
  title: string,
  message: string,
  company: number,
  author: number
}

@Component({
  selector: 'app-announcements',
  standalone: true,
  imports: [CommonModule, Navbar, AnnouncementCard],
  templateUrl: './announcements.html',
  styleUrls: ['./announcements.css']
})
export class Announcements implements OnInit {
  company: Company | null = null;
  announcements: Announcement[] = [];
  error = '';


  constructor(private companyState: CompanyStateService, private http: HttpClient) { }

  ngOnInit(): void {
    this.company = this.companyState.getCompany();
    console.log('Selected company:', this.company);
  }

  getAnnouncements() {
    const url = `https://localhost:8080/companies/${this.company?.id}/announcements`;
    this.http.get<Announcement[]>(url).subscribe({
      next: (data) => {
        this.announcements = data;
      },
      error: (err) => {
        this.error = 'Could not load announcements.';
        console.error(err);
      },
    });

  }
}
