import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Navbar } from '../navbar/navbar';
import { User, UserRequestDto } from '../../models';
import { AddUserModal } from '../add-user-modal/add-user-modal';
import { CommonModule } from '@angular/common';
import { CompanyStateService } from '../../services/company-state.service';

@Component({
  selector: 'app-user-registry',
  imports: [Navbar, AddUserModal, CommonModule],
  templateUrl: './user-registry.html',
  styleUrl: './user-registry.css'
})
export class UserRegistry {

  users: User[] = [];
  showAddUserModal = false;
  constructor(private http: HttpClient, private companyState: CompanyStateService) { }

  ngOnInit() {
    // Initialization logic here
    this.loadUsers();
  }

  loadUsers() {
    // Load only users for the selected company
    const company = this.companyState.getCompany();
    if (!company) {
      // Guard should prevent this route, but keep a safe fallback
      this.users = [];
      console.warn('No company selected; users list is empty.');
      return;
    }

    this.http
      .get<User[]>(`http://localhost:8080/companies/${company.id}/users`)
      .subscribe(data => {
        this.users = data;
        console.log('Company users loaded:', this.users);
      });
  }

  ngOnDestroy() {
    // Cleanup logic here
  }

  openAddUser() {
    this.showAddUserModal = true;
  }
  closeAddUserModal() {
    this.showAddUserModal = false;
    // this.loadUsers();
  }

  onUserAdded(userData: UserRequestDto) {
    console.log('Sending user data to backend:', userData);
    const company = this.companyState.getCompany();
    if (!company) {
      console.error('Cannot add user: no company selected');
      return;
    }

    this.http.post<User>('http://localhost:8080/users', userData).subscribe({
      next: (newUser) => {
        console.log('User created successfully:', newUser);
        // Associate the new user with the selected company so they appear in the filtered list
        this.http
          .post<User>(`http://localhost:8080/companies/${company.id}/users`, null, { params: { userId: String(newUser.id) } })
          .subscribe({
            next: () => {
              console.log('User added to company');
              this.closeAddUserModal();
              this.loadUsers();
            },
            error: (err) => {
              console.error('Error adding user to company:', err);
              // Still close modal, but keep UX flexible: you may choose to keep it open
              this.closeAddUserModal();
              this.loadUsers();
            }
          });
      },
      error: (error) => {
        console.error('Error creating user:', error);
        // Handle error - you might want to show an error message in the modal
      }
    });
  }

}
