import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { EditProjectModal } from '../edit-project-modal/edit-project-modal';
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
  selector: 'app-project-card', 
  standalone: true,      
  imports: [CommonModule, EditProjectModal],
  templateUrl: './project-card.html',
  styleUrls: ['./project-card.css']
})
export class ProjectCard {

  constructor(private http: HttpClient) { }

  error = '';
  editingProject: Project | null = null;
  @Input({ required: true }) project!: {
    id: number;
    name: string;
    description: string;
    active: boolean;
    team: { id: number, name: string, description: string }
  }

  openEdit(p: Project) {
    this.editingProject = p;
  }

  onProjectUpdated(dto: ProjectRequestDto) {
    const id = this.project?.id;
    if (!id) return;

    const url = `http://localhost:8080/projects/${id}`;

    this.http.patch<Project>(url, dto, { withCredentials: true }).subscribe({
      next: (updated) => {
        this.project = updated ?? { ...this.project!, ...dto, id };
        this.error = '';
        this.editingProject = null;
      },
      error: (err) => {
        this.error = 'Could not update project.';
        console.error(err);
      },
    });
  }


}


