import { Component, EventEmitter, Output } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ReactiveFormsModule, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { UserRequestDto } from '../../models';
import { CompanyStateService } from '../../services/company-state.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-add-user-modal',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './add-user-modal.html',
  styleUrl: './add-user-modal.css'
})
export class AddUserModal {
  @Output() close = new EventEmitter<void>();
  @Output() userAdded = new EventEmitter<UserRequestDto>();
  form: FormGroup;

  constructor(private fb: FormBuilder, private companyState: CompanyStateService) {
    this.form = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(20)]],
      firstName: ['', [Validators.required, Validators.minLength(2)]],
      lastName: ['', [Validators.required, Validators.minLength(2)]],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', [Validators.pattern(/^\(?(\d{3})\)?[- ]?(\d{3})[- ]?(\d{4})$/)]],
      password: ['', [Validators.required, Validators.minLength(8), Validators.pattern(/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d@$!%*#?&]{8,}$/)]],
      confirmPassword: ['', [Validators.required]],
      admin: [false],
      active: [true]
    }, { validators: this.passwordMatchValidator() });
  }

  // Custom validator to check if password and confirmPassword match
  passwordMatchValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const password = control.get('password');
      const confirmPassword = control.get('confirmPassword');

      if (!password || !confirmPassword) {
        return null;
      }

      return password.value === confirmPassword.value ? null : { passwordMismatch: true };
    };
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
