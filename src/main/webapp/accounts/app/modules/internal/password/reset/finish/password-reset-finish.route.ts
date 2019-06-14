import { Route } from '@angular/router';

import { PasswordResetFinishComponent } from './password-reset-finish.component';

export const PASSWORD_RESET_FINISH_ROUTE: Route = {
  path: 'password/reset/finish',
  component: PasswordResetFinishComponent,
  data: {
    authorities: [],
    pageTitle: 'global.menu.account.password'
  }
};
