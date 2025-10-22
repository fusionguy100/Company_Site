import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

/**
 * TeamService — handles all HTTP requests to the /teams backend endpoints.
 * Includes { withCredentials: true } for session-based authentication using HttpSession.
 */
@Injectable({
  providedIn: 'root'
})
export class TeamService {
  private apiUrl = 'http://localhost:8080/teams'; // backend base URL

  constructor(private http: HttpClient) {}


  getAllTeams(): Observable<TeamResponseDto[]> {
    return this.http.get<TeamResponseDto[]>(this.apiUrl, { withCredentials: true });
  }

  getTeamById(teamId: number): Observable<TeamResponseDto> {
    return this.http.get<TeamResponseDto>(`${this.apiUrl}/${teamId}`, { withCredentials: true });
  }

  createTeam(userId: number, teamRequest: TeamRequestDto): Observable<TeamResponseDto> {
    return this.http.post<TeamResponseDto>(
      `${this.apiUrl}?userId=${userId}`,
      teamRequest,
      { withCredentials: true }
    );
  }


  updateTeam(teamId: number, currentUserId: number, teamRequest: Partial<TeamRequestDto>): Observable<TeamResponseDto> {
    return this.http.patch<TeamResponseDto>(
      `${this.apiUrl}/${teamId}?currentUserId=${currentUserId}`,
      teamRequest,
      { withCredentials: true }
    );
  }


  addUserToTeam(teamId: number, userId: number, currentUserId: number): Observable<TeamResponseDto> {
    // Backend expects both userId and currentUserId as query params
    return this.http.post<TeamResponseDto>(
      `${this.apiUrl}/${teamId}/users/${userId}?currentUserId=${currentUserId}`,
      {},
      { withCredentials: true }
    );
  }

  /** Remove a user from a team (admin only) */
  removeUserFromTeam(teamId: number, userId: number): Observable<UserResponseDto> {
    return this.http.delete<UserResponseDto>(
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
  company: number; // company ID
}

export interface CompanyResponseDto {
  id: number;
  name: string;
  description: string;
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
  users: UserResponseDto[];
  projects: ProjectResponseDto[];
}
