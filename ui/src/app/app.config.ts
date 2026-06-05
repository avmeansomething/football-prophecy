import { APP_INITIALIZER, ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient, withInterceptors } from '@angular/common/http';

import { routes } from './app.routes';
import { authInterceptor } from './core/interceptors/auth.interceptor';

import { providePrimeNG } from 'primeng/config';
import Aura from '@primeuix/themes/aura';
import { AuthStateService } from './core/services/auth.state.service'; // Lara/Aura preset depending on install

export const appConfig: ApplicationConfig = {
  providers: [

    // Router
    provideRouter(routes),

    // HTTP + JWT interceptor
    provideHttpClient(
      withInterceptors([authInterceptor])
    ),

    // PrimeNG config (Lara/Aura theme system)
    providePrimeNG({
      theme: {
        preset: Aura
      }
    }),
    {
      provide: APP_INITIALIZER,
      useFactory: initAuth,
      deps: [AuthStateService],
      multi: true
    }
  ]
};

export function initAuth(auth: AuthStateService) {
  return () => auth.init();
}
