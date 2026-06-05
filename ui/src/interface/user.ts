export interface User {
  id: number;
  username: string;
  role: 'ADMIN' | 'PLAYER';
  points: number;
}

export interface LoginRequest {
  username: string;
  password: string;
}

export interface RegisterRequest {
  username: string;
  password: string;
}

export interface AuthResponse {
  token: string;
  username: string;
  role: 'ADMIN' | 'PLAYER';
}
