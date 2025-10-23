import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { CompanyStateService } from '../../services/company-state.service';
import { Navbar } from '../navbar/navbar';
import { AnnouncementCard } from '../announcement-card/announcement-card';
import { Subscription } from 'rxjs';
import { CreateAnnouncementModal } from '../create-announcement-modal/create-announcement-modal';
import { AuthService } from '../../services/auth';
import { Company } from '../../models/company.model';
import { ProjectCard } from '../project-card/project-card';
import { CreateProjectModal } from '../create-project-modal/create-project-modal';
import { ActivatedRoute } from '@angular/router';

interface Project {
  id: number;
  name: string;
  description: string;
  active: boolean;
  team: { id: number, name: string, description: string }
}

interface ProjectRequestDto {
  name: string;
  description: string;
  active: boolean;
  team: number
}

@Component({
  selector: 'app-projects',
  imports: [CommonModule, Navbar, ProjectCard, CreateProjectModal],
  templateUrl: './projects.html',
  styleUrl: './projects.css'
})
export class Projects {
  teamId: number = 0;
  teamName: string = '';
  projects: Project[] = [];
  error = '';
  private sub?: Subscription;
  showCreateModal = false;
  isAdmin = false;

  constructor(private route: ActivatedRoute,  private companyState: CompanyStateService, private http: HttpClient, private authService: AuthService) { }

  ngOnInit(): void {
    this.isAdmin = this.authService.isAdmin();
    this.route.queryParams.subscribe(params => {
      const id = Number(params['teamId']);
      const name = String(params['teamName']);
      if (id && !isNaN(id) && name) {
        this.teamId = id;
        this.teamName = name;
        this.getProjects(this.teamId);
      } else {
        this.error = 'No team selected.';
        this.projects = [];
      }
    });
  }

  ngOnDestroy(): void {
    this.sub?.unsubscribe();
  }


  private getProjects(teamId: number) {
    const url = `http://localhost:8080/teams/${teamId}/projects`;
    this.http.get<Project[]>(url, {
      withCredentials: true
    }).subscribe({
      next: (data) => {
        this.projects = data;
        this.error = '';
        console.log(this.projects);
      },
      error: (err) => {
        this.error = 'Could not load projects.';
        console.error(err);
      },
    });
  }

  openCreateModal() {
    this.showCreateModal = true;
  }

  onCreateProject(projectDto: ProjectRequestDto) {
    const url = `http://localhost:8080/projects`;
    this.http.post<Project>(url, projectDto).subscribe({
      next: (newProject) => {
        console.log('Project created successfully:', newProject);
        this.showCreateModal = false;
        this.getProjects(this.teamId);
      },
      error: (error) => {
        console.error('Error creating project:', error);
      }
    });
  }


}
