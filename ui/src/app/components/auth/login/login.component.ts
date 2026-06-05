import { Component, inject, signal } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { Button } from 'primeng/button';
import { Divider } from 'primeng/divider';
import { FloatLabel } from 'primeng/floatlabel';
import { Password } from 'primeng/password';
import { FormsModule } from '@angular/forms';
import { InputText } from 'primeng/inputtext';
import { Message } from 'primeng/message';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
  imports: [
    Button,
    Divider,
    FloatLabel,
    Password,
    FormsModule,
    InputText,
    Message,
    RouterLink
  ],
  standalone: true
})
export class LoginComponent {

  private auth = inject(AuthService);
  private router = inject(Router);

  username = '';
  password = '';

  loading = signal(false);
  error = signal('');

  login() {
    this.loading.set(true);
    this.error.set('');

    this.auth.login({
      username: this.username,
      password: this.password
    }).subscribe({
      next: () => {
        this.loading.set(false);
        this.router.navigate(['/dashboard']);
      },
      error: () => {
        this.loading.set(false);
        this.error.set('Неверный логин или пароль');
      }
    });
  }

  demoAdmin() {
    this.username = 'admin';
    this.password = 'admin';
    this.login();
  }

  demoPlayer() {
    this.username = 'player';
    this.password = 'player';
    this.login();
  }
}
