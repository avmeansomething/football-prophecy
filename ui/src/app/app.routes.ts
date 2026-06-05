import { Routes } from '@angular/router';

import { authGuard } from './core/guards/auth.guard';
import { adminGuard } from './core/guards/admin.guard';

export const routes: Routes = [

  // =========================
  // PUBLIC
  // =========================

  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'login'
  },

  {
    path: 'login',
    loadComponent: () =>
      import('./components/auth/login/login.component')
        .then(m => m.LoginComponent)
  },

  {
    path: 'register',
    loadComponent: () =>
      import('./components/auth/register/register.component')
        .then(m => m.RegisterComponent)
  },

  // =========================
  // PLAYER AREA
  // =========================

  {
    path: '',
    canActivate: [authGuard],
    children: [

      {
        path: 'dashboard',
        loadComponent: () =>
          import('./components/dashboard/dashboard.component')
            .then(m => m.DashboardComponent)
      },

      {
        path: 'matches',
        loadComponent: () =>
          import('./components/matches/matches.component')
            .then(m => m.MatchesComponent)
      },

      {
        path: 'predictions',
        loadComponent: () =>
          import('./components/predictions/predictions.component')
            .then(m => m.PredictionsComponent)
      },

      {
        path: 'leaderboard',
        loadComponent: () =>
          import('./components/leaderboard/leaderboard.component')
            .then(m => m.LeaderboardComponent)
      }

    ]
  },

  // =========================
  // ADMIN AREA
  // =========================

  {
    path: 'admin',
    canActivate: [authGuard, adminGuard],
    children: [

      {
        path: '',
        loadComponent: () =>
          import('./components/admin/admin-dashboard.component/admin-dashboard.component')
            .then(m => m.AdminDashboardComponent)
      },

      {
        path: 'teams',
        loadComponent: () =>
          import('./components/admin/teams-admin.component/teams-admin.component')
            .then(m => m.TeamsAdminComponent)
      },

      {
        path: 'predictions',
        loadComponent: () =>
          import('./components/admin/predictions-admin.component/predictions-admin.component')
            .then(m => m.PredictionsAdminComponent)
      },

      {
        path: 'matches',
        loadComponent: () =>
          import('./components/admin/matches-admin.component/matches-admin.component')
            .then(m => m.MatchesAdminComponent)
      },

      {
        path: 'users',
        loadComponent: () =>
          import('./components/admin/users-admin.component/users-admin.component')
            .then(m => m.UsersAdminComponent)
      }

    ]
  },

  // =========================
  // FALLBACK
  // =========================

  {
    path: '**',
    redirectTo: 'login'
  }

];
