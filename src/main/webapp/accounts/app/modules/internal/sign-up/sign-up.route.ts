import { Route } from '@angular/router';

import { ContinueParamValidatorService } from '../../../core';
import { SignUpComponent } from './sign-up.component';
import { SignUpResultComponent } from './sign-up-result.component';

export const signUpRoute: Route = {
    path: 'signup',
    component: SignUpComponent,
    data: {
        authorities: [],
        pageTitle: 'sign-up.title'
    },
    canActivate: [ContinueParamValidatorService]
};

export const signUpResultRoute: Route = {
    path: 'signup/result/:result',
    component: SignUpResultComponent,
    data: {
        authorities: [],
        pageTitle: 'sign-up.title'
    },
    canActivate: [ContinueParamValidatorService]
};
