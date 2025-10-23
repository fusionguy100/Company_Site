import { FormsModule } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { Navbar } from '../navbar/navbar';
import { TeamService } from '../../services/team-service';
import { NgIf, NgFor } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { AuthService} from '../../services/auth';
import { User } from '../../models';
import { CompanyStateService } from '../../services/company-state.service';


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
  allUsersByCompany: UserResponseDto[] = [];

  isAdmin = false;
  currentUser: User | null = null;

  newTeam: TeamRequestDto = {
    name: '',
    description: '',
    company: 1
  };

  constructor(
    private teamService: TeamService,
    private http: HttpClient,
    private authService: AuthService,
    private companyState: CompanyStateService
  ) {}

  ngOnInit(): void {
    this.currentUser = this.authService.getCurrentUser();
    this.isAdmin = this.authService.isAdmin();

    this.loadTeams();
    this.loadAllUsers();
    this.loadAllUsersByCompany(this.companyState.getCompany()?.id || 0);
  }

  loadTeams(): void {
    this.teamService.getAllTeams().subscribe({
      next: (data) => {
        this.teams = data;
        console.log('Teams loaded:', data);
      },
      error: (err) => console.error(' Error loading teams:', err)
    });
  }

  loadAllUsers(): void {
    this.http.get<UserResponseDto[]>('http://localhost:8080/users', { withCredentials: true }).subscribe({
      next: (data) => {
        console.log('Users loaded:', data);
        this.allUsers = data;
      },
      error: (err) => console.error('Error loading users:', err)
    });
  }
  loadAllUsersByCompany(companyId: number): void {
    this.http.get<UserResponseDto[]>(`http://localhost:8080/companies/${companyId}/users`, { withCredentials: true }).subscribe({
      next: (data) => {
        console.log('Users loaded for company:', data);
        this.allUsersByCompany = data;
      },
      error: (err) => console.error('Error loading users for company:', err)
    });
  }

  openModal(): void {
    if (!this.isAdmin) {
      alert('Only admins can create teams.');
      return;
    }
    this.showModal = true;
  }

  closeModal(): void {
    this.showModal = false;
    this.selectedMembers = [];
    this.newTeam = { name: '', description: '', company: 1 };
  }

  addMember(): void {
    const user = this.allUsersByCompany.find(u => u.id === Number(this.selectedUserId));
    if (user && !this.selectedMembers.some(m => m.id === user.id)) {
      this.selectedMembers.push(user);
    }
    this.selectedUserId = '';
  }

  removeMember(member: UserResponseDto): void {
    this.selectedMembers = this.selectedMembers.filter(m => m.id !== member.id);
  }

  submitTeam(): void {
    if (!this.isAdmin) {
      alert('You are not authorized to create teams.');
      return;
    }

    if (!this.newTeam.name.trim()) {
      alert('Team name is required.');
      return;
    }

    const teamRequest: TeamRequestDto = {
      ...this.newTeam,
      userIds: this.selectedMembers.map(m => m.id)
    };

    this.teamService.createTeam(teamRequest).subscribe({
      next: (team) => {
        console.log('Team created successfully:', team);
        this.teams.push(team);
        this.closeModal();
      },
      error: (err) => console.error('Error creating team:', err)
    });
  }

  deleteTeam(teamId: number): void {
    if (!this.isAdmin) {
      alert('Only admins can delete teams.');
      return;
    }

    if (!confirm('Are you sure you want to delete this team?')) return;

    this.teamService.deleteTeam(teamId).subscribe({
      next: () => {
        this.teams = this.teams.filter(t => t.id !== teamId);
        console.log(`🗑 Team ${teamId} deleted`);
      },
      error: (err) => console.error('Error deleting team:', err)
    });
  }

updateTeam(team: TeamResponseDto): void {
  if (!this.isAdmin) {
    alert('Only admins can edit teams.');
    return;
  }


  if (!team.users || team.users.length === 0) {
    alert('A team must have at least one member before editing.');
    return;
  }

  const newName = prompt('Enter new team name:', team.name);
  const newDesc = prompt('Enter new team description:', team.description);

  if (newName && newDesc) {
    const updatedTeam: TeamRequestDto = {
      name: newName.trim(),
      description: newDesc.trim(),
      company: team.company.id,

      userIds: team.users.map(u => u.id)
    };

    this.teamService.updateTeam(team.id, updatedTeam).subscribe({
      next: (updated) => {
        const index = this.teams.findIndex(t => t.id === updated.id);
        if (index !== -1) this.teams[index] = updated;
        console.log('Team updated successfully:', updated);
      },
      error: (err) => console.error('Error updating team:', err)
    });
  }
}
}




export interface TeamRequestDto {
  name: string;
  description: string;
  company: number;
  userIds?: number[];
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
