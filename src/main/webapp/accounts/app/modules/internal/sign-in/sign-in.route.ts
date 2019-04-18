import { Route } from '@angular/router';

import { ContinueParamValidatorService } from '../../../core';
import { SignInComponent } from './sign-in.component';

export const signInRoute: Route = {
    path: 'signin',
    component: SignInComponent,
    data: {
        authorities: [],
        pageTitle: 'global.menu.sign.in'
    },
    canActivate: [ContinueParamValidatorService]
};
