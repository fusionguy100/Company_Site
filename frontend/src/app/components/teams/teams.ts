import { Component, OnInit } from '@angular/core';
import { Navbar } from '../navbar/navbar';
import { TeamService, TeamResponseDto } from '../../services/team-service';

@Component({
  selector: 'app-teams',
  standalone: true,
  imports: [Navbar],
  templateUrl: './teams.html',
  styleUrls: ['./teams.css']
})
export class Teams implements OnInit {
  teams: TeamResponseDto[] = [];

  constructor(private teamService: TeamService) {}

  ngOnInit(): void {
    this.loadTeams();
  }

  loadTeams(): void {
    this.teamService.getAllTeams().subscribe({
      next: (data) => (this.teams = data),
      error: (err) => console.error('Error loading teams:', err)
    });
  }

  createNewTeam(): void {
    alert('Create new team clicked!');
  }
}
