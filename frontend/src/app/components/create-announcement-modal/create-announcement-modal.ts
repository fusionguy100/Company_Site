import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';

export interface AnnouncementRequestDto {
  title: string;
  message: string;
  company: number;   // backend will infer author from session; no author here
}

@Component({
  selector: 'app-create-announcement-modal',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './create-announcement-modal.html',
  styleUrl: './create-announcement-modal.css'
})
export class CreateAnnouncementModal {
  /** company id to post under (required) */
  @Input({ required: true }) companyId!: number;

  /** optional: show the company name in the modal */
  @Input() companyName?: string;

  @Output() close = new EventEmitter<void>();
  @Output() created = new EventEmitter<AnnouncementRequestDto>();

  form: FormGroup;

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      title: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(120)]],
      message: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(225)]],
    });
  }

  onSubmit() {
    if (this.form.invalid) return;

    const { title, message } = this.form.value;
    const dto: AnnouncementRequestDto = {
      title,
      message,
      company: this.companyId
    };

    // emit to parent; parent handles POST to /companies/{id}/announcements or /announcements
    this.created.emit(dto);
    this.close.emit();
  }

  onClose() {
    this.close.emit();
  }

  onBackdropClick(event: MouseEvent) {
    if ((event.target as HTMLElement).classList.contains('modal-backdrop')) {
      this.onClose();
    }
  }
}
