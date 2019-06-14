import { Route } from '@angular/router';

import { ContinueParamAccessService } from '../../../core';
import { SignInComponent } from './sign-in.component';

export const SIGN_IN_ROUTE: Route = {
  path: 'signin',
  component: SignInComponent,
  data: {
    authorities: [],
    pageTitle: 'global.menu.sign.in'
  },
  canActivate: [ContinueParamAccessService]
};
