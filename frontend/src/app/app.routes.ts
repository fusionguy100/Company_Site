import { Routes } from '@angular/router';
import { Login } from './components/login/login';
import { SelectCompany } from './components/select-company/select-company';
import { authGuard } from './guards/auth-guard';
import { loginGuard } from './guards/login-guard';
import { Announcements } from './components/announcements/announcements';

export const routes: Routes = [
  { path: 'login', component: Login, canActivate: [loginGuard] },
  { path: 'select-company', component: SelectCompany, canActivate: [authGuard] },
  { path: 'announcements', component: Announcements, canActivate: [authGuard] },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: '**', redirectTo: '/login' }
];
