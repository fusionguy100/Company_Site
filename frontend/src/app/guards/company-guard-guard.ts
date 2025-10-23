import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { CompanyStateService } from '../services/company-state.service';

export const companyGuard: CanActivateFn = (route, state) => {
  const companyState = inject(CompanyStateService);
  const router = inject(Router);

  const selectedCompany = companyState.getCompany();

  if (selectedCompany) {
    return true;
  } else {
    // No company selected, redirect to select-company page
    router.navigate(['/select-company']);
    return false;
  }
};
