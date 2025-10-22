import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CompanyStateService, Company } from '../../services/company-state.service';

@Component({
  selector: 'app-announcements',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './announcements.html',
  styleUrls: ['./announcements.css']
})
export class Announcements implements OnInit {
  company: Company | null = null;

  constructor(private companyState: CompanyStateService) {}

  ngOnInit(): void {
    // Get the currently selected company
    this.company = this.companyState.getCompany();
    console.log('Selected company:', this.company);
  }
}
