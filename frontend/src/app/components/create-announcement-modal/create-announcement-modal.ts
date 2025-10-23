import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';

export interface AnnouncementRequestDto {
  title: string;
  content: string;
  company: number;  
}

@Component({
  selector: 'app-create-announcement-modal',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './create-announcement-modal.html',
  styleUrl: './create-announcement-modal.css'
})
export class CreateAnnouncementModal {
  @Input({ required: true }) companyId!: number;
  @Input() companyName?: string;

  @Output() close = new EventEmitter<void>();
  @Output() created = new EventEmitter<AnnouncementRequestDto>();

  form: FormGroup;

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      title: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(120)]],
      content: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(225)]],
    });
  }

  onSubmit() {
    if (this.form.invalid) return;

    const { title, content } = this.form.value;
    const dto: AnnouncementRequestDto = {
      title,
      content,
      company: this.companyId
    };

    // emit to parent; parent handles POST to /announcements
    this.created.emit(dto);
    this.close.emit();
  }

  onClose() {
    this.close.emit();
  }

}
