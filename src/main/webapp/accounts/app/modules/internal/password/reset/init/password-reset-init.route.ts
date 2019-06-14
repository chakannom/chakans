import { Route } from '@angular/router';

import { PasswordResetInitComponent } from './password-reset-init.component';

export const PASSWORD_RESET_INIT_ROUTE: Route = {
  path: 'password/reset/init',
  component: PasswordResetInitComponent,
  data: {
    authorities: [],
    pageTitle: 'global.menu.account.password'
  }
};
