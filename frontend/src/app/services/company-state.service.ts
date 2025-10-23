// services/company-state.service.ts
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Company } from '../models/company.model';

const STORAGE_KEY = 'selectedCompany';

@Injectable({ providedIn: 'root' })
export class CompanyStateService {
  private selectedCompanySubject = new BehaviorSubject<Company | null>(this.loadInitial());
  selectedCompany$ = this.selectedCompanySubject.asObservable();

  private loadInitial(): Company | null {
    try {
      const raw = sessionStorage.getItem(STORAGE_KEY);
      return raw ? JSON.parse(raw) as Company : null;
    } catch {
      return null;
    }
  }

  setCompany(company: Company) {
    this.selectedCompanySubject.next(company);
    sessionStorage.setItem(STORAGE_KEY, JSON.stringify(company));
  }

  getCompany(): Company | null {
    return this.selectedCompanySubject.getValue();
  }

  clearCompany() {
    this.selectedCompanySubject.next(null);
    sessionStorage.removeItem(STORAGE_KEY);
  }
}
