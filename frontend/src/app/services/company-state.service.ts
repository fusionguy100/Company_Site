import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

export interface Company {
  id: number;
  name: string;
  address?: string;
  description?: string;
}

@Injectable({
  providedIn: 'root'
})
export class CompanyStateService {
  private selectedCompanySubject = new BehaviorSubject<Company | null>(null);
  selectedCompany$ = this.selectedCompanySubject.asObservable();

  setCompany(company: Company) {
    this.selectedCompanySubject.next(company);
  }

  getCompany(): Company | null {
    return this.selectedCompanySubject.getValue();
  }

  clearCompany() {
    this.selectedCompanySubject.next(null);
  }
}
