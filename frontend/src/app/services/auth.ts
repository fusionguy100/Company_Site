import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { User, Credentials } from '../models';


@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8080/auth';
  private currentUserSubject = new BehaviorSubject<User | null>(null);
  public currentUser = this.currentUserSubject.asObservable();

  constructor(private http: HttpClient) {
    this.loadUserFromStorage();
  }

  login(credentials: Credentials) {
    return this.http.post<User>(`${this.baseUrl}/login`, credentials, {withCredentials: true}).pipe(
      tap((user: User) => {
        this.currentUserSubject.next(user);
        localStorage.setItem('currentUser', JSON.stringify(user));
      })
    );
  }

  logout() : Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/logout`, {}, {withCredentials: true}).pipe(
      tap(() => {
        this.currentUserSubject.next(null);
        localStorage.removeItem('currentUser');
      })
    );
  }

  getCurrentUser(): User | null {
    return this.currentUserSubject.value;
  }

  isLoggedIn(): boolean {
    return this.currentUserSubject.value !== null;
  }

  isAdmin(): boolean {
    const user = this.currentUserSubject.value;
    return user ? user.admin : false;
  }

  private loadUserFromStorage() {
    const userJson = localStorage.getItem('currentUser');
    if (userJson) {
      try {
        this.currentUserSubject.next(JSON.parse(userJson));
      } catch (error) {
        console.error('Error parsing user JSON from localStorage', error);
        localStorage.removeItem('currentUser');
      }
    }
  }
}
