import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs';
import { LoginRequest, RegisterRequest, AuthResponse } from '../../../interface/user';
import { TokenService } from './token.service';
import { environment } from '../environment/environment';

@Injectable({ providedIn: 'root' })
export class AuthService {

  private api = `${environment.apiUrl}/auth`;

  constructor(
    private http: HttpClient,
    private tokenService: TokenService
  ) {}

  login(req: LoginRequest) {
    return this.http.post<AuthResponse>(`${this.api}/login`, req)
      .pipe(
        tap(res => this.handleAuth(res))
      );
  }

  register(req: RegisterRequest) {
    return this.http.post<AuthResponse>(`${this.api}/register`, req)
      .pipe(
        tap(res => this.handleAuth(res))
      );
  }

  private handleAuth(res: AuthResponse) {
    this.tokenService.setToken(res.token);
    this.tokenService.setUser({
      username: res.username,
      role: res.role
    });
  }

  logout() {
    this.tokenService.clear();
  }
}
