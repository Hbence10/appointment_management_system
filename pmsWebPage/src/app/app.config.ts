import { ApplicationConfig, provideBrowserGlobalErrorListeners, provideZoneChangeDetection } from '@angular/core';
import { provideRouter, withComponentInputBinding } from '@angular/router';

import { HTTP_INTERCEPTORS, provideHttpClient, withFetch, withInterceptors } from '@angular/common/http';
import { provideClientHydration, withEventReplay } from '@angular/platform-browser';
import { provideServerRendering } from '@angular/ssr';
import { routes } from './app.routes';
import { MAT_DATE_LOCALE, provideNativeDateAdapter } from '@angular/material/core';
import { asdInterceptor } from './auth.interceptor';
import { CookieService } from 'ngx-cookie-service';


export const appConfig: ApplicationConfig = {
  providers: [
    CookieService,
    provideBrowserGlobalErrorListeners(),
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes, withComponentInputBinding()), provideClientHydration(withEventReplay()),
    provideServerRendering(),
    provideHttpClient(
      withFetch(),
      withInterceptors([asdInterceptor])
    ),
    provideNativeDateAdapter(),
    { provide: MAT_DATE_LOCALE, useValue: 'hu' },
  ]
};
