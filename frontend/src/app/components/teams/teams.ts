import { FormsModule } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { Navbar } from '../navbar/navbar';
import { TeamService } from '../../services/team-service';
import { NgIf, NgFor,} from '@angular/common';
import { HttpClient } from '@angular/common/http';


@Component({
  selector: 'app-teams',
  standalone: true,
  imports: [Navbar, NgFor, NgIf, FormsModule],
  templateUrl: './teams.html',
  styleUrls: ['./teams.css']
})
export class Teams implements OnInit {
  teams: TeamResponseDto[] = [];
  allUsers: UserResponseDto[] = [];
  selectedMembers: UserResponseDto[] = [];
  selectedUserId: string = '';
  showModal = false;

  newTeam: TeamRequestDto = {
    name: '',
    description: '',
    company: 1
  };

  constructor(private teamService: TeamService, private http: HttpClient) {}

  ngOnInit(): void {
    this.loadTeams();
    this.loadAllUsers();
  }

  loadTeams(): void {
    this.teamService.getAllTeams().subscribe({
      next: (data) => (this.teams = data),
      error: (err) => console.error('Error loading teams:', err)
    });
  }

  loadAllUsers(): void {
  this.http.get<UserResponseDto[]>('http://localhost:8080/users').subscribe({
    next: (data) => {
      console.log('✅ Users loaded:', data);
      this.allUsers = data;
    },
    error: (err) => console.error('Error loading users:', err)
  });
}

  openModal(): void {
    this.showModal = true;
  }

  closeModal(): void {
    this.showModal = false;
    this.selectedMembers = [];
    this.newTeam = { name: '', description: '', company: 1 };
  }

  addMember(): void {
    const user = this.allUsers.find(u => u.id === Number(this.selectedUserId));
    if (user && !this.selectedMembers.some(m => m.id === user.id)) {
      this.selectedMembers.push(user);
    }
    this.selectedUserId = '';
  }

  removeMember(member: UserResponseDto): void {
    this.selectedMembers = this.selectedMembers.filter(m => m.id !== member.id);
  }

  submitTeam(): void {
    const currentUserId = 1; // admin

    const teamRequest = {
      ...this.newTeam,
      userIds: this.selectedMembers.map(m => m.id)
    };

    this.teamService.createTeam(currentUserId, teamRequest).subscribe({
      next: (team) => {
        this.teams.push(team);
        this.closeModal();
      },
      error: (err) => console.error('Error creating team:', err)
    });
  }
}
export interface TeamRequestDto {
  name: string;
  description: string;
  company: number;
}

export interface CompanyResponseDto {
  id: number;
  name: string;
  description: string;
}

export interface UserSummaryDto {
  id: number;
  username: string;
}

export interface UserResponseDto {
  id: number;
  username: string;
  active: boolean;
  admin: boolean;
  status: string;
}

export interface ProjectResponseDto {
  id: number;
  name: string;
  description: string;
  active: boolean;
}

export interface TeamResponseDto {
  id: number;
  name: string;
  description: string;
  company: CompanyResponseDto;
  users: UserSummaryDto[];
  projects: ProjectResponseDto[];
}
