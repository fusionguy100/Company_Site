import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Navbar } from '../navbar/navbar';

interface Company {
  id: number;
  name: string;
  address?: string;
  description?: string;
}

@Component({
  selector: 'app-select-company',
  standalone: true,
  imports: [CommonModule, FormsModule, Navbar],
  templateUrl: './select-company.html',
  styleUrls: ['./select-company.css']
})
export class SelectCompany implements OnInit {
  companies: Company[] = [];
  selectedCompanyId: number | null = null;
  loading = false;
  error: string | null = null;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.fetchCompanies();
  }

  fetchCompanies(): void {
    this.loading = true;
    this.error = null;

    this.http.get<Company[]>('http://localhost:8080/companies')
      .subscribe({
        next: (data) => {
          this.companies = data;
          this.loading = false;
        },
        error: (err) => {
          this.error = 'Failed to load companies';
          console.error(err);
          this.loading = false;
        }
      });
  }

  onSelectCompany(): void {
    console.log('Selected company ID:', this.selectedCompanyId);
    // You can navigate or store the selected company here
  }
}
