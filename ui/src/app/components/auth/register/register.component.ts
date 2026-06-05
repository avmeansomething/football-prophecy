import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  private authService = inject(AuthService);
  private router = inject(Router);

  username = '';
  password = '';
  confirmPassword = '';

  loading = signal(false);
  errorMessage = signal('');

  onSubmit() {
    if (!this.username || !this.password) {
      this.errorMessage.set('Заполните все поля');
      return;
    }

    if (this.password !== this.confirmPassword) {
      this.errorMessage.set('Пароли не совпадают');
      return;
    }

    if (this.password.length < 4) {
      this.errorMessage.set('Пароль должен быть не менее 4 символов');
      return;
    }

    this.authService.register({ username: this.username, password: this.password })
      .subscribe({
        next: () => {
          this.router.navigate(['/dashboard']);
        },
        error: (err) => {
          this.errorMessage.set(err.error?.message || 'Ошибка регистрации');
        }
      });
  }
}
