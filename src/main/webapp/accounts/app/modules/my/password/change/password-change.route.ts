import { Route } from '@angular/router';

import { UserRouteAccessService } from '../../../../core';
import { PasswordChangeComponent } from './password-change.component';

export const PASSWORD_CHANGE_ROUTE: Route = {
  path: 'password/change',
  component: PasswordChangeComponent,
  outlet: 'my',
  data: {
    authorities: ['ROLE_USER'],
    pageTitle: 'global.menu.account.password'
  },
  canActivate: [UserRouteAccessService]
};
