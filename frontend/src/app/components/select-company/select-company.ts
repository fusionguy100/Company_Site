import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { CompanyStateService } from '../../services/company-state.service';
import { Navbar } from '../navbar/navbar';
import { Company } from '../../models/company.model';

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

  constructor(
    private http: HttpClient,
    private companyState: CompanyStateService,
    private router: Router
  ) {}

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
          console.error(err);
          this.error = 'Failed to load companies';
          this.loading = false;
        }
      });
  }

  onSelectCompany(): void {
    const company = this.companies.find(c => c.id === this.selectedCompanyId);
    if (company) {
      this.companyState.setCompany(company);
      this.router.navigate(['/announcements']);
    }
  }
}
