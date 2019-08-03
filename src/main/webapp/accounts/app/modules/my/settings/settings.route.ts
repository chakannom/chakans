import { Route } from '@angular/router';

import { UserRouteAccessService } from '../../../core';
import { SettingsComponent } from './settings.component';

export const SETTINGS_ROUTE: Route = {
  path: 'settings',
  component: SettingsComponent,
  data: {
    authorities: ['ROLE_USER'],
    pageTitle: 'global.menu.account.settings'
  },
  canActivate: [UserRouteAccessService]
};
