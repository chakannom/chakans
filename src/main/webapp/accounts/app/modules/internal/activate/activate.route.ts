import { Route } from '@angular/router';

import { ActivateComponent } from './activate.component';

export const ACTIVATE_ROUTE: Route = {
  path: 'activate',
  component: ActivateComponent,
  data: {
    authorities: [],
    pageTitle: 'activate.title'
  }
};
