// components/announcements/announcements.ts
import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { CompanyStateService } from '../../services/company-state.service';
import { Navbar } from '../navbar/navbar';
import { AnnouncementCard } from '../announcement-card/announcement-card';
import { Subscription } from 'rxjs';
import { CreateAnnouncementModal } from '../create-announcement-modal/create-announcement-modal';
import { AuthService } from '../../services/auth';
import { Company } from '../../models/company.model';

interface Announcement {
  id: number;
  date: string;
  title: string;
  content: string;
  company: { id: number, name: string, description: string },
  author: { id: number, username: string, firstName: string, lastName: string, admin: boolean, active: boolean }
}

interface AnnouncementRequestDto {
  title: string;
  content: string;
  company: number;
}

@Component({
  selector: 'app-announcements',
  standalone: true,
  imports: [CommonModule, Navbar, AnnouncementCard, CreateAnnouncementModal],
  templateUrl: './announcements.html',
  styleUrls: ['./announcements.css']
})
export class Announcements implements OnInit, OnDestroy {
  company: Company | null = null;
  announcements: Announcement[] = [];
  error = '';
  private sub?: Subscription;
  showModal = false;
  isAdmin = false;

  constructor(private companyState: CompanyStateService, private http: HttpClient, private authService: AuthService) { }

  ngOnInit(): void {
    this.isAdmin = this.authService.isAdmin();
    this.sub = this.companyState.selectedCompany$.subscribe((c) => {
      this.company = c;
      if (c) this.getAnnouncements(c.id);
      else {
        this.announcements = [];
        this.error = 'No company selected.';
      }
    });
  }

  ngOnDestroy(): void {
    this.sub?.unsubscribe();
  }

  private getAnnouncements(companyId: number) {
    const url = `http://localhost:8080/companies/${companyId}/announcements`;
    this.http.get<Announcement[]>(url, {
      withCredentials: true
    }).subscribe({
      next: (data) => {
        this.announcements = data;
        this.error = '';
      },
      error: (err) => {
        this.error = 'Could not load announcements.';
        console.error(err);
      },
    });
  }

  openModal(){
    this.showModal = true;
  }

  onCreateAnnouncement(announcementDto: AnnouncementRequestDto) {
    const url = `http://localhost:8080/announcements`;
    this.http.post<Announcement>(url, announcementDto).subscribe({
      next: (newAnnouncement) => {
        console.log('Announcment created successfully:', newAnnouncement);
        this.showModal = false;
         if (this.company) {
          this.getAnnouncements(this.company.id);
        }
      },
      error: (error) => {
        console.error('Error creating announcement:', error);
      }
    });
  }
}
