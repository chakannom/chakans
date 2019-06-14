import { Route } from '@angular/router';

import { GoComponent } from './go.component';

export const GO_ROUTE: Route = {
  path: 'go/:modulename',
  component: GoComponent,
  data: {
    authorities: [],
    pageTitle: 'home.title'
  },
  canActivate: []
};
