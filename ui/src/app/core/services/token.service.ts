import { Injectable } from '@angular/core';

const TOKEN_KEY = 'wc_token';
const USER_KEY = 'wc_user';

@Injectable({ providedIn: 'root' })
export class TokenService {

  setToken(token: string) {
    localStorage.setItem(TOKEN_KEY, token);
  }

  getToken(): string | null {
    return localStorage.getItem(TOKEN_KEY);
  }

  clear() {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.removeItem(USER_KEY);
  }

  setUser(user: any) {
    localStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  getUser(): any {
    const data = localStorage.getItem(USER_KEY);
    return data ? JSON.parse(data) : null;
  }

  getRole(): string | null {
    return this.getUser()?.role ?? null;
  }

  isAdmin(): boolean {
    return this.getRole() === 'ADMIN';
  }
}
