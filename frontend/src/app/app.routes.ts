import { Routes } from '@angular/router';
import { Login } from './components/login/login';
import { SelectCompany } from './components/select-company/select-company';
import { authGuard } from './guards/auth-guard';
import { loginGuard } from './guards/login-guard';
import { adminGuard } from './guards/admin-guard-guard';
import { Announcements } from './components/announcements/announcements';
import { Teams } from './components/teams/teams';
import { UserRegistry } from './components/user-registry/user-registry';

export const routes: Routes = [
  { path: 'login', component: Login, canActivate: [loginGuard] },
  { path: 'select-company', component: SelectCompany, canActivate: [authGuard, adminGuard] },
  { path: 'announcements', component: Announcements, canActivate: [authGuard] },
  { path: 'user-registry', component: UserRegistry, canActivate: [authGuard, adminGuard] },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: '**', redirectTo: '/login' }
];
