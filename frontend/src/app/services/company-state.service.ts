import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Company } from '../models/company.model';

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
