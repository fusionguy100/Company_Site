import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TeamService {
  private apiUrl = 'http://localhost:8080/teams';

  constructor(private http: HttpClient) {}

getTeamsByCompany(companyId: number): Observable<TeamResponseDto[]> {
  return this.http.get<TeamResponseDto[]>(`http://localhost:8080/companies/${companyId}/teams`, {
    withCredentials: true
  });
}

  getAllTeams(): Observable<TeamResponseDto[]> {
    return this.http.get<TeamResponseDto[]>(this.apiUrl, { withCredentials: true });
  }

  getTeamById(teamId: number): Observable<TeamResponseDto> {
    return this.http.get<TeamResponseDto>(`${this.apiUrl}/${teamId}`, { withCredentials: true });
  }


  createTeam(teamRequest: TeamRequestDto): Observable<TeamResponseDto> {
    return this.http.post<TeamResponseDto>(
      this.apiUrl,
      teamRequest,
      { withCredentials: true }
    );
  }

  updateTeam(teamId: number, team: TeamRequestDto): Observable<TeamResponseDto> {
    return this.http.patch<TeamResponseDto>(
      `${this.apiUrl}/${teamId}`,
      team,
      { withCredentials: true }
    );
  }

  addUserToTeam(teamId: number, userId: number): Observable<TeamResponseDto> {
    return this.http.post<TeamResponseDto>(
      `${this.apiUrl}/${teamId}/users/${userId}`,
      {},
      { withCredentials: true }
    );
  }



  removeUserFromTeam(teamId: number, userId: number): Observable<TeamResponseDto> {
    return this.http.delete<TeamResponseDto>(
      `${this.apiUrl}/${teamId}/users/${userId}`,
      { withCredentials: true }
    );
  }


  deleteTeam(teamId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${teamId}`, { withCredentials: true });
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
