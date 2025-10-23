import { Component, EventEmitter, Output } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { UserRequestDto } from '../../models';
import { CompanyStateService } from '../../services/company-state.service';

@Component({
  selector: 'app-add-user-modal',
  imports: [ReactiveFormsModule],
  templateUrl: './add-user-modal.html',
  styleUrl: './add-user-modal.css'
})
export class AddUserModal {
  @Output() close = new EventEmitter<void>();
  @Output() userAdded = new EventEmitter<UserRequestDto>();
  form: FormGroup;

  constructor(private fb: FormBuilder, private companyState: CompanyStateService) {
    this.form = this.fb.group({
      username: [''],
      firstName: [''],
      lastName: [''],
      email: [''],
      phone: [''],
      password: [''],
      confirmPassword: [''],
      admin: [false],
      active: [true]
    });
  }

  onSubmit() {
    if (this.form.valid) {
      const formValue = this.form.value;

      const company = this.companyState.getCompany();
      if (!company) {
        console.error('Company not found in state.');
        return;
      }

      // Transform flat form data into nested DTO structure for backend
      const userRequest = {
        credentials: {
          username: formValue.username,
          password: formValue.password
        },
        profile: {
          firstName: formValue.firstName,
          lastName: formValue.lastName,
          email: formValue.email,
          phone: formValue.phone
        },
        isAdmin: formValue.admin,
        companyId: company.id
      };

      console.log('User request:', userRequest);
      this.userAdded.emit(userRequest);
      this.close.emit();
    }
  }

  onClose() {
    this.close.emit();
  }

  onBackdropClick(event: MouseEvent) {
    if ((<HTMLElement>event.target).classList.contains('modal-backdrop')) {
      this.onClose();
    }
  }
}
