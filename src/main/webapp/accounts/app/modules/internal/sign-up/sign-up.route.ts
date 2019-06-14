import { Route } from '@angular/router';

import { ContinueParamAccessService } from '../../../core';
import { SignUpComponent } from './sign-up.component';
import { SignUpResultComponent } from './sign-up-result.component';

export const SIGN_UP_ROUTE: Route = {
  path: 'signup',
  component: SignUpComponent,
  data: {
    authorities: [],
    pageTitle: 'sign-up.title'
  },
  canActivate: [ContinueParamAccessService]
};

export const SIGN_UP_RESULT_ROUTE: Route = {
  path: 'signup/result/:result',
  component: SignUpResultComponent,
  data: {
    authorities: [],
    pageTitle: 'sign-up.title'
  },
  canActivate: [ContinueParamAccessService]
};
