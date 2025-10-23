import { Router } from '@angular/router';
import { AuthService } from './../../services/auth';
import { Credentials } from '../../models';
import { Component } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, Validators, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {
  form: FormGroup;

  errorMessage: string | null = '';
  isLoading = false;

  constructor(private authService: AuthService, private router: Router, private fb: FormBuilder) {
    this.form = this.fb.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required]]
    });
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.errorMessage = 'Please enter both username and password.';
      return;
    }

    this.errorMessage = '';
    this.isLoading = true;

    const credentials = this.form.value as Credentials;

    this.authService.login(credentials).subscribe({
      next: (user) => {
        console.log('Login successful:', user);
        if (user.admin) {
          this.router.navigate(['/select-company']);
        } else {
          this.router.navigate(['/announcements']);
        }
      },
      error: (err) => {
        this.errorMessage = 'Login failed. Please check your credentials and try again.';
        console.error('Login error:', err);
        this.isLoading = false;
      },
      complete: () => {
        this.isLoading = false;
      }

    });
  }
}
