import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgFor, NgIf } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Navbar } from '../navbar/navbar';
import { TeamService } from '../../services/team-service';
import { AuthService } from '../../services/auth';
import { CompanyStateService } from '../../services/company-state.service';
import { User } from '../../models';
import { Router } from '@angular/router';

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

  // NEW: For editing team members
  showMemberModal = false;
  selectedTeam: TeamResponseDto | null = null;

  isAdmin = false;
  currentUser: User | null = null;
  selectedCompanyId: number | null = null;

  newTeam: TeamRequestDto = {
    name: '',
    description: '',
    company: 0 // set dynamically
  };

  constructor(
    private teamService: TeamService,
    private http: HttpClient,
    private authService: AuthService,
    private companyState: CompanyStateService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.currentUser = this.authService.getCurrentUser();
    this.isAdmin = this.authService.isAdmin();

    // Determine which company to use
    const selectedCompany = this.companyState.getCompany();

    if (this.isAdmin) {
      // Admin manually selects a company
      if (selectedCompany) {
        this.selectedCompanyId = selectedCompany.id;
        this.newTeam.company = selectedCompany.id;
        console.log(`Admin viewing teams for company ID: ${this.selectedCompanyId}`);
      } else {
        console.warn('Admin has not selected a company — redirecting to Select Company.');
        this.router.navigate(['/select-company']);
        return;
      }
    } else {
      // Normal user -> use their own company
      const companyId = this.currentUser?.companies?.[0]?.id;

      if (companyId) {
        this.selectedCompanyId = companyId;
        this.newTeam.company = companyId;
        console.log(`User logged in with company ID: ${companyId}`);
      } else {
        console.error('User has no associated companies!');
        return;
      }
    }

    // Once company determined, load data
    this.loadTeams();
    this.loadAllUsers();
  }
  canAccessTeam(team: TeamResponseDto): boolean {

  if (this.isAdmin) return true;


  return team.users.some(u => u.id === this.currentUser?.id);
}


  goToProjects(teamId: number, teamName: string): void {
    console.log(`Navigating to projects for team ID: ${teamId}`);
    this.router.navigate(['/projects'], { queryParams: { teamId, teamName } });
  }

  loadTeams(): void {
    if (!this.selectedCompanyId) {
      console.error('No company selected.');
      return;
    }

    this.teamService.getTeamsByCompany(this.selectedCompanyId).subscribe({
      next: (data) => {
        this.teams = data;
        console.log(`Loaded teams for company ID ${this.selectedCompanyId}:`, data);
      },
      error: (err) => console.error('Error loading teams:', err)
    });
  }

  loadAllUsers(): void {
    this.http
      .get<UserResponseDto[]>('http://localhost:8080/users', { withCredentials: true })
      .subscribe({
        next: (data) => {
          // Filter users by company
          this.allUsers = data.filter((u) =>
            (u as any).companies?.some((c: any) => c.id === this.selectedCompanyId)
          );
          console.log('Filtered users loaded:', this.allUsers);
        },
        error: (err) => console.error('Error loading users:', err)
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
    this.newTeam = { name: '', description: '', company: 0 };
  }

  addMember(): void {
    const user = this.allUsers.find((u) => u.id === Number(this.selectedUserId));
    if (user && !this.selectedMembers.some((m) => m.id === user.id)) {
      this.selectedMembers.push(user);
    }
    this.selectedUserId = '';
  }

  removeMember(member: UserResponseDto): void {
    this.selectedMembers = this.selectedMembers.filter((m) => m.id !== member.id);
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

    if (this.selectedMembers.length === 0) {
      alert('You must add at least one team member.');
      return;
    }

    const companyId = this.selectedCompanyId;
    if (!companyId) {
      alert('No company found for current user.');
      return;
    }

    const teamRequest: TeamRequestDto = {
      ...this.newTeam,
      company: companyId,
      userIds: this.selectedMembers.map((m) => m.id)
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
        this.teams = this.teams.filter((t) => t.id !== teamId);
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
        userIds: team.users.map((u) => u.id)
      };

      this.teamService.updateTeam(team.id, updatedTeam).subscribe({
        next: (updated) => {
          const index = this.teams.findIndex((t) => t.id === updated.id);
          if (index !== -1) this.teams[index] = updated;
          console.log('Team updated successfully:', updated);
        },
        error: (err) => console.error('Error updating team:', err)
      });
    }
  }



  editMembers(team: TeamResponseDto): void {
    if (!this.isAdmin) {
      alert('Only admins can edit team members.');
      return;
    }
    this.selectedTeam = team;
    this.showMemberModal = true;
  }

  closeMemberModal(): void {
    this.showMemberModal = false;
    this.selectedTeam = null;
    this.selectedUserId = '';
  }

  addMemberToTeam(): void {
    if (!this.selectedTeam || !this.selectedUserId) return;

    const userId = Number(this.selectedUserId);
    this.teamService.addUserToTeam(this.selectedTeam.id, userId).subscribe({
      next: (updated) => {
        this.selectedTeam = updated;
        const index = this.teams.findIndex(t => t.id === updated.id);
        if (index !== -1) this.teams[index] = updated;
        console.log(` Added user ${userId} to ${updated.name}`);
      },
      error: (err) => console.error('Error adding member:', err)
    });

    this.selectedUserId = '';
  }

removeMemberFromTeam(user: UserSummaryDto): void {
  if (!this.selectedTeam) return;

  this.teamService.removeUserFromTeam(this.selectedTeam.id, user.id).subscribe({
    next: (updated: TeamResponseDto) => {
      this.selectedTeam = updated;
      const index = this.teams.findIndex(t => t.id === updated.id);
      if (index !== -1) this.teams[index] = updated;
      console.log(`🗑 Removed ${user.username} from ${updated.name}`);
    },
    error: (err) => console.error('Error removing member:', err)
  });
}
}

/* --- DTO Interfaces --- */

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
