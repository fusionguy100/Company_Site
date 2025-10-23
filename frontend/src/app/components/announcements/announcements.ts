import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CompanyStateService } from '../../services/company-state.service';
import { Navbar } from '../navbar/navbar';
import { Company } from '../../models/company.model';

@Component({
  selector: 'app-announcements',
  standalone: true,
  imports: [CommonModule, Navbar],
  templateUrl: './announcements.html',
  styleUrls: ['./announcements.css']
})
export class Announcements implements OnInit {
  company: Company | null = null;

  constructor(private companyState: CompanyStateService, private http: HttpClient) {}

  ngOnInit(): void {
    // Get the currently selected company
    this.company = this.companyState.getCompany();
    console.log('Selected company:', this.company);
  }
}
