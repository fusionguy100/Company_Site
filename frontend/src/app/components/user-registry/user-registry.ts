import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Navbar } from '../navbar/navbar';
import { User, UserRequestDto } from '../../models';
import { AddUserModal } from '../add-user-modal/add-user-modal';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-user-registry',
  imports: [Navbar, AddUserModal, CommonModule],
  templateUrl: './user-registry.html',
  styleUrl: './user-registry.css'
})
export class UserRegistry {

  users: User[] = [
    {
      id: 1,
      profile: { firstName: 'John', lastName: 'Doe', email: 'john.doe@example.com' },
      admin: false,
      active: true,
      status: 'JOINED'
    }
    ,
    {
      id: 2,
      profile: { firstName: 'Jane', lastName: 'Smith', email: 'jane.smith@example.com' },
      admin: true,
      active: false,
      status: 'PENDING'
    },
    {
      id: 3,
      profile: { firstName: 'Alice', lastName: 'Johnson', email: 'alice.johnson@example.com' },
      admin: false,
      active: true,
      status: 'JOINED'
    }
  ];
  showAddUserModal = false;
  constructor(private http: HttpClient) { }

  ngOnInit() {
    // Initialization logic here
    this.loadUsers();
  }

  loadUsers() {
    // Load users logic here
    this.http.get<User[]>('http://localhost:8080/users').subscribe(data => {
      this.users = data;
      console.log('Users loaded:', this.users);
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
    this.http.post<User>('http://localhost:8080/users', userData).subscribe({
      next: (newUser) => {
        console.log('User created successfully:', newUser);
        this.closeAddUserModal();
        this.loadUsers(); // Reload the entire list
      },
      error: (error) => {
        console.error('Error creating user:', error);
        // Handle error - you might want to show an error message in the modal
      }
    });
  }

}
