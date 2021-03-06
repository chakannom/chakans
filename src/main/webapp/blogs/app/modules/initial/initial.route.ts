import { Route } from '@angular/router';

import { UserProfileRouteAccessService } from '../../core';
import { InitialComponent } from './initial.component';

export const INITIAL_ROUTE: Route = {
  path: 'initial.cb',
  component: InitialComponent,
  data: {
    authorities: ['ROLE_USER'],
    pageTitle: 'initial.title'
  },
  canActivate: [UserProfileRouteAccessService]
};
