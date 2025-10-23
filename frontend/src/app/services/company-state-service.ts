import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Company } from '../models/company.model';

@Injectable({
  providedIn: 'root'
})
export class CompanyStateService {
  private selectedCompanySubject = new BehaviorSubject<Company | null>(null);
  selectedCompany$ = this.selectedCompanySubject.asObservable();

  setCompany(company: Company): void {
    this.selectedCompanySubject.next(company);
    localStorage.setItem('selectedCompany', JSON.stringify(company));
  }

  getCompany(): Company | null {
    const stored = localStorage.getItem('selectedCompany');
    return stored ? JSON.parse(stored) : this.selectedCompanySubject.value;
  }

  clearCompany(): void {
    localStorage.removeItem('selectedCompany');
    this.selectedCompanySubject.next(null);
  }
}
