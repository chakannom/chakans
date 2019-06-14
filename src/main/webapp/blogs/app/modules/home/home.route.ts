import { Route } from '@angular/router';

import { HomeComponent } from './';
import { HomeModuleAccessService } from '../../core';

export const HOME_ROUTE: Route = {
  path: '',
  component: HomeComponent,
  data: {
    authorities: [],
    pageTitle: 'home.title'
  },
  canActivate: [HomeModuleAccessService]
};
