import { Component, EventEmitter, Input, Output, OnChanges, SimpleChanges, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';

interface Project {
  id: number;
  name: string;
  description: string;
  active: boolean;
  team: { id: number; name?: string; description?: string };
}

interface ProjectUpdateDto {
  id: number;
  name: string;
  description: string;
  active: boolean;
  team: number;
}

@Component({
  selector: 'app-edit-project-modal',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './edit-project-modal.html',
  styleUrls: ['./edit-project-modal.css']
})
export class EditProjectModal implements OnChanges, OnInit {
  @Input({ required: true }) project!: Project | null;
  @Output() close = new EventEmitter<void>();
  @Output() updated = new EventEmitter<ProjectUpdateDto>();

  form: FormGroup;

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(120)]],
      description: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(225)]],
      active: [true, [Validators.required]]
    });
  }

  ngOnInit(): void {
    if (this.project) this.patchForm(this.project);
  }

  ngOnChanges(changes: SimpleChanges): void {
    const p = changes['project']?.currentValue as Project | null;
    if (p) this.patchForm(p);
  }

  private patchForm(p: Project) {
    this.form.patchValue({
      name: p.name ?? '',
      description: p.description ?? '',
      active: p.active ?? true
    });
  }

  onSubmit() {
  if (this.form.invalid) return;
  if (!this.project || this.project === null) {
    console.warn('Tried to submit before project was loaded');
    return;
  }

  const { name, description, active } = this.form.value;
  const dto: ProjectUpdateDto = {
    id: this.project.id,        
    name,
    description,
    active,
    team: this.project.team?.id ?? (() => { console.warn('Project has no team - aborting update'); return null; })()
  };

  // If team is missing, don't emit an invalid DTO
  if (dto.team === null) return;

  this.updated.emit(dto as ProjectUpdateDto);
}


  onClose() {
    this.close.emit();
  }
}
