import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';

interface ProjectRequestDto {
  name: string;
  description: string;
  active: boolean;
  team: number
}


@Component({
  selector: 'app-create-project-modal',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './create-project-modal.html',
  styleUrl: './create-project-modal.css'
})
export class CreateProjectModal {
  @Input({ required: true }) teamId!: number;

  @Output() close = new EventEmitter<void>();
  @Output() created = new EventEmitter<ProjectRequestDto>();

  form: FormGroup;

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(120)]],
      description: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(225)]],
    });
  }

  onSubmit() {
    if (this.form.invalid) return;

    const { name, description } = this.form.value;
    const dto: ProjectRequestDto = {
      name,
      description,
      active: true,
      team: this.teamId
    };

    // emit to parent; parent handles POST to /projects
    this.created.emit(dto);
    this.close.emit();
  }

  onClose() {
    this.close.emit();
  }

}
