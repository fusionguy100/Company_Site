import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth';

export const loginGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const authService = inject(AuthService);

  if (authService.isLoggedIn()) {
    // User is logged in, redirect to appropriate page
    const user = authService.getCurrentUser();
    if (user?.admin) {
      router.navigate(['/select-company']);
    } else {
      router.navigate(['/announcements']);
    }
    return false;
  } else {
    // User is not logged in, allow access to login page
    return true;
  }
};
