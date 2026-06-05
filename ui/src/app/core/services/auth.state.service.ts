import { Injectable, signal } from '@angular/core';

export interface User {
  username: string;
  role: 'ADMIN' | 'PLAYER';
}

@Injectable({ providedIn: 'root' })
export class AuthStateService {

  user = signal<User | null>(null);

  init() {
    const raw = localStorage.getItem('wc_user');
    const token = localStorage.getItem('wc_token');

    if (raw && token) {
      this.user.set(JSON.parse(raw));
    }
  }

  setUser(user: User) {
    this.user.set(user);
    localStorage.setItem('wc_user', JSON.stringify(user));
  }

  clear() {
    this.user.set(null);
    localStorage.removeItem('wc_user');
    localStorage.removeItem('wc_token');
  }

  isAuthenticated(): boolean {
    return !!localStorage.getItem('wc_token');
  }

  isAdmin(): boolean {
    return this.user()?.role === 'ADMIN';
  }
}
