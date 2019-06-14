import { Routes } from '@angular/router';

import { PASSWORD_CHANGE_ROUTE, SETTINGS_ROUTE } from './';

export const SELF_ROUTES: Routes = [
  {
    path: 'self',
    children: [PASSWORD_CHANGE_ROUTE, SETTINGS_ROUTE]
  }
];
